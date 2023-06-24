interface Lik {
    public double obseg();
  }


class Pravokotnik implements Lik{
    private int a;
    private int b;

    Pravokotnik(int a, int b){
        this.a = a;
        this.b = b;
    }

    public double obseg(){
        return 2 * this.a + 2 * this.b;
    }

}

class Kvadrat implements Lik{
    private int a;

    Kvadrat(int a){
        this.a = a;
    }

    public double obseg(){
        return 4 * this.a;
    }


}

class NKotnik implements Lik{
    private int a;
    private int n;

    NKotnik(int n, int a){
        this.n = n;
        this.a = a;
    }

    public double obseg(){
        return this.n * this.a;
    }

}

public class DN09 {

    static int skupniObseg(Lik[] liki){

        int obseg = 0;
        
        for(int i = 0; i < liki.length; i++){
            obseg += liki[i].obseg();
        }

        return obseg;
    }

    public static void main(String[] args){

        Lik[] liki = new Lik[args.length];

        for(int i = 0; i < liki.length; i++){
            String[] podatki = args[i].split(":");

            switch (podatki[0].toLowerCase()) {
                case "kvadrat":
                    liki[i] = new Kvadrat(Integer.parseInt(podatki[1]));
                    break;
            
                case "pravokotnik":
                    liki[i] = new Pravokotnik(Integer.parseInt(podatki[1]), Integer.parseInt(podatki[2]));
                    break;

                case "nkotnik":
                    liki[i] = new NKotnik(Integer.parseInt(podatki[1]), Integer.parseInt(podatki[2]));
                    break;
            }

            //System.out.println(liki[i].obseg());
        }

        System.out.print(skupniObseg(liki));

    }

    
}