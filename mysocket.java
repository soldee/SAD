
package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.io.*;

public class mysocket extends Socket{
    private InputStream inputstream;
    private OutputStream outputstream;
    private BufferedReader read;
    private PrintWriter write;
    public String nom;

public mysocket()throws IOException{

    inputstream= getInputStream();
    outputstream=getOutputStream();
    
    read= new BufferedReader(new InputStreamReader(inputstream));
    write= new PrintWriter(outputstream,true);
    
    enviarusuari();
}



    public String llegir() throws IOException{ //llegir el que arriba del servidor

        String miss= read.readLine();
       
        return miss;
    }

    public void escriure(String  outputstream) throws IOException{ //escriu al servidor

        write.println(outputstream);
    }
    public void enviarusuari() throws IOException{ 

        escriure(nom);
    }
     public static void main(String[] args) {
     
     
     }
}
