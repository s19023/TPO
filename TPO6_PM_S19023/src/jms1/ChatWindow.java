package jms1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatWindow extends JFrame
{
    private Receiver receiver;
    private Sender sender;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(ChatWindow::new);
    }

    private ChatWindow()
    {
        setTitle("JMS Chat");
        setVisible(true);
        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        sender = new Sender();
        receiver = new Receiver();
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
                receiver.disconnect();
                sender.disconnect();
            }
        });
    }


}
