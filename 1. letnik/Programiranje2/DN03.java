import java.util.Scanner;

import javax.swing.tree.ExpandVetoException;

import java.io.File;
import java.util.Random;

public class DN03 {


    public static void main(String[] args)throws Exception{

        String geslo = "";

        Scanner dat = new Scanner(new File(args[0]));
        Random rng = new Random(Integer.parseInt(args[2]));

        int st = dat.nextInt();
        dat.nextLine();

        String[] notri = new String[st];

        for(int i = 0; dat.hasNextLine(); i++){
            String vrstica = dat.nextLine();
            notri[i] = vrstica;
        }

        for(int i = 0; i < Integer.parseInt(args[1]); i++){
            int bes = rng.nextInt(st);
            int not = rng.nextInt(notri[bes].length());
            geslo += (char) notri[bes].charAt(not);
        
        }        
        
        dat.close();
        System.out.print(geslo);
    }
}
