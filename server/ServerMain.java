import java.io.*;
import java.net.*;
import java.util.Scanner;
 
public class ServerMain {
 
    public static void main(String[] args) throws InterruptedException, IOException {
        
        Scanner input = new Scanner(System.in);
        System.out.print("Port: ");
        int port = input.nextInt();
        System.out.println("Server listening on port " + port);
        input.close();
 
            MyServerSocket serverSocket = new MyServerSocket(port);

            //Noves connexions
            while(true) {
                Socket socket = serverSocket.accept();
                String user = serverSocket.addUser(socket);
                System.out.println(serverSocket.getUsers());

                //comprovem si l'username ja ha estat utilitzat
                if(user != null) {
                    serverSocket.write("addUser " + user, user);
                    serverSocket.sendUsers(user);

                    //Thread per llegir del nou client
                    Thread llegir = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String text;
                            try {
                                while((text = serverSocket.read(user)) != null) {
                                    System.out.println(text);
                                    serverSocket.write(user + ": " + text, user);
                                }
                                serverSocket.deleteUser(user);
                                serverSocket.write("removeUser " + user, "");
                                System.out.println(user + " disconnected");
                            } catch(IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    llegir.start();

                } else {
                    //notifiquem al client que l'username no és vàlid
                    OutputStream output = socket.getOutputStream();
                    PrintWriter write = new PrintWriter(output, true);

                    write.println("InvalidUsername");
                    socket.close();
                }

            }
    }
}