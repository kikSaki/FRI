public class DN02 {
    public static void main(String[] args){
        if(args.length == 0){
            System.out.println("Napaka pri uporabi programa!");
        }
        else{

            String a = "* " + String.join(" ", args) + " *";

            for(int i = 0; i<a.length();i++){
                System.out.print("*");
            }

            System.out.print("\n"+ a +"\n");
            
            for(int i = 0; i<a.length();i++){
                System.out.print("*");
            } 
        }
    }  
} 
