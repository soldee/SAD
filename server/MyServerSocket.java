import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class MyServerSocket extends ServerSocket {
    
    private Map<String, Socket> usuaris;

    public MyServerSocket (int port) throws IOException {
        super(port);
        usuaris = new HashMap<String, Socket>();
    }

    public void write (String text, String clientExclos) throws IOException {
        Socket socket;
        PrintWriter write;
        
        for (Map.Entry<String, Socket> entry : usuaris.entrySet()) {
            if (clientExclos != entry.getKey()){
                socket = entry.getValue();
                write = new PrintWriter(socket.getOutputStream(), true);
                write.println(text);
            }
        }
    }

    public String read (String user) throws IOException {
        Socket socket = usuaris.get(user);

        BufferedReader read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String text = read.readLine();
        return text;
    }

    public String addUser (Socket socket) throws IOException {
        //llegir nom
        BufferedReader read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String user = read.readLine();

        //comprovar que username no ha estat utilitzat
        if(!usuaris.containsKey(user)) {
            usuaris.put(user, socket);
            return user;
        }
        return null;
    }

    public void sendUsers(String user) throws IOException {
        Socket socket = usuaris.get(user);
        PrintWriter write = new PrintWriter(socket.getOutputStream(), true);

        String text = "sendUsers";
        for (String nick : usuaris.keySet())
            text += " " + nick;
        write.println(text);
    }

    public void deleteUser (String user) throws IOException {
        usuaris.remove(user);
    }

    public Map<String, Socket> getUsers () {
        return this.usuaris;
    }
}