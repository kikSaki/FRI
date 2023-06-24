import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DN07 {

    static void izpisi_datoteke(File f){

        File[] datoteke = f.listFiles();
        String[] imena = f.list();

        for (int i = 0 ; i < datoteke.length; i++){

            double velikost = datoteke[i].length()/1000.0;
             
            if(datoteke[i].isDirectory()){
                System.out.printf("%20s%20s%10.3f%n", imena[i], "Mapa", velikost);
            }
            else{
                System.out.printf("%20s%20s%10.3f%n", imena[i], "Datoteka", velikost);
            }
        }
    }

  
    static void najvecja_datoteka(File f){

        File[] datoteke = f.listFiles();
        String[] imena = f.list();

        double velikost = datoteke[0].length()/1000.0;

        double max[] = {0, 0};
        double min[] = {0, velikost};

        for (int i = 1 ; i < datoteke.length; i++){

            velikost = datoteke[i].length()/1000.0;
             
            if(datoteke[i].isFile()){
                
                if(velikost > max[1]){
                    max[0] = i;
                    max[1] = velikost;
                }
                else if(velikost < min[1]){
                    min[0] = i;
                    min[1] = velikost;
                }
            }
        }
        System.out.printf("%s%6.3f%n", imena[(int)max[0]], max[1]);
        System.out.printf("%s%6.3f%n", imena[(int)min[0]], min[1]);
    }


    static void izpis_vsebine(File f, int n) throws Exception{

        File[] datoteke = f.listFiles();
        String[] imena = f.list();

        for (int i = 0 ; i < datoteke.length; i++){
             
            if(datoteke[i].isFile()){
                if(imena[i].contains(".txt")){
                    Scanner trenutna = new Scanner(datoteke[i]);

                    System.out.printf("%s%n", imena[i]);

                    for(int j = 0; j < n && trenutna.hasNextLine(); j++){

                        //Lahko bi rešil tako kot je v komentarju, da dodaš 4 presledke pred string katerekoli dolžine,
                        //ampak lahko samo na začetek dodam 4 presledke in dobimisti rezultat

                        //String vrstica = trenutna.nextLine();
                        //int dolzina = vrstica.length() + 4;
                        //System.out.printf("%"+dolzina+"s%n", vrstica);

                        System.out.printf("    %s%n", trenutna.nextLine());

                    }
                    trenutna.close();
                
                }
                else{
                    System.out.printf("%s %s%n", imena[i], "(ni tekstovna datoteka)");
                }
            }
        }
    }


    static void kopiraj_datoteko(String vhodnaDatoteka, String izhodnaDatoteka) throws Exception{

        File f = new File(izhodnaDatoteka);

        f.createNewFile();  // ta funkcija sama preveri, ali datoteka že obstaja, saj vrne boolean vrednost
                            // če ne obstaja, jo pač kreira

        if(f.length() == 0){

            FileWriter pisi = new FileWriter(f);
            Scanner beri = new Scanner(new File(vhodnaDatoteka));

            while(true){                                // lahko while(beri.hasNextLine()){itd}
                pisi.write(beri.nextLine());            // ampak bi bil notri isti pogoj, da bi gledal za zadnjo vrstico,
                                                        // kjer nočem "newline \n", saj ga tudi v originalni datoteki ni
                if(beri.hasNextLine()){
                    pisi.write("\n");
                }
                else{
                    break;
                }
            }

            beri.close();
            pisi.close();
        }
        else{
            System.out.print("Napaka pri kopiranju, datoteka že vsebuje besedilo");
        }
    }


    static void zdruzi_datoteko(File direktorij, String izhodnaDatoteka) throws Exception{

        File[] datoteke = direktorij.listFiles();
        String[] imena = direktorij.list();

        File f = new File(izhodnaDatoteka);             //datoteka bo narejena v direktoriju, ki je podan kot argument, torej npr. .naloga/datoteke,
                                                        //saj v navodilih ne piše kje naj se ustvari
        f.createNewFile();                              //tudi ni posebnih navodil glede tega, če datoteka že obstaja in notri karkoli piše
        FileWriter skupni = new FileWriter(f);

        for(int i = 0; i < datoteke.length; i++){

            if(imena[i].endsWith(".txt")){
                Scanner beri = new Scanner(datoteke[i]);

                while(beri.hasNextLine()){
                    skupni.write(beri.nextLine() + "\n");
                }

                beri.close();
            }
        }
        skupni.close();

        if(f.length() == 0){
            f.delete();
            System.out.print("Direktorij ne vsebuje tekstovnih datotek.");
        }
    }


    static void najdiVDatotekah(File f, String iskanNiz) throws Exception{

        File[] datoteke = f.listFiles();
        String[] imena = f.list();

        for(int i = 0; i < datoteke.length; i++){

            if(imena[i].endsWith(".txt")){

                Scanner beri = new Scanner(datoteke[i]);

                for(int j = 1; beri.hasNextLine(); j++){          

                    String vrstica = beri.nextLine();

                    if(vrstica.contains(iskanNiz)){
                        System.out.printf("%s %d: %s%n", imena[i], j, vrstica);
                    }
                }

                beri.close();
            }
        }
    }

    static void drevo(File f){
        
        drevo(f, "    ");
    }

    private static void drevo(File f, String zamik){
        
        File[] datoteke = f.listFiles();
        String[] imena = f.list();

        System.out.printf("/%s%n", f.getName());

        for(int i = 0; i < imena.length; i++){

            if(datoteke[i].isFile()){

                System.out.printf("%s%s%n", zamik, imena[i]);
            }
            else{
                System.out.printf("%s",zamik);
  
                drevo(datoteke[i], zamik + "    ");    
            }
        }
    }


    static void resiMatematicneIzraze(File f) throws Exception{

        File[] datoteke = f.listFiles();
        String[] imena = f.list();
        char znak;
       // char digitPo, digitPred;
        boolean jeRacun = false;

        int rezultat = 0;

        for(int i = 0; i < imena.length; i++){

            if(datoteke[i].isFile() && imena[i].endsWith(".txt")){

                System.out.println(imena[i]);
                Scanner beri = new Scanner(datoteke[i]);

                while(beri.hasNextLine()){          

                    String vrstica = beri.nextLine();

                    /* 
                    if(vrstica.contains("+")){

                        znak = (char) vrstica.indexOf("+");
                        digitPred = vrstica.charAt(znak-1);
                        digitPo = vrstica.charAt(znak+1);

                        if(Character.isDigit(digitPred) && Character.isDigit(digitPo)){
                            jeRacun = true;
                        }
                    }
                    else if(vrstica.contains("-")){

                        znak = (char) vrstica.indexOf("-");
                        digitPred = vrstica.charAt(znak-1);
                        digitPo = vrstica.charAt(znak+1);

                        if(Character.isDigit(digitPred) && Character.isDigit(digitPo)){
                            jeRacun = true;
                        }
                    }
                    */

                    for(int j = 0; j < vrstica.length(); j++){                       //tale je malo lepša rešitev,
                        if(j%2 == 0){                                                //saj preveri celotno vrstico,
                            if(!Character.isDigit(vrstica.charAt(j))){               //če se slučajno kje skriva kakšna črka ipd.
                                jeRacun = false;
                                break;
                            }
                        }
                        else if(!(vrstica.charAt(j) == '+' || vrstica.charAt(j) == '-')){
                            jeRacun = false;
                            break;
                        }

                        jeRacun = true;
                    }


                    if(jeRacun == true){
                        System.out.print("  ");
                        for(int j = 0; j < vrstica.length(); j+=2){

                            if(j == 0){
                                System.out.print(vrstica.charAt(j));
                                rezultat += Character.getNumericValue(vrstica.charAt(j));
                            }
                            else{
                                znak = vrstica.charAt(j-1);

                                if(znak == '+'){
                                    rezultat += Character.getNumericValue(vrstica.charAt(j));
                                    System.out.print("+" + vrstica.charAt(j));
                                }
                                else if(znak == '-'){
                                    rezultat -= Character.getNumericValue(vrstica.charAt(j));
                                    System.out.print("-" + vrstica.charAt(j));
                                }
                            }
                        }
                        System.out.println("=" + rezultat);
                    }

                    jeRacun = false;
                    rezultat = 0;
                }

                beri.close();
            }
            else if(datoteke[i].isFile()){
                System.out.println(imena[i]); //v navodilih sicer piše samo tekstovne datoteke, v testih pa je vključen tudi .exe
            }
        }
    }
    

    static void nNajvecjih(File f, int n){

        HashMap<String, Long> vseDat = new HashMap<String, Long>();

        vseDat = vseMozne(f, vseDat);

        HashMap<String, Long> temp = new HashMap<String, Long>();

        HashMap<String, Long> maxDat = nNajvecjih(temp, vseDat, n);

        ArrayList<String> keys = new ArrayList<String>();

        for(String i : maxDat.keySet()){
            keys.add(i);
        }

        for(int i = keys.size()-1; i >= 0; i--){

            System.out.printf("%s %d%n", keys.get(i), maxDat.get(keys.get(i)));
        }

    }

    private static HashMap<String, Long> vseMozne(File f, HashMap<String, Long> dat){
        
        File[] datoteke = f.listFiles();
        String[] imena = f.list();

        for(int i = 0; i < imena.length; i++){

            if(datoteke[i].isFile()){
                dat.put(imena[i], datoteke[i].length());
            }
            else{
                vseMozne(datoteke[i], dat);  
            }
        }

        return dat;
    }

    private static HashMap<String, Long> nNajvecjih(HashMap<String, Long> maxDat, HashMap<String, Long> vseDat, int n){

        long max = 0;
        String key = " ";

        if(n == 0){
            return maxDat;
        }

        for (String i : vseDat.keySet()) {

            if(vseDat.get(i) > max){
                key = i;
                max = vseDat.get(i);
            }
        }

        maxDat.put(key, vseDat.get(key));
        vseDat.remove(key);

        return nNajvecjih(maxDat, vseDat, n-1);
    }

    
    public static void main(String[] args) throws Exception{

        File imenik = new File(args[1]);
        int metoda = Integer.parseInt(args[0]);

        String relPot = args[1] + "/";

        switch(metoda){
            case 1:
                izpisi_datoteke(imenik);
                break;
        
            case 2:
                najvecja_datoteka(imenik);
                break;

            case 3:
                izpis_vsebine(imenik, Integer.parseInt(args[2]));
                break;
            
            case 4:
                kopiraj_datoteko(relPot + args[2], relPot + args[3]);
                break;

            case 5:
                zdruzi_datoteko(imenik, relPot + args[2]);
                break;

            case 6: 
                najdiVDatotekah(imenik, args[2]);
                break;

            case 7:
                drevo(imenik);
                break;

            case 8:
                resiMatematicneIzraze(imenik);
                break;

            case 9:
                nNajvecjih(imenik, Integer.parseInt(args[2]));
                break;
            
            default:

        }
    }
}  //  :)