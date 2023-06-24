import java.util.TreeSet;
import java.util.ArrayList;

public class DN10 {

    static TreeSet<String> getVsiPodnizi(String niz){

        TreeSet<String> mnozica = new TreeSet<String>();

        for(int i = 1; i <= niz.length(); i++){
            for(int j = 0; j < niz.length(); j++){
                if(j+i <= niz.length()){
                    System.out.println(niz.substring(j,j+i));
                    mnozica.add(niz.substring(j,j+i));
                }
                else{
                    break;
                }
            }
        }

        return mnozica;
    }

    static TreeSet<String> presek(ArrayList<TreeSet<String>> vsi){

        TreeSet<String> glavni = vsi.get(0);

        for(int i = 1; i < vsi.size(); i++){
            glavni.retainAll(vsi.get(i));
        }

        return glavni;
    }

    static void dolg(TreeSet<String> skupen){

        String najdalsi = skupen.first();

        for(String k : skupen){
            if(k.length() > najdalsi.length()){
                najdalsi = k;
            }
        }
        System.out.print(najdalsi);

    }


  
    public static void main(String[] args){

        ArrayList<TreeSet<String>> k = new ArrayList<TreeSet<String>>();

        System.out.println(getVsiPodnizi("abcabc"));
        
        for(int i = 0; i < args.length; i++){
            k.add(getVsiPodnizi(args[i]));
        }

        //dolg(presek(k));

    }
}