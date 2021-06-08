import java.io.*;
import java.util.Scanner;


public class ClientMain {

    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);
        
        System.out.print("Username: ");
        String nom = input.next();
        System.out.print("Host: ");
        String host = input.next();
        System.out.print("Port: ");
        int port = input.nextInt();


        MySocket socket = new MySocket(host,port,nom);
        
        //Thread llegir de consola i enviar al server
        Thread llegirT = new Thread(new Runnable() {
            @Override
            public void run() {
                String linia;
                BufferedReader llegir = new BufferedReader(new InputStreamReader(System.in));
                
                try {
                    while ((linia = llegir.readLine()) != null) {
                        socket.escriure(linia);
                    }
                    socket.close();
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        });
        llegirT.start();

        //Thread llegir del server i escriure a consola
        String text;   
        while((text = socket.llegir())!=null){
            System.out.println(text);
        }
    }
}