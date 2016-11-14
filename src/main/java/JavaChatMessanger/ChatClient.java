package JavaChatMessanger;

import javafx.scene.input.KeyCode;

import javax.swing.*;
import java.awt.event.*;
import java.net.Socket;
import java.io.*;


/**
 * Created by ZuZ on 2016-08-15.
 */
public class ChatClient extends JFrame implements Runnable {

    Socket socket;
    JTextArea textArea;
    JButton send;
    JButton logout;
    JTextField textField;

    Thread thread;

    DataInputStream din;
    DataOutputStream dout;

    String LoginName;

    ChatClient(String login) throws IOException {
        super(login);
        LoginName = login;

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    dout.writeUTF(LoginName + " " + "LOGOUT");
                    System.exit(1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        textArea = new JTextArea(18, 50);
        textField = new JTextField(50);

        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    try {
                        if (textField.getText().length() > 0);
                        dout.writeUTF(LoginName + " " + "DATA " + textField.getText().toString());
                        textField.setText("");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        send = new JButton("SEND");
        logout = new JButton("Logout");

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (textField.getText().length()>0);
                    dout.writeUTF(LoginName + " " + "DATA" + textField.getText().toString());
                    textField.setText("");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dout.writeUTF(LoginName + " " + "LOGOUT");
                    System.exit(1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        socket = new Socket("localhost", 5220);

        din = new DataInputStream(socket.getInputStream());
        dout = new DataOutputStream(socket.getOutputStream());

        dout.writeUTF(LoginName);
        dout.writeUTF(LoginName + " LOGIN");

        thread = new Thread(this);
        thread.start();
        setup();
    }

    private void setup() {
        setSize(600, 400);
        JPanel panel = new JPanel();

        panel.add(new JScrollPane(textArea));
        panel.add(textField);
        panel.add(send);
        panel.add(logout);
        add(panel);
        setVisible(true);
    }


    @Override
    public void run() {
        while (true) {
            try {
                textArea.append("\n" + din.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
