import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.text.DefaultCaret;



public class ClientGrafic extends JFrame implements ActionListener {

    private static DefaultListModel<String> clients;
    private JTextField tf;
    private static JTextArea tArea;
    private static ClientMain client;

    public ClientGrafic() {
    
        //Part esquerra (usuaris)
        JPanel esquerra = new JPanel();
        esquerra.setBorder(BorderFactory.createEmptyBorder(10,30,10,30));
        esquerra.setLayout(new BoxLayout(esquerra, BoxLayout.Y_AXIS));
        esquerra.setBackground(new Color(0,153,153));
        add(esquerra, BorderLayout.WEST);

        JLabel l = new JLabel("USERS");
        l.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
        l.setFont(new Font("SAN_SERIF", Font.PLAIN, 22));
        l.setForeground(Color.WHITE);
        esquerra.add(l);

        clients = new DefaultListModel<>();
        JList<String> jList = new JList<>(clients);
        jList.setSize(100, 400);
        jList.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
        jList.setForeground(Color.green);
        jList.setBackground(new Color(0,153,153));
        esquerra.add(jList);


        //Part central (missatges)
        JPanel central = new JPanel();
        central.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        central.setLayout(new BoxLayout(central, BoxLayout.PAGE_AXIS));
        add(central, BorderLayout.CENTER);

        tArea = new JTextArea(20, 20);
        tArea.setBackground(new Color(204,255,229));
        tArea.setFont(new Font("SAN_SERIF", Font.PLAIN, 17));
        tArea.setLineWrap(true);
        tArea.setWrapStyleWord(true);
        tArea.setEditable(false);
        DefaultCaret caret = (DefaultCaret)tArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        central.add(new JScrollPane(tArea));
        
        
        //Part inferior (escriure + boto)
        JPanel inferior = new JPanel();
        inferior.setLayout(new BoxLayout(inferior, BoxLayout.LINE_AXIS));
        inferior.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        add(inferior, BorderLayout.PAGE_END);

        tf = new JTextField(25);
        tf.setFont(new Font("SAN_SERIF", Font.PLAIN, 17));
        inferior.add(tf);

        JButton send = new JButton("SEND");
        send.setBackground(new Color(0,153,153));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        inferior.add(send);


        //frame
        getContentPane().setBackground(new Color(204,255,229));
        setResizable(false);
        setSize(600,600);
        setLocation(600,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    public void actionPerformed(ActionEvent event) {
        String text = tf.getText();
                if (!text.isEmpty()){
                    tArea.append("\t\tMe: " + text + "\n");
                    synchronized (client.lock) {
                        client.text = text;
                        client.lock.notifyAll();
                    }
                    tf.setText("");
                }
    }


    public static void main(String[] args) throws IOException {

        Scanner in = new Scanner(System.in);
        System.out.print("Server: ");
        String host = in.nextLine();
        System.out.print("Port: ");
        int port =  in.nextInt();
        System.out.print("Username: ");
        String username = in.next();

        new ClientGrafic();
        client = new ClientMain(clients, tArea);
        client.MainClient(host, port, username);
    }
}