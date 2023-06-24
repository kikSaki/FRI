import java.util.Scanner;
import java.io.File;

class Planet{

    private String ime;
    private int radij;

    //konstruktor
    Planet(String ime, int radij){
        this.ime = ime;
        this.radij = radij;
    }


    //getter
    public String getIme(){
        return this.ime;
    }
    public int getRadij(){
        return this.radij;
    }

    //metoda
    double povrsina(){
        return 4 * Math.PI * (this.radij*this.radij);
    }

}


public class DN08 {

    static Planet[] podatki(String dat) throws Exception{

        File f = new File(dat);
        String[] tab = new String[2];

        Planet[] planeti = new Planet[8];

        Scanner data = new Scanner(f);

        for(int i = 0; data.hasNextLine(); i++){
            tab = data.nextLine().split(":");

            Planet p = new Planet(tab[0], Integer.parseInt(tab[1]));

            planeti[i] = p;

        }
        data.close();

        return planeti;
    }  

    static void povrsina(String planet, Planet[] planeti){

        long povrsina = 0;

        String niz = planet.toLowerCase();

        for(int i = 0; i < planeti.length; i++){
            if(niz.contains(planeti[i].getIme().toLowerCase())){

                povrsina += planeti[i].povrsina();
            }
        }
        System.out.print("Povrsina planetov \"" + planet + "\" je " + povrsina / 1000000 + " milijonov km2");
    }
    
    public static void main(String[] args) throws Exception{


        Planet[] planeti = podatki(args[0]);

        /* 
        for(int i = 0; i < planeti.length; i++){
            System.out.println(planeti[i].getIme() + " : " + planeti[i].getRadij());
        }
        */

        povrsina(args[1], planeti);
    }
}