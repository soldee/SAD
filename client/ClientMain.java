import java.io.*;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.DefaultListModel;
import javax.swing.JTextArea;


public class ClientMain {

    private DefaultListModel<String> clients;
    public String text;
    public final Lock lock = new ReentrantLock();
    private JTextArea tArea;

    public ClientMain (DefaultListModel<String> clients, JTextArea tArea) {
        this.clients = clients;
        this.text = "";
        this.tArea = tArea;
    }

    public void MainClient(String host, int port, String nom) throws IOException, UnknownHostException, ConnectException {

        MySocket socket = new MySocket(host,port,nom);
        
        //Thread llegir de clientGrafic i enviar al server
        Thread llegirT = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        synchronized (lock) {
                            while (text.isEmpty())
                                lock.wait();
                            socket.escriure(text);
                            text = "";
                        }
                    } catch (IOException | InterruptedException e ) {
                        e.printStackTrace();
                    }
                }
            }
        });
        llegirT.start();

        
        //Thread llegir del server i escriure a consola
        Thread escriure = new Thread(new Runnable() {
            @Override
            public void run() {
                String text;   
                String[] control;
                try {
                    //sintaxis: INSTRUCCIO user1 user2 userx
                    while((text = socket.llegir())!=null){
                        control = text.split(" ");

                        switch (control[0]) {
                            case "addUser":
                                clients.addElement(control[1]);
                                tArea.append("\n\t ----- " + control[1] + " connected -----\n\n");
                                break;
                            case "removeUser":
                                System.out.println("f");
                                clients.removeElement(control[1]);
                                tArea.append("\n\t----- " + control[1] + " disconnected -----\n\n");
                                break;
                            case "sendUsers":
                                for (int i=1; i<control.length; i++)
                                    clients.addElement(control[i]);
                                break;
                            case "InvalidUsername":
                                System.out.println("Invalid Username");
                            default:
                                tArea.append(text + "\n");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        escriure.start();
    }
}