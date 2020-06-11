package jms1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatWindow extends JFrame
{
    private JMSConnection jmsConnection;
    private JTextArea chatHistory;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new ChatWindow(args[0]));
    }

    private ChatWindow(String username)
    {
        setTitle("JMS Chat user: " + username);
        setVisible(true);
        setSize(400, 400);
        createGui();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jmsConnection = new JMSConnection(username, chatHistory);
    }

    private void createGui()
    {
        chatHistory = new JTextArea();
        chatHistory.setLineWrap(true);
        chatHistory.setEditable(false);
        add(chatHistory);
        add(new JScrollPane(chatHistory));

        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new GridLayout(1, 2));

        TextField newMessageField = new TextField();
        bottomRow.add(newMessageField, BorderLayout.LINE_START);

        Button sendButton = new Button("Send!");
        sendButton.addActionListener(e ->
        {
            String message = newMessageField.getText();
            if (!message.equals(""))
            {
                jmsConnection.sendMessage(message);
                newMessageField.setText("");
            }
        });
        bottomRow.add(sendButton, BorderLayout.LINE_END);

        add(bottomRow, BorderLayout.PAGE_END);

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
