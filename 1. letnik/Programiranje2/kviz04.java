
/**
 * kvizŠtiri
 */

interface SkladInterface {

    public boolean isEmpty(); // je sklad prazen?

    public void push(Object o); // doda element na vrh sklada

    public Object pop(); // vrne element z vrha sklada

    public void reverse(); // obrne vrstni red elementov na skladu

}

class Sklad implements SkladInterface {

    private java.util.ArrayList<Object> podatki;

    Sklad() {
        this.podatki = new java.util.ArrayList<Object>();
    }

    public boolean isEmpty() {
        if (this.podatki.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void push(Object o) {
        this.podatki.add(0, o);
    }

    public Object pop() {
        Object a = this.podatki.get(0);
        this.podatki.remove(a);
        return a;
    }

    public void reverse() {
        java.util.Collections.reverse(podatki);
    }
}

// popravi z extend in super()
class ArrayListPlus extends java.util.ArrayList<String> {

    ArrayListPlus() {
        super();
    }

    ArrayListPlus(String a) {
        super();
        String[] k = a.split(";");
        for (String l : k) {
            this.add(l);
        }
    }

    public String set(int index, String element) {

        if (index < this.size()) {
            return super.set(index, element);
        } else {
            while (index >= this.size()) {
                this.add("");
            }
            this.set(index, element);
            return element;
        }

    }

    public String toString() {
        String vrni = this.get(0).toString();

        for (int i = 1; i < this.size(); i++) {
            vrni += ";" + this.get(i);
        }

        return vrni;
    }
}

// ta naloga je tko prevec komplicirana, zato ni pravilno narjena
interface Mnozica {
    void add(char c);

    void remove(char c);

    void flip(char c);

    boolean contains(char c);

    boolean isEmpty();
}

class MnozicaZnakov implements Mnozica {

    private java.util.TreeSet<Character> crke;

    MnozicaZnakov() {
        this.crke = new java.util.TreeSet<>();
    }

    public void add(char c) {
        if (Character.isLetter(c)) {
            this.crke.add(c);
        }
    }

    public void remove(char c) {
        this.crke.remove(c);
    }

    public boolean contains(char c) {
        if (this.crke.contains(c)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isEmpty() {
        if (this.crke.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void flip(char c) {
        if (this.crke.contains(c)) {
            this.crke.remove(c);
        } else {
            this.crke.add(c);
        }
    }

    public String toString() {

        if (this.crke.isEmpty()) {
            return "0";
        }

        String vrni = "";

        for (int i = 'a'; i <= this.crke.last(); i++) {
            if (this.crke.contains((char) i)) {
                vrni = "1" + vrni;
            } else {
                vrni = "0" + vrni;
            }
        }
        return vrni;
    }
}

class Tocka {
    private int x;
    private int y;

    Tocka(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double izracunajRazdaljo(Tocka a) {
        String k = String.format("%.1f", java.lang.Math.sqrt((this.x) * (this.x) + (this.y) * (this.y)));
        return Double.parseDouble(k);
    }

    public double izracunajRazdaljo(Tocka a, Tocka b) {
        String k = String.format("%.1f", java.lang.Math.sqrt((a.getX() - b.getX()) * (a.getX() - b.getX()) + (a.getY() - b.getY()) * (a.getY() - b.getY())));
        return Double.parseDouble(k);
    }

    public String toString() {
        return String.format("Tocka (%d,%d) D = %.1f", this.x, this.y, izracunajRazdaljo(this));
    }
}

class Redovalnica {

    public static String[] pridobiImena(String x) {

        String[] tab = x.split(" ");
        int dolzina = tab.length / 2;
        int i = 0;

        String[] vrni = new String[dolzina];

        for (String k : tab) {
            if (Character.isLetter(k.charAt(0))) {
                vrni[i] = k;
                i++;
            }
        }

        return vrni;
    }

    public static int[] pridobiOcene(String x) {

        String[] tab = x.split(" ");
        int dolzina = tab.length / 2;
        int i = 0;

        int[] vrni = new int[dolzina];

        for (String k : tab) {
            if (Character.isDigit(k.charAt(0))) {
                vrni[i] = Integer.parseInt(k);
                i++;
            }
        }

        return vrni;
    }

    public static void uredi(int[] ocene, String[] imena) {

        int[] zakaj = new int[ocene.length];
        String[] zakva = new String[ocene.length];

        int max = 0;
        int index = 0;
        String temp1;

        for (int i = 0; i < ocene.length; i++) {
            for (int j = 0; j < ocene.length; j++) {
                if (ocene[j] > max) {
                    max = ocene[j];
                    index = j;
                }
            }

            temp1 = imena[i];

            zakaj[i] = max;
            zakva[i] = imena[index];

            ocene[index] = 0;
            imena[index] = temp1;
            max = 0;

        }

        for (int i = 0; i < ocene.length; i++) {
            ocene[i] = zakaj[i];
            imena[i] = zakva[i];
        }
    }

    public static void izpis(int[] ocene, String[] imena) {
        int index = 1;

        for (int i = 0; i < ocene.length; i++) {
            System.out.printf("%d:%d %s%n", index, ocene[i], imena[i]);
            index++;
        }

    }

}

public class kviz04 {

    static void dvojnaNagrada(String igralkeFilename, String igralciFilename) {

        java.util.List<java.util.List<String>> zenske = new java.util.ArrayList<>();
        java.util.List<java.util.List<String>> moski = new java.util.ArrayList<>();

        java.util.List<String> notri = new java.util.ArrayList<>();

        try (java.io.BufferedReader igralke = new java.io.BufferedReader(new java.io.FileReader(igralkeFilename));
                java.io.BufferedReader igralci = new java.io.BufferedReader(new java.io.FileReader(igralciFilename))) {
            String igralka;
            String igralec;

            while ((igralka = igralke.readLine()) != null) {
                String[] valuesZ = igralka.split(",");
                zenske.add(java.util.Arrays.asList(valuesZ));
            }
            while ((igralec = igralci.readLine()) != null) {
                String[] valuesM = igralec.split(",");
                moski.add(java.util.Arrays.asList(valuesM));
            }

            for (java.util.List<String> zen : zenske) {
                for (java.util.List<String> men : moski) {
                    if (zen.get(1).equals(men.get(1)) && zen.get(4).equals(men.get(4))) {
                        notri.add(String.format("Film:%s, Leto:%s, Igralka:%s, Igralec:%s", zen.get(4), zen.get(1),
                                zen.get(3), men.get(3)));
                        // System.out.println(zen.get(1) + " " + zen.get(3) + " " + men.get(3));
                    }
                }
            }

            java.util.Collections.sort(notri);
            for (String k : notri) {
                System.out.println(k);
            }

        } catch (java.io.IOException e) {
            System.out.println("aaaaaaaaaaa");
        }
    }

    static void poisciInIzpisiBarve(String imeDatoteke) {

        try (java.util.Scanner sc = new java.util.Scanner(new java.io.File(imeDatoteke))) {

            while (sc.hasNextLine()) {
                String a = sc.nextLine();

                if (a.contains("color: #")) {

                    for (int indeks = a.indexOf("color: #"); indeks >= 0; indeks = a.indexOf("color: #", indeks + 1)) {
                        int glej = 0;
                        // System.out.println(indeks);

                        if ((indeks + 14) > a.length()) {
                            continue;
                        }

                        String dva = a.substring(indeks, indeks + 14);
                        String[] vrednost = dva.split(" ");
                        String ena = vrednost[1];

                        for (int i = 1; i < ena.length(); i++) {
                            if (!Character.isDigit(ena.charAt(i)) && !Character.isLetter(ena.charAt(i))) {
                                // System.out.println(ena + " " + ena.charAt(i));
                                glej = 1;
                                break;
                            }
                        }

                        if (indeks - 1 < 0 || indeks - 3 < 0) {
                        } else if (ena.length() < 7 || a.charAt(indeks - 1) == '-' || a.charAt(indeks - 3) == 'd'
                                || glej == 1) {
                            // System.out.println(glej == 1);
                            continue;
                        }

                        int r = Integer.parseInt(ena.substring(1, 3), 16);
                        int g = Integer.parseInt(ena.substring(3, 5), 16);
                        int b = Integer.parseInt(ena.substring(5, 7), 16);

                        double R = r / 255.0;
                        double G = g / 255.0;
                        double B = b / 255.0;

                        double M = 0;
                        double m = 0;
                        char rgb = 'a';

                        if (r >= g && r >= b) {
                            M = R;
                            rgb = 'r';
                        } else if (g >= r && g >= b) {
                            M = G;
                            rgb = 'g';
                        } else if (b >= r && b >= g) {
                            M = B;
                            rgb = 'b';
                        }
                        if (r <= g && r <= b) {
                            m = R;
                        } else if (g <= r && g <= b) {
                            m = G;
                        } else if (b <= r && b <= g) {
                            m = B;
                        }

                        double C = (M - m);
                        double Ho = 0;

                        if (C == 0) {
                            Ho = 0;
                        } else if (rgb == 'r') {
                            Ho = ((G - B) / C) % 6;
                        } else if (rgb == 'g') {
                            Ho = ((B - R) / C) + 2;
                        } else if (rgb == 'b') {
                            Ho = ((R - G) / C) + 4;
                        }

                        double H = 60 * Ho;

                        if (H < 0) {
                            H += 360;
                        }

                        double L = (M + m) / 2;

                        double S = 0;

                        if (L != 1) {
                            S = C / (1 - java.lang.Math.abs(2 * L - 1));
                        }
                        System.out.printf("%s -> rgb(%d, %d, %d) -> hsl(%.0f, %.0f, %.0f)%n", ena, r, g, b, H, S * 100,
                                L * 100);
                    }
                }
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println("jah");
        }
    }

    static void statistikaStavkov(String imeDatoteke) throws IzjemaManjkajocegaLocila {

        try (java.util.Scanner sc = new java.util.Scanner(new java.io.File(imeDatoteke))) {

            while (sc.hasNextLine()) {
                String stavek = sc.nextLine();

                if (stavek.contains("?")) {
                    stavek = stavek.replaceAll("?", ".");
                } else if (stavek.contains("!")) {
                    stavek = stavek.replaceAll("!", ".");
                }

                stavek = stavek.replaceAll("\\. ", ".");

                // System.out.println(stavek);

                String[] stavki = stavek.split("\\.");

                java.util.ArrayList<Integer> dolzine = new java.util.ArrayList<>();

                java.util.HashMap<Integer, Integer> oboje = new java.util.HashMap<>();

                for (String k : stavki) {
                    if (!Character.isLetter(k.charAt(0))) {
                        k = k.substring(1);
                    }

                    String[] temp = k.split("\\s+");

                    if (oboje.containsKey(temp.length)) {
                        Integer count = oboje.get(temp.length);

                        oboje.put(temp.length, count + 1);
                    } else {
                        oboje.put(temp.length, 1);
                        dolzine.add(temp.length);
                    }
                }

                java.util.Collections.sort(dolzine);

                if (!sc.hasNext() && !stavek.endsWith(".")) {
                    throw new IzjemaManjkajocegaLocila();
                }

                for (Integer i : dolzine) {
                    System.out.printf("Stavki dolzine %d se pojavijo: %dx.%n", i, oboje.get(i));
                }
            }

        } catch (java.io.FileNotFoundException e) {
            System.out.print("Napaka pri branju datoteke.");
        }
    }

    static void preberiRacunInIzpisi(String imeDatoteke) {

        double ddv = 0;
        double cena = 0;

        try {
            java.util.Scanner sc = new java.util.Scanner(new java.io.File(imeDatoteke));

            while (sc.hasNextLine()) {
                String niz = sc.nextLine();
                if (niz.length() == 0) {
                    continue;
                } else if (niz.contains("\t") && Character.isDigit(niz.charAt(0))) {
                    String[] k = niz.split("\t");
                    k[1] = k[1].replace(",", ".");
                    k[2] = k[2].replace(",", ".");

                    ddv += Double.parseDouble(k[1]);
                    cena += Double.parseDouble(k[2]);
                }
            }

            System.out.printf("%-18s%5.2f%n", "Skupaj brez DDV:", cena - ddv);
            System.out.printf("%-18s%5.2f%n", "DDV:", ddv);
            System.out.printf("%-18s%5.2f%n", "ZNESEK SKUPAJ:", cena);
            sc.close();

        } catch (java.io.FileNotFoundException e) {
            System.out.print("Napaka pri branju datoteke!");
        }

    }

    static void izpisi(String imeDatoteke) {

        System.out.println("V datoteki " + imeDatoteke + " so naslednje številke:");

        try (java.io.DataInputStream ds = new java.io.DataInputStream(
                new java.io.FileInputStream(new java.io.File(imeDatoteke)));) {
            String k = "";
            int st = 1;

            while (ds.available() > 0) {

                int z = ds.read();
                String l = Integer.toBinaryString(z);
                k += String.format("%8s", l).replace(' ', '0');

                if (st % 3 == 0) {
                    String stevilka = String.valueOf(Integer.parseInt(k, 2));
                    System.out.printf("0%s / %s %s", stevilka.charAt(0), stevilka.substring(1, 4),
                            stevilka.substring(4, 7));
                    System.out.println();
                    k = "";
                }
                st++;
            }

        } catch (java.io.IOException e) {
            System.out.println("HJAJAJJAJA");
        }
    }

    static void preveri(String stevilka, String imeDatoteke) {

        try (java.io.DataInputStream ds = new java.io.DataInputStream(
                new java.io.FileInputStream(new java.io.File(imeDatoteke)));) {
            String k = "";
            int st = 1;

            while (ds.available() > 0) {

                int z = ds.read();
                String l = Integer.toBinaryString(z);
                k += String.format("%8s", l).replace(' ', '0');

                System.out.printf("%02d", l);

                if (st % 3 == 0) {
                    String stev = String.valueOf(Integer.parseInt(k, 2));
                    stev = String.format("0%s / %s %s", stev.charAt(0), stev.substring(1, 4), stev.substring(4, 7));
                    if (stev.equals(stevilka)) {
                        System.out.printf("Številka %s je v datoteki", stevilka);
                        st = 0;
                        break;
                    }

                    k = "";
                }
                st++;
            }

            if (st != 0) {
                System.out.printf("Številke %s ni v datoteki", stevilka);
            }

        } catch (java.io.IOException e) {
            System.out.println("HJAJAJJAJA");
        }

    }

    static int[] getVrstica(int n) {

        int[] zgorna = { 1 };
        int[] spodna = {};

        for (int i = 2; i <= n; i++) {

            spodna = new int[i];
            spodna[0] = i % 10;

            for (int j = 1; j < spodna.length; j++) {
                spodna[j] = (spodna[j - 1] + zgorna[j - 1]) % 10;
            }

            if (i == n) {
                return spodna;
            }
            zgorna = spodna;
        }

        return zgorna;
    }

    static void izpisiBesedilo(String imeDatoteke, int n, int s) {
        int st_pik = s;

        try {
            java.util.Scanner sc = new java.util.Scanner(new java.io.File(imeDatoteke));

            while (sc.hasNextLine()) {
                String niz = sc.nextLine();

                String[] l = niz.split(" ");

                String k = l[0];

                for (int i = 1; i < l.length; i++) {
                    k += " " + l[i];

                    if (k.length() > n) {
                        k = k.substring(0, k.lastIndexOf(" "));
                    } else {
                        continue;
                    }
                    st_pik -= k.length();
                    if (st_pik % 2 == 0) {
                        st_pik = st_pik / 2;
                        k = ".".repeat(st_pik) + k + ".".repeat(st_pik);
                    } else {
                        st_pik = st_pik / 2;
                        k = ".".repeat(st_pik) + k + ".".repeat(st_pik + 1);
                    }

                    System.out.printf("%s%n", k);
                    k = l[i];
                    st_pik = s;
                }

                st_pik -= k.length();
                if (st_pik % 2 == 0) {
                    st_pik = st_pik / 2;
                    k = ".".repeat(st_pik) + k + ".".repeat(st_pik);
                } else {
                    st_pik = st_pik / 2;
                    k = ".".repeat(st_pik) + k + ".".repeat(st_pik + 1);
                }

                System.out.printf("%s%n", k);
                st_pik = s;
            }
            sc.close();

        } catch (java.io.FileNotFoundException e) {
            System.out.print("Datoteka ne obstaja!");
        }
    }



    static void izracunaj(String imeDatoteke) {

        try (java.io.DataInputStream ds = new java.io.DataInputStream(
                new java.io.FileInputStream(new java.io.File(imeDatoteke)))) {

            if (ds.readByte() == 0x43 && ds.readByte() == 0x41 && ds.readByte() == 0x4C && ds.readByte() == 0x43) {

                int stRac = ds.readInt();
                int err = 0;

                for (int i = 0; i < stRac; i++) {
                    int prvi = ds.readInt();
                    char operacija = (char) ds.readByte();
                    int drugi = ds.readInt();

                    switch (operacija) {
                        case '+':
                            System.out.println(prvi + drugi);
                            break;

                        case '-':
                            System.out.println(prvi - drugi);
                            break;

                        case '*':
                            System.out.println(prvi * drugi);
                            break;

                        case '/':
                            System.out.println(prvi / drugi);
                            break;

                        default:
                            System.out.println("Napačen operator.");
                            err = 1;

                    }
                    if (err == 1) {
                        break;
                    }

                }
            } else {
                System.out.println("Datoteka " + imeDatoteke + " ni v formatu CLC!");
            }
        } catch (java.io.IOException e) {
            System.out.println("ja lej");
        }
    }

    static void histogram(String imeDatoteke) {

        java.util.TreeMap<String, Integer> histo = new java.util.TreeMap<>();
        StringBuilder t = new StringBuilder();

        try (java.io.DataInputStream ds = new java.io.DataInputStream(
                new java.io.FileInputStream(new java.io.File(imeDatoteke)))) {

            if (ds.readByte() == 0x50 && ds.readByte() == 0x32 && ds.readByte() == 0x42) {
                // System.out.println("je prava datoteka");

                int širina = ds.readInt();
                int višina = ds.readInt();

                for (int x = 0; x < širina; x++) {
                    for (int y = 0; y < višina; y++) {

                        for (int i = 0; i < 3; i++) {
                            t.append(String.format("%02x", ds.readUnsignedByte()));
                        }
                        String preveri = t.toString();

                        if (!histo.keySet().contains(preveri)) {
                            histo.put(preveri, 1);
                        } else {
                            int count = histo.get(preveri);

                            histo.put(preveri, count + 1);
                        }

                        t = new StringBuilder();

                    }
                }
            } else if (!imeDatoteke.endsWith("p2b")) {
                System.out.println("Datoteka ni v formatu P2B: napaka v imenu datoteke.");
            } else {
                System.out.println("Datoteka ni v formatu P2B: napaka pri podpisu slike.");
            }

            for (java.util.Map.Entry<String, Integer> entry : histo.entrySet()) {

                if (entry.getKey().startsWith("0")) {
                    String zbrisi = entry.getKey().replaceFirst("^0*", "");
                    if (zbrisi.isEmpty()) {
                        zbrisi = "0";
                    }

                    System.out.printf("%6s %d%n", zbrisi, entry.getValue());
                } else {
                    System.out.printf("%6s %d%n", entry.getKey(), entry.getValue());
                }
            }
        } catch (java.io.IOException e) {
            System.out.println(imeDatoteke + " (No such file or directory)");
        }
    }

    static void izpisi(int znak[]) {

        for (int i : znak) {
            for (int j = 0; j < 8; j++) {
                if ((i & (1 << j)) != 0) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    static void izpisiSt(String a) {

        int st = 0;

        for (int i = 0; i < a.length(); i++) {
            if (Character.isLetter(a.charAt(i))) {
                st++;
                System.out.print(a.charAt(i));
            } else {
                if (Character.isLetter(a.charAt(i - 1))) {
                    System.out.print("(" + st + ")");
                }
                System.out.print(a.charAt(i));

                st = 0;
            }
        }

        if (!(a.endsWith(".") || a.endsWith(",") || a.endsWith(" "))) {
            System.out.print("(" + st + ")");
        }

    }

    // ker so testi narobe zapostavljeni, sem si privoscil pravico do ustvarjanja
    // nove tabele
    // ki je samo kopija originalne

    static int[] premakni(int[] a) {

        int[] b = a.clone();

        int najmanjsi = a[0];
        int index = 0;

        for (int i = 0; i < b.length; i++) {
            if (b[i] < najmanjsi) {
                najmanjsi = b[i];
                index = i;
            }
        }

        for (int i = 0; i < (b.length - index); i++) {
            int temp1;

            temp1 = b[b.length - 1];

            for (int j = b.length - 1; j > 0; j--) {

                b[j] = b[j - 1];

            }
            b[0] = temp1;
        }
        return b;
    }

    static void kompaktniIzpis(String besedilo, int velikostIzpisa) {

        String[] besede = besedilo.split(" ");
        String a = besede[0];

        for (int i = 1; i < besede.length; i++) {
            a += " " + besede[i];

            if (a.length() > velikostIzpisa) {
                a = a.replace(" " + besede[i], "");

            } else {
                continue;
            }

            System.out.printf("%s%n", a + "_".repeat(velikostIzpisa - a.length()));
            a = besede[i];
        }

        System.out.printf("%s%n", a + "_".repeat(velikostIzpisa - a.length()));
    }



    public static void main(String[] args) {

    }

}

public class IzjemaManjkajocegaLocila extends RuntimeException {
    public String getMessage() {
        return "Izjema manjkajocega locila.";
    }

    IzjemaManjkajocegaLocila() {
        super();
    }
}