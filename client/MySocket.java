import java.net.*;
import java.io.*;

public class MySocket extends Socket {
    private InputStream inputstream;
    private OutputStream outputstream;
    private BufferedReader read;
    private PrintWriter write;
    public String nom;

    public MySocket(String host, int port, String nom) throws IOException { 
        super(host,port);
        this.nom = nom;

        inputstream = this.getInputStream();
        outputstream = this.getOutputStream();
        
        read = new BufferedReader(new InputStreamReader(inputstream));
        write = new PrintWriter(outputstream,true);
        
        escriure(nom);       
    }

    public String llegir() throws IOException{ //llegir el que arriba del servidor
        String miss = read.readLine();
        return miss;
    }

    public void escriure(String outputstream) throws IOException{ //escriu al servidor
        write.println(outputstream);
    }
}
