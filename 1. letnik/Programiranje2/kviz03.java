import java.util.TreeMap;

/**
 * kviz03
 */


class Tocka{

    private int x;
    private int y;


    public Tocka(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return this.y;
    }
    public void setY(int y) {
        this.y = y;
    }

    static Tocka[] preberiTocke(String imeDatoteke){

        java.util.ArrayList<Tocka> tabela = new java.util.ArrayList<>();

        try(java.util.Scanner sc = new java.util.Scanner(new java.io.File(imeDatoteke))){

            while(sc.hasNextLine()){
                String tocka = sc.nextLine();

                String[] koordinati = tocka.split(" ");

                if(koordinati.length == 2){
                    tabela.add(new Tocka(Integer.parseInt(koordinati[0]), Integer.parseInt(koordinati[1])));
                }
            }

        } catch(java.io.FileNotFoundException e){
            e.printStackTrace();
        }

        
        Tocka[] vse = new Tocka[tabela.size()];
        for(int i = 0; i < tabela.size(); i++){
            vse[i] = tabela.get(i);
        }
        
        return vse;
    }

    static String tabelaToString(Tocka[] tocke){

        if(tocke.length == 0){
            return "[]";
        }

        StringBuilder a = new StringBuilder();

        for(Tocka i : tocke){
            a.append("("+i.getX()+","+i.getY()+"), ");
        }

        return String.format("[%s]", a.toString().substring(0, a.length()-2));
    }

    private static double razdalja(Tocka prva, Tocka druga){
        String k = String.format("%.2f", java.lang.Math.sqrt((prva.getX() - druga.getX()) * (prva.getX() - druga.getX()) + (prva.getY() - druga.getY()) * (prva.getY() - druga.getY())));
        return Double.parseDouble(k);
    }

    static void najblizji(Tocka[] t1, Tocka[] t2){

        if(t1.length == 0){
            System.out.print("Prva tabela ne vsebuje točk");
        }
        else if(t2.length == 0){
            System.out.print("Druga tabela ne vsebuje točk");
        }
        else{
            Tocka[] najblizji = {t1[0], t2[0]};
            double razdaljaT = razdalja(t1[0], t2[0]);

            for(Tocka i : t1){
                for(Tocka j : t2){
                    if(razdalja(i,j) < razdaljaT){
                        najblizji[0] = i;
                        najblizji[1] = j;
                        razdaljaT = razdalja(i,j);
                    }
                }
            }
            System.out.printf("Najbližji točki sta Tocka (%d,%2d) in Tocka (%d,%2d), razdalja med njima je %.2f",
                            najblizji[0].getX(), najblizji[0].getY(), najblizji[1].getX(), najblizji[1].getY(), razdaljaT);
        }
    }
}


class Matrika{

    private int[][] matrix;
    private int velikost;


    Matrika(int[][] matrix, int velikost){
        this.matrix = matrix;
        this.velikost = velikost;
    }

    static Matrika preberiMatriko(String imeDatotake){

        try(java.util.Scanner sc = new java.util.Scanner(new java.io.File(imeDatotake))){

            int velikost = Integer.parseInt(sc.nextLine());
            int st = 0;

            int[][] mat = new int[velikost][velikost];

            while(sc.hasNextLine()){

                String koe = sc.nextLine();
                String[] posebej = koe.split(" ");

                int[] noter = new int[velikost];

                for(int i = 0; i < velikost; i++){
                    noter[i] = Integer.parseInt(posebej[i]);
                    
                }
                mat[st] = noter;
                st++;

            }

            return new Matrika(mat, velikost);

        } catch(java.io.FileNotFoundException e){
            e.getMessage();
        }
        return null;
  
    }

    public Matrika zmnozi(Matrika b){

        int[][] nova = new int[this.velikost][this.velikost];

        for(int i = 0; i < this.velikost; i++){
            for(int j = 0; j < this.velikost; j++){
                

                for(int k = 0; k < this.velikost; k++){
                    nova[i][j] += this.matrix[i][k] * b.matrix[k][j];
                }
            }
        }
        return new Matrika(nova, velikost);
    }


    public void izpisi(){
        for(int[] i : this.matrix){
            for(int j : i){
                System.out.printf("%3d", j);
            }
            System.out.println();
        }
    }


}



public class kviz03 {


    static boolean jeAnagram(String prvaBesede, String drugaBeseda, boolean zanemariVelikost){

        if(prvaBesede.equals(drugaBeseda) || prvaBesede.length() != drugaBeseda.length()){
            return false;
        }

        if(zanemariVelikost == true){
            prvaBesede = prvaBesede.toLowerCase();
            drugaBeseda = drugaBeseda.toLowerCase();
        }

        for(int i = 0; i < prvaBesede.length(); i++){
            if(prvaBesede.contains(String.valueOf(drugaBeseda.charAt(i)))){
                prvaBesede.replace(String.valueOf(drugaBeseda.charAt(i)), "");
            }
            else{
                return false;
            }
        }

        return true;
    }


    static void preberiInIzpisi(String oznaka){
        java.util.TreeMap<String, String> stud = new java.util.TreeMap<>();
        java.util.HashMap<String, Integer> tocke = new java.util.HashMap<>();


        try(java.util.Scanner prijave = new java.util.Scanner(new java.io.File(oznaka + "-prijave.txt"));
            java.util.Scanner n1 = new java.util.Scanner(new java.io.File(oznaka + "-n1.txt"));
            java.util.Scanner n2 = new java.util.Scanner(new java.io.File(oznaka + "-n2.txt"));
            java.util.Scanner n3 = new java.util.Scanner(new java.io.File(oznaka + "-n3.txt"));
            java.util.Scanner n4 = new java.util.Scanner(new java.io.File(oznaka + "-n4.txt"));){

            
            while(prijave.hasNextLine()){
                String a = prijave.nextLine();
                String[] podatka = a.split(":");
                if(podatka.length == 2){
                    stud.put(podatka[1], podatka[0]);
                }
            }

            while(n1.hasNextLine()){
                String naloga1 = n1.nextLine();
                String[] pod1 = naloga1.split(":");

                if(pod1.length == 2){
                   tocke.put(pod1[0], Integer.parseInt(pod1[1])); 
                }
                
            }

            while(n2.hasNextLine()){
                String naloga2 = n2.nextLine();
                String[] pod2 = naloga2.split(":");
                if(pod2.length == 2){

                    if(tocke.get(pod2[0]) == null){
                        tocke.put(pod2[0], Integer.parseInt(pod2[1]));
                    }
                    else{
                        int t2 = tocke.get(pod2[0]);
                        tocke.put(pod2[0], Integer.parseInt(pod2[1]) + t2);
                    }

                }
            }

            while(n3.hasNextLine()){
                String naloga3 = n3.nextLine();
                String[] pod3 = naloga3.split(":");

                if(pod3.length == 2){

                    if(tocke.get(pod3[0]) == null){
                        tocke.put(pod3[0], Integer.parseInt(pod3[1]));
                    }
                    else{
                        int t3 = tocke.get(pod3[0]);
                        tocke.put(pod3[0], Integer.parseInt(pod3[1]) + t3);
                    }
                }
            }

            while(n4.hasNextLine()){
                String naloga4 = n4.nextLine();
                String[] pod4 = naloga4.split(":");

                if(pod4.length == 2){

                    if(tocke.get(pod4[0]) == null){
                        tocke.put(pod4[0], Integer.parseInt(pod4[1]));
                    }
                    else{
                        int t4 = tocke.get(pod4[0]);
                        tocke.put(pod4[0], Integer.parseInt(pod4[1]) + t4);
                    }
                }
            }

            for(String imena : stud.keySet()){
                if(tocke.get(stud.get(imena)) == null){
                    System.out.printf("%s:%s:%s%n", stud.get(imena), imena, "VP");
                }
                else{
                   System.out.printf("%s:%s:%d%n", stud.get(imena), imena, tocke.get(stud.get(imena))); 
                }
                
            }


        } catch(java.io.FileNotFoundException e){
            e.getMessage();
        }
    }


    static int countWords(String fileName){

        try(java.util.Scanner sc = new java.util.Scanner(new java.io.File(fileName))){
            int st = 0;

            while(sc.hasNextLine()){
                String k = sc.nextLine();
                String[] besede = k.split(" ");

                st += besede.length;
            }

            return st;


        } catch(java.io.FileNotFoundException e){
            return -1;
        }
    }



    public static void main(String[] args) {
        
    }
}