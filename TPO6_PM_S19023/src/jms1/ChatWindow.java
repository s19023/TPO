package jms1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatWindow extends JFrame
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new ChatWindow(args[0]));
    }

    private ChatWindow(String username)
    {
        setTitle("JMS Chat");
        setVisible(true);
        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JMSConnection jmsConnection = new JMSConnection(username);
        TextArea chatHistory = new TextArea();
        chatHistory.setEditable(false);
        add(chatHistory);
        TextField newMessageField = new TextField();
        add(newMessageField);
        Button sendButton = new Button("Send!");
        add(sendButton);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
                jmsConnection.disconnect();
            }
        });
    }


}
