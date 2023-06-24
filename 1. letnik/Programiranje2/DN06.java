public class DN06 {
    
    static int bsdChecksum(String niz) {    
        int checksum = 0;
        for (int i = 0; i<niz.length(); i++) {
          checksum = (checksum >> 1) + ((checksum & 1) << 15);
          checksum += niz.charAt(i);
          checksum &= 0xffff;       
        }
        return checksum;
     }
 
    static String povecaj(String niz){

        char a = niz.charAt(niz.length()-1);

        int nasledni = (int)a + 1;

        char b = (char)nasledni;

        StringBuffer sb = new StringBuffer(niz);
        sb.replace(niz.length()-1, niz.length(), String.valueOf(b));
        
        return sb.toString();
   
        /*
         * 
         *      if(b <= 'z'){
         *          return sb.toString();
         *      }
         *      else{
         *          return povecaj(sb.deleteCharAt(sb.length()-1).toString()) + 'a';
         *      }
         * 
         * 
         *      TOLE BI BILA REKURZIJA 
         * 
         */

    }

    public static void main(String[] args){
        
        String a = "a"; // uporabljen za .repeat, najbrz se da drgac
        String arg = args[0];

        StringBuffer bsd = new StringBuffer();
        for(int i = 0; i < arg.length(); i++){
            bsd.append("a");
        }

        String glej = bsd.toString();

        /* 
         *    while(true){
         *
         *            if(bsdChecksum(arg) == bsdChecksum(glej)){
         *                System.out.print(glej);
         *                break;
         *            }
         *            glej = povecaj(glej);
         *        }
         *
         *   TO JE POTREBNO ZA UPORABO Z REKURZIJO
         *
         * 
         *   v primeru rekurzije to pod tem ni potrebno
         * 
         */
        
        for(int i = glej.length()-1; i >= 0; i--){ //pojdi čez vse črke v stringu a-jev, aaaaaa
                       
            for(int j = glej.length()-1; j >= i; j--){ //pojdi cez vse, ki so za črko v prejšnjem loopu aaaa(<- ta je trenuten), grem čez 2 zadnja

                while(true){
                
                    if(glej.charAt(j) < 'z'){
                        glej = povecaj(glej.substring(0, j+1)) + a.repeat((glej.length()-1)-j);
                    }
                    else{
                        break;
                    }

                    if(j < glej.length()-1){
                        j = glej.length()-1;
                    }

                    if(bsdChecksum(arg) == bsdChecksum(glej)){
                        System.out.print(glej);
                        break; 
                    }
                }    

                if(bsdChecksum(arg) == bsdChecksum(glej)){
                    break; 
                }
            }

            if(bsdChecksum(arg) == bsdChecksum(glej)){
                break; 
            }
        }
    }
}