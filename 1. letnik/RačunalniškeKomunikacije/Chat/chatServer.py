
#PRIVATNA SPOROČILA SE ZAČNEJO: /ime ...


import signal

signal.signal(signal.SIGINT, signal.SIG_DFL)
import socket
import struct
import threading

import ssl

PORT = 1234
HEADER_LENGTH = 2


def receive_fixed_length_msg(sock, msglen):
    message = b''
    while len(message) < msglen:
        chunk = sock.recv(msglen - len(message))  # preberi nekaj bajtov
        if chunk == b'':
            raise RuntimeError("socket connection broken")
        message = message + chunk  # pripni prebrane bajte sporocilu

    return message



def receive_message(sock):
    header = receive_fixed_length_msg(sock,
                                      HEADER_LENGTH)  # preberi glavo sporocila (v prvih 2 bytih je dolzina sporocila)
    message_length = struct.unpack("!H", header)[0]  # pretvori dolzino sporocila v int

    message = None
    if message_length > 0:  # ce je vse OK
        message = receive_fixed_length_msg(sock, message_length)  # preberi sporocilo
        message = message.decode("utf-8")

    return message


def send_message(sock, message):
    encoded_message = message.encode("utf-8")  # pretvori sporocilo v niz bajtov, uporabi UTF-8 kodno tabelo

    # ustvari glavo v prvih 2 bytih je dolzina sporocila (HEADER_LENGTH)
    # metoda pack "!H" : !=network byte order, H=unsigned short
    header = struct.pack("!H", len(encoded_message))

    message = header + encoded_message  # najprj posljemo dolzino sporocilo, slee nato sporocilo samo
    sock.sendall(message)


# funkcija za komunikacijo z odjemalcem (tece v loceni niti za vsakega odjemalca)
def client_thread(client_sock, client_addr):
    global clients
    global client_dict

    print("[system] connected with " + client_addr[0] + ":" + str(client_addr[1]))
    print("[system] we now have " + str(len(clients)) + " clients")

    #pozdrav = receive_message(client_sock)

    #ime = pozdrav[pozdrav.index("<")+1:pozdrav.index(">")]
    #print(ime)

    client_dict[user] = client_sock
    client_Rdict[client_sock] = user
    #print(client_dict)

    #print("[RKchat] [" + client_addr[0] + ":" + str(client_addr[1]) + "] : " + pozdrav)

    #for client in clients:
    #    send_message(client, pozdrav.upper())

    try:

        while True:  # neskoncna zanka
            msg_received = receive_message(client_sock)

            if not msg_received:  # ce obstaja sporocilo
                break

            message = msg_received.split(" >> ")
            #print(message)
            #print(message)

            if message[1].startswith("/"):
                privat = message[1][1:message[1].index(" ")]
               #print(privat)
                if privat in client_dict:
                    send_message(client_dict[privat], message[0].upper() +" "+ client_Rdict[client_sock] + " ("+ privat +")>>"+ message[1][message[1].index(" "):])
                    send_message(client_sock, message[0].upper() +" "+"JAZ" +" ("+ privat +")>>"+ message[1][message[1].index(" "):])
                else:
                    send_message(client_sock, "napaka: uporabnik ni prijavljen")
            else:

                print("[RKchat] [" + str(client_addr[0]) + ":" + str(client_addr[1]) + "] : " + message[0] + "<" + client_Rdict[client_sock] +"> >> " + message[1])

                for client in clients:
                    if client == client_sock:
                        send_message(client, message[0] + "<" + "JAZ" +"> >> " + message[1].upper())
                    else:
                        send_message(client, message[0] + "<" + client_Rdict[client_sock] +"> >> " + message[1].upper())
    except:
        # tule bi lahko bolj elegantno reagirali, npr. na posamezne izjeme. Trenutno kar pozremo izjemo
        pass
 
    # prisli smo iz neskoncne zanke
    with clients_lock:
        clients.remove(client_sock)
        client_dict.pop(client_Rdict[client_sock])
        client_Rdict.pop(client_sock)
    print("[system] we now have " + str(len(clients)) + " clients")
    client_sock.close()


def setup_SSL_context():
    #uporabi samo TLS, ne SSL
    context = ssl.SSLContext(ssl.PROTOCOL_TLSv1_2)
    # certifikat je obvezen
    context.verify_mode = ssl.CERT_REQUIRED
    #nalozi svoje certifikate
    context.load_cert_chain(certfile="server.crt", keyfile="server.key")
    # nalozi certifikate CAjev, ki jim zaupas
    # (samopodp. cert. = svoja CA!)
    context.load_verify_locations('clients.pem')
    # nastavi SSL CipherSuites (nacin kriptiranja)
    context.set_ciphers('ECDHE-RSA-AES128-GCM-SHA256')
    return context



my_ssl_ctx = setup_SSL_context()
server_socket = my_ssl_ctx.wrap_socket(socket.socket(socket.AF_INET, socket.SOCK_STREAM))
server_socket.bind(("localhost", PORT))
server_socket.listen(1)


# kreiraj socket
#server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
#server_socket.bind(("localhost", PORT))
#server_socket.listen(1)

# cakaj na nove odjemalce
print("[system] listening ...")
clients = set()
client_dict = {}
client_Rdict = {}
clients_lock = threading.Lock()
while True:
    try:
        # pocakaj na novo povezavo - blokirajoc klic
        client_sock, client_addr = server_socket.accept()
        #print(client_addr[1])

        cert = client_sock.getpeercert()
        for sub in cert['subject']:
            for key, value in sub:
            # v commonName je ime uporabnika
                if key == 'commonName':
                    user = value


        print('Established SSL connection with: ', user)
        

        with clients_lock:
            clients.add(client_sock)

        for client in clients:
            send_message(client, "Povezal se je: " + user)    

        #client_dict[user] = client_sock
        #print(client_dict)

        #print(clients)

        thread = threading.Thread(target=client_thread, args=(client_sock, client_addr))
        thread.daemon = True
        thread.start()

    except KeyboardInterrupt:
        break

print("[system] closing server socket ...")
server_socket.close()
