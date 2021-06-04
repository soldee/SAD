
package client;

import java.net.*;
import java.io.*;

public class mysocket extends Socket{
    private InputStream inputstream;
    private OutputStream outputstream;
    private BufferedReader read;
    private PrintWriter write;
    public String nom;

public mysocket(String host, int port, String nom)throws IOException{ 
    super(host,port);
   this.nom=nom;

    inputstream= getInputStream();
    outputstream=getOutputStream();
    
    read= new BufferedReader(new InputStreamReader(inputstream));
    write= new PrintWriter(outputstream,true);
    demanarconnexio();
     
}



    public String llegir() throws IOException{ //llegir el que arriba del servidor
       
        String miss= read.readLine();
        if(miss=="nomclient"){
            enviarnom(); // quan rebi el missatge "server" se li passarà el nom el client
        }
        return miss;
    }

    public void escriure(String  outputstream) throws IOException{ //escriu al servidor

        write.println(outputstream);
    }
    public void enviarnom() throws IOException{ 

        escriure(nom);
    }
    public void demanarconnexio() throws IOException{
        String s= "demanarconexio";
        escriure(s);
}
     
  

 
    public static void main(String[] args) throws IOException {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String nom= args[2];
        
        mysocket socket=new mysocket(host,port,nom);
        Thread t = new Thread(new thread1());
        t.start();
        String text= socket.llegir();
        while(text!=null){
            System.out.println(text);
        }
    }
}

