
#PRIVATNA SPOROČILA SE ZAČNEJO: /ime ...


import signal

signal.signal(signal.SIGINT, signal.SIG_DFL)
import socket
import struct
import threading

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

    pozdrav = receive_message(client_sock)

    ime = pozdrav[pozdrav.index("<")+1:pozdrav.index(">")]
    #print(ime)

    client_dict[ime] = client_sock
    #print(client_dict)

    print("[RKchat] [" + client_addr[0] + ":" + str(client_addr[1]) + "] : " + pozdrav)

    for client in clients:
        send_message(client, pozdrav.upper())

    try:

        while True:  # neskoncna zanka
            msg_received = receive_message(client_sock)

            if not msg_received:  # ce obstaja sporocilo
                break

            message = msg_received.split(" >> ")
            #print(message)

            if message[1].startswith("/"):
                privat = message[1][1:message[1].index(" ")]
                #print(privat)
                if privat in client_dict:
                    send_message(client_dict[privat], message[0].upper() + " ("+ privat +")>>"+ message[1][message[1].index(" "):])
                    send_message(client_sock, message[0].upper() + " ("+ privat +")>>"+ message[1][message[1].index(" "):])
                else:
                    send_message(client_sock, "napaka: uporabnik ni prijavljen")
            else:

                print("[RKchat] [" + client_addr[0] + ":" + str(client_addr[1]) + "] : " + msg_received)

                for client in clients:
                    send_message(client, msg_received.upper())
    except:
        # tule bi lahko bolj elegantno reagirali, npr. na posamezne izjeme. Trenutno kar pozremo izjemo
        pass
 
    # prisli smo iz neskoncne zanke
    with clients_lock:
        clients.remove(client_sock)
        client_dict.pop(ime)
    print("[system] we now have " + str(len(clients)) + " clients")
    client_sock.close()


# kreiraj socket
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind(("localhost", PORT))
server_socket.listen(1)

# cakaj na nove odjemalce
print("[system] listening ...")
clients = set()
client_dict = {}
clients_lock = threading.Lock()
while True:
    try:
        # pocakaj na novo povezavo - blokirajoc klic
        client_sock, client_addr = server_socket.accept()
        #print(client_addr[1])

        with clients_lock:
            clients.add(client_sock)

        #print(clients)

        thread = threading.Thread(target=client_thread, args=(client_sock, client_addr))
        thread.daemon = True
        thread.start()

    except KeyboardInterrupt:
        break

print("[system] closing server socket ...")
server_socket.close()
