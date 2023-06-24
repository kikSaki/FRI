import java.util.*;
import java.io.*;

/*
 * Malo razlage kode:
 * 
 * 
 * Za vlake sem delal po dva konstruktorja,
 * enega s tipom double za čas v urah in drugega s tipom int za minute.
 * 
 * Za izpis je potrebno specificirati ali je v urah če je več kot 60 min,
 * zato sem se odločil za tak pristop.
 * 
 * Če bi vrednosti pretvarjal v minute bi mogoče lahko skrajšal kodo,
 * a se mi je ta način zdel lažji.
 * 
 * 
 * 
 * Za prvo nalogo za preverjanje podatkov sem uporabil regularne izraze,
 * so se mi zdeli, še najhitrejši način pregledati pravilnost vsebine datoteke.
 * 
 * 
 */

class Kraj implements Comparable<Kraj>{

    private String ime;
    private String kratica;
    private ArrayList<Vlak> odhodi;

    Kraj(String ime, String kratica){
        this.ime = ime;
        this. kratica = kratica;
        this.odhodi = new ArrayList<Vlak>();
    }

    public String getIme(){
        return this.ime;
    }
    public String getKratica(){
        return this.kratica;
    }

    public ArrayList<Vlak> getOdhodi(){
        return this.odhodi;
    }

    public String toString(){
        return String.format("%s (%s)", this.ime, this.kratica);
    }

    public boolean dodajOdhod(Vlak vlak){

        if(!odhodi.contains(vlak)){
            odhodi.add(vlak);
            return true;
        }
        else{
            return false;
        }
    }

    public void odhodi(){
        System.out.printf("%s%nodhodi vlakov (%d):%n", this.toString(), this.odhodi.size());
        for(Vlak i : this.odhodi){
            System.out.printf(" - %s%n", i.toString());
        }
    }

    public void odhodiUrejeni(){
        Collections.sort(this.odhodi);

        System.out.printf("%s%nodhodi vlakov (%d):%n", this.toString(), this.odhodi.size());
        for(Vlak i : this.odhodi){
            System.out.printf(" - %s%n", i.toString());
        }
    }

    
    public int compareTo(Kraj ena){

        if(this.kratica.compareTo(ena.kratica) != 0){
            return this.kratica.compareTo(ena.getKratica());
        }
        else{
            return this.ime.compareTo(ena.getIme());
        }
    }

    public TreeSet<Kraj> destinacije(int k){

        TreeSet<Kraj> vsi = new TreeSet<Kraj>();
        TreeSet<Kraj> trenutni = new TreeSet<Kraj>();

        for(Vlak i : odhodi){
            vsi.add(i.getKam());
        }

        if(k > 0){
            for(Kraj i : vsi){
                TreeSet<Kraj> nov = i.destinacije(k-1);
                trenutni.addAll(nov);
            }
            vsi.addAll(trenutni);
        }
        vsi.remove(this);

        return vsi;
    }
}


class RegionalniVlak extends Vlak{
    private int hitrost = 50;
    private double cena = 0.068;

    RegionalniVlak(String id, Kraj odKje, Kraj kam, int cas){
        super(id, odKje, kam, cas);
    }

    RegionalniVlak(String id, Kraj odKje, Kraj kam, double casUre){
        super(id, odKje, kam, casUre);
    }

    public String opis(){
        return "regionalni";
    }

    public double cenaVoznje(){
        return this.getCas() * (hitrost/60.0) * cena; 
    }

}

class EkspresniVlak extends Vlak{
    private int hitrost = 110;
    private double cena = 0.154;

    private double doplacilo;

    EkspresniVlak(String id, Kraj odKje, Kraj kam, int cas, double doplacilo){
        super(id, odKje, kam, cas);
        this.doplacilo = doplacilo;
    }

    EkspresniVlak(String id, Kraj odKje, Kraj kam, double casUre, double doplacilo){
        super(id, odKje, kam, casUre);
        this.doplacilo = doplacilo; 
    }

    public String opis(){
        return "ekspresni";
    } 

    public double cenaVoznje(){
        return this.getCas() * (hitrost/60.0) * cena + this.doplacilo; 
    }
}

abstract class Vlak implements Comparable<Vlak>{

    private String id;
    private Kraj odKje;
    private Kraj kam;
    private int cas;
    private double casUre;

    Vlak(String id, Kraj odKje, Kraj kam, int cas){
        this.id = id;
        this.odKje = odKje;
        this.kam = kam;
        this.cas = cas;
        this.casUre = 0;
    }

    Vlak(String id, Kraj odKje, Kraj kam, double casUre){
        this.id = id;
        this.odKje = odKje;
        this.kam = kam;
        this.casUre = casUre;

        String minuteT = String.format("%.2f", casUre - Math.floor(casUre));
        double minT = Double.parseDouble(minuteT);

        this.cas = (int)Math.floor(casUre) * 60 + (int)Math.round(minT*100);
    }

    public String getId(){
        return this.id;
    }

    public Kraj getOdKje(){
        return this.odKje;
    }

    public Kraj getKam(){
        return this.kam;
    }

    public int getCas(){
        return this.cas;
    }

    public double getCasUre(){
        return this.casUre;
    }

    abstract public String opis();

    abstract public double cenaVoznje();

    public String toString(){
        if(this.casUre == 0){
            return String.format("Vlak %s (%s) %s -- %s (%d min, %.2f EUR)", this.id, this.opis(), this.odKje.toString(), this.kam.toString(), this.cas, this.cenaVoznje());
        }
        else{
            return String.format("Vlak %s (%s) %s -- %s (%.2fh, %.2f EUR)", this.id, this.opis(), this.odKje.toString(), this.kam.toString(), this.casUre, this.cenaVoznje()); 
        }
    }

    public int compareTo(Vlak ena){
        return Double.compare(ena.cenaVoznje(), this.cenaVoznje());
    }
}

class EuroRail{

    private List<Kraj> kraji;
    private List<Vlak> vlaki;

    private ArrayList<String> imenaKrajev;

    EuroRail(){
        this.kraji = new ArrayList<Kraj>();
        this.vlaki = new ArrayList<Vlak>();
        this.imenaKrajev = new ArrayList<String>();
    }

    public boolean preberiKraje(String imeDatoteke){

        try(Scanner sc = new Scanner(new File(imeDatoteke))){

            while(sc.hasNextLine()){
                String niz = sc.nextLine();

                if(niz.length() == 0 || !niz.contains(";")){
                    continue;
                }

                String[] podatki = niz.split(";");

                if(podatki.length != 2){
                    continue;
                }
                if(!podatki[0].matches("^[a-zA-Z ]+$") || !podatki[1].matches("^[A-Z]{1,4}$")){
                    continue;
                }
                if(podatki.length == 2 && !this.imenaKrajev.contains(podatki[0])){
                    Kraj nov = new Kraj(podatki[0], podatki[1]);
                    this.kraji.add(nov);
                    this.imenaKrajev.add(podatki[0]);

                }
            }
            return true;

        } catch(FileNotFoundException e){
            return false;
        }
    }

    public boolean preberiPovezave(String imeDatoteke){

        try(Scanner sc = new Scanner(new File(imeDatoteke))){

            while(sc.hasNextLine()){
                String niz = sc.nextLine();

                if(niz.length() == 0 || !niz.contains(";")){
                    continue;
                }

                String[] podatkiPov = niz.split(";");

                if((podatkiPov.length < 4 || podatkiPov.length > 5) || podatkiPov[1].equals(podatkiPov[2]) || (!podatkiPov[3].matches("^[0-9]+[.]?[0-9]+$") && !podatkiPov[3].matches("^[0-9]+$")) || !podatkiPov[0].matches("^[a-zA-Z0-9]{1,7}$")){
                    continue;
                }
                if(podatkiPov.length == 5 && !podatkiPov[4].matches("^[0-9]+[.]?[0-9]+$") && !podatkiPov[4].matches("^[0-9]+$")){
                    continue;
                }

                if((imenaKrajev.contains(podatkiPov[1]) && imenaKrajev.contains(podatkiPov[2]))){
                    Kraj zacetek = vrniKraj(podatkiPov[1]);
                    Kraj konec = vrniKraj(podatkiPov[2]);
                    Vlak nov;

                    if(podatkiPov[0].startsWith("RG") && podatkiPov.length == 4){
                        
                        if(podatkiPov[3].contains(".")){
                            nov = new RegionalniVlak(podatkiPov[0], zacetek, konec, Double.parseDouble(podatkiPov[3]));
                        }
                        else{
                            nov = new RegionalniVlak(podatkiPov[0], zacetek, konec, Integer.parseInt(podatkiPov[3]));
                        }

                        this.vlaki.add(nov);
                        zacetek.dodajOdhod(nov);
                    }
                    else if((podatkiPov[0].startsWith("IC") || podatkiPov[0].startsWith("EC")) && podatkiPov.length == 5){
                        
                        if(podatkiPov[3].contains(".")){
                            nov = new EkspresniVlak(podatkiPov[0], zacetek, konec, Double.parseDouble(podatkiPov[3]), Double.parseDouble(podatkiPov[4]));
                        }
                        else{
                            nov = new EkspresniVlak(podatkiPov[0], zacetek, konec, Integer.parseInt(podatkiPov[3]), Double.parseDouble(podatkiPov[4]));
                        }

                        this.vlaki.add(nov);
                        zacetek.dodajOdhod(nov);
                    }
                }
            }
            return true;

        } catch(FileNotFoundException e){
            return false;
        }
    }

    public boolean beriBin(String imeDatoteke){
        try(DataInputStream ds = new DataInputStream(new FileInputStream(new File(imeDatoteke)));) {
            
            int st=0;
            int min = 0;
            int dop = 0;
            String pov = "";
            int z;
            byte k;
            byte y;
 
            while (ds.available() > 0) {
                st++;

                if(st <= 6){
                    z = ds.readUnsignedByte();
                    if(z != 32){
                       pov += (char) z; 
                    }
                }
                else if(st == 7){
                    z = ds.readUnsignedByte();
                    pov += ";" + z;
                }
                else if(st == 8){
                    z = ds.readUnsignedByte();
                    pov += ";" + z;
                }
                else if(st <= 10){

                    k = ds.readByte();
                    y = ds.readByte();
                    st++;

                    int combined = (((k & 0xFF) << 8) | (y & 0xFF));

                    min = combined;

                    if(st == 10){
                        pov += ";" + min;
                    }
                }
                else if(st <= 12){
                    k = ds.readByte();
                    y = ds.readByte();
                    st++;

                    int combined = (((k & 0xFF) << 8) | (y & 0xFF));

                    if(!pov.startsWith("R")){
                        dop = combined;
                        if(st == 12){
                            pov += ";" + dop;
                        }
                    }
                }

                if(st == 12){
                    String[] podatki = pov.split(";");
                    int index1 = Integer.parseInt(podatki[1]) - 1;
                    int index2 = Integer.parseInt(podatki[2]) - 1;
                    int minute = Integer.parseInt(podatki[3]);

                    double ure = 0;
                    double doplacilo = 0;
                    
                    if(index1 < this.kraji.size() && index2 < this.kraji.size() && index1 != index2){
                        Kraj od = this.kraji.get(index1);
                        Kraj kam = this.kraji.get(index2);
                        Vlak nov;

                        if(minute > 60){
                            ure = minute/60 + (minute%60)/100.0;
                        }

                        if(podatki.length == 4){
                            if(ure == 0){
                                nov = new RegionalniVlak(podatki[0], od, kam, minute);
                            }
                            else{
                                nov = new RegionalniVlak(podatki[0], od, kam, ure);
                            }
                            this.vlaki.add(nov);
                        }
                        else if(podatki.length == 5){
                            doplacilo = Double.parseDouble(podatki[4]) / 100.0;

                            if(ure == 0){
                                nov = new EkspresniVlak(podatki[0], od, kam, minute, doplacilo);
                            }
                            else{
                                nov = new EkspresniVlak(podatki[0], od, kam, ure, doplacilo);
                            }
                            this.vlaki.add(nov);
                        }
                    }
                    // v navodilu ne piše nič, da tem krajem potem dodamo odhode, drugače je postopek isti kot pri branju navadnih povezav

                    pov = "";
                    min = 0;
                    dop = 0;
                    st = 0;
                }
            }
            return true;

          } catch (FileNotFoundException e) { 
            return false;
          }
          catch(IOException e){
            return false;
          }
    }

    private Kraj vrniKraj(String ime){
        int index1 = this.imenaKrajev.indexOf(ime);
        return this.kraji.get(index1);
    }

    public void izpisiKraje(){
        System.out.println("Kraji, povezani z vlaki:");
        for(Kraj i : kraji){
            System.out.println(i.toString());
        }
    }

    public void izpisiPovezave(){
        System.out.println("Vlaki, ki povezujejo kraje:");
        for(Vlak i : vlaki){
            System.out.println(i.toString());
        }
    }

    public void izpisiOdhodov(){
        System.out.println("Kraji in odhodi vlakov:");
        for(Kraj i : kraji){
            i.odhodi();
            System.out.println();
        }
    }

    public void izpisUrejen(){
        Collections.sort(this.kraji);

        System.out.println("Kraji in odhodi vlakov (po abecedi):");
        for(Kraj i : kraji){
            i.odhodiUrejeni();
            System.out.println();
        }
    }

    public void izlet(String ime, int k){
        int index = imenaKrajev.indexOf(ime);

        if(index < 0){
            System.out.printf("NAPAKA: podanega kraja (%s) ni na seznamu krajev.", ime);
        }
        else if(this.kraji.get(index).getOdhodi().size() == 0){
            System.out.printf("Iz kraja %s ni nobenih povezav.", this.kraji.get(index).toString());
        }
        else{
            Set<Kraj> vsi = this.kraji.get(index).destinacije(k);

            System.out.printf("Iz kraja %s lahko z max %d prestopanji pridemo do naslednjih krajev:%n", this.kraji.get(index).toString(), k);

            for(Kraj i : vsi){
                System.out.println(i.toString());
            }
        }
    }
}


public class DN11{

    public static void main(String[] args){

        EuroRail test = new EuroRail();

        switch(Integer.parseInt(args[0])){
            case 1:
                test.preberiKraje(args[1]);
                test.izpisiKraje();
                System.out.println();

                test.preberiPovezave(args[2]);
                test.izpisiPovezave();
                break;

            case 2:
                test.preberiKraje(args[1]);
                test.preberiPovezave(args[2]);
                test.izpisiOdhodov();
                break;


            case 3:
                test.preberiKraje(args[1]);
                test.preberiPovezave(args[2]);
                test.izpisUrejen();
                break;

            case 4:
                String imeMesta = args[4];
                for(int i = 5; i < args.length; i++){
                    imeMesta += " " + args[i];
                }

                test.preberiKraje(args[1]);
                test.preberiPovezave(args[2]);
                test.izlet(imeMesta, Integer.parseInt(args[3]));
                break;
            
            case 5:
                test.preberiKraje(args[1]);
                test.beriBin(args[2]);
                test.izpisiPovezave();
                break;
        }
    } 
} // :)