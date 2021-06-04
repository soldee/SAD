package client;
import java.io.*;
import java.net.*;
import java.io.IOException;




public class thread1 implements Runnable {
     private OutputStream outputstream;
     private mysocket socket;
     
     public thread1() throws IOException{
    
     }
     @Override
    
   public void run() {
       String linia;
       BufferedReader llegir = new BufferedReader(new InputStreamReader(System.in));
    try{
         
             while ((linia = llegir.readLine()) != null){
             
                 socket.escriure(linia);
             };
             
           
        }catch(IOException i){
           System.err.println("Error "); 
        }
   
   } 
}
