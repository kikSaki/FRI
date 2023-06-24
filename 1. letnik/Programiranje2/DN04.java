public class DN04 {
    public static void main(String args[]){

        String[] b = args[0].split("(?<=\\G.{8})");
        
        String koncn = "";

        for(String el : b){

            int decimal = Integer.parseInt(el, 2);
            char znak = (char) decimal;

            koncn += String.valueOf(znak);
        }
 
        System.out.println(koncn);
    }
}
