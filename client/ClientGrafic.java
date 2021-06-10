import java.awt.event.*;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;


public class ClientGrafic extends JFrame implements ActionListener{

    JPanel esquerra;
    JPanel inferior;

    ClientGrafic() throws IOException {

        Scanner in = new Scanner(System.in);
        String host = "localhost";
        int port =  5050;
        System.out.print("Introduce username: ");
        String nom = in.nextLine();


        //Part central (missatges)
        JTextArea tArea = new JTextArea(20,20);
        //tArea.setLayout(null);
        //tArea.setBackground(new Color(204,255,229));
        tArea.setBounds(165, 15, 420, 470);
        tArea.setFont(new Font("SAN_SERIF", Font.PLAIN, 17));
        tArea.setLineWrap(true);
        tArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(tArea);
        scroll.setLayout(null);
        scroll.setBounds(160,10,430,480);
        add(tArea);
        add(scroll);


        DefaultListModel<String> clients = new DefaultListModel<>();
        ClientMain client = new ClientMain(clients, tArea);
        client.MainClient(host, port, nom);

        
        //Part inferior (escriure + botó)
        inferior = new JPanel();
        inferior.setLayout(null);
        inferior.setBackground(Color.WHITE);
        inferior.setBounds(0, 500, 600, 150);
        add(inferior);

        JButton send = new JButton("SEND");
        send.setBounds(490,10, 100, 50);
        inferior.add(send);

        JTextField tf = new JTextField(40);
        tf.setBounds(10,15,450,40);
        tf.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
        inferior.add(tf);

        //Send button handler
        send.addActionListener(new ActionListener (){
            public void actionPerformed (ActionEvent e){
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
        });


        //Part esquerra (usuaris)
        esquerra = new JPanel();
        esquerra.setLayout(null);
        esquerra.setBackground(new Color(0,153,153));
        esquerra.setBounds(0, 0, 150, 600);
        add(esquerra);

        JList<String> jList = new JList<>(clients);
        jList.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
        jList.setForeground(Color.green);
        jList.setBounds(30,60,100,400);
        esquerra.add(jList);

        JLabel l = new JLabel("USERS");
        l.setFont(new Font("SAN_SERIF", Font.PLAIN, 22));
        l.setForeground(Color.WHITE);
        l.setBounds(30,20, 100, 20);
        esquerra.add(l);
        

        getContentPane().setBackground(new Color(204,255,229));
        setLayout(null);
        setSize(600,600);
        setLocation(600,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    public static void main (String args[]) throws IOException {
        new ClientGrafic().setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {        
    }

}
