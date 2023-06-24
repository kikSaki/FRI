public class DN05 {

        static String dajVseString(String[] arg){
            StringBuffer str = new StringBuffer("");

            for(int i = 0; i < arg.length; i++){
                str.append(arg[i]);
                if(i+1 != arg.length){
                    str.append(" ");
                }
            }

            return str.toString();
        }

        static int[] tabelaVseh(String str){
            int[] stevke = new int[10];

            for(int i = 0; i < str.length(); i++){
                if (Character.isDigit(str.charAt(i))){
                    stevke[Integer.parseInt(String.valueOf(str.charAt(i)))]++;
                }
            }

            return stevke;
        }

        static int najdiMax(int[] stevke){
            int max = 0;

            for(int i = 0; i < stevke.length; i++){
                if(stevke[i] > max){
                    max = stevke[i];
                }
            }

            return max;
        }

        static void izpis(int max, int[] stevke, String str){
            if(max == 0){
                System.out.println("V nizu '" + str + "' ni stevk");
            }
            else{
                System.out.print("'" + str + "'"+" -> ");
                for (int i = 0; i < stevke.length; i++) {
                    if(stevke[i] == max){
                        System.out.print(i + " ");
                    }
                }
                System.out.print("(" + max + ")");
            }
        }

    public static void main(String args[]){

        String str = dajVseString(args); 

        int[] stevke = tabelaVseh(str);

        int max = najdiMax(stevke);

        izpis(max, stevke, str);
    }
}
