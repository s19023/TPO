package jms1;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JMSConnection
{
    private Context context;
    private TopicConnection connection;
    private TopicSession session;
    private Topic topic;
    private String username;
    private Sender sender;
    private Receiver receiver;
    private JTextArea chatHistory;
    private SimpleDateFormat dateFormat;

    public JMSConnection(String username, JTextArea chatHistory)
    {
        this.username = username;
        connect();
        sender = new Sender(this);
        receiver = new Receiver(this);
        this.chatHistory = chatHistory;
        dateFormat = new SimpleDateFormat("dd/MM/yy '|' HH:mm:ss");
    }

    private void connect()
    {
        try
        {
            context = new InitialContext();

            TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) context.lookup("JmsTopicConnectionFactory");
            connection = topicConnectionFactory.createTopicConnection();
            connection.start();

            session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            topic = (Topic) context.lookup("chatTopic");
        }
        catch (NamingException namingException)
        {
            System.out.println("JMS server not configured properly. Cause: " + namingException.getMessage() + ".");
        }
        catch (JMSException jmsException)
        {
            System.out.println("JMS error while establishing connection. Cause: " + jmsException.getMessage() + ".");
        }
    }

    public void disconnect()
    {
        try
        {
            if (context != null)
                context.close();

            if (connection != null)
                connection.close();

            if(session != null)
                session.close();
        }
        catch (NamingException namingException)
        {
            System.err.println("JMS server not configured properly. Cause: " + namingException.getMessage() + ".");
        }
        catch (JMSException jmsException)
        {
            System.err.println("JMS error while closing connection. Cause: " + jmsException.getMessage() + ".");
        }
    }

    public void sendMessage(String message)
    {
        sender.sendMessage(username + ": " + message);
    }

    public void receiveMessage(String message)
    {
        chatHistory.append(dateFormat.format(new Date()) + "\n");
        chatHistory.append(message + "\n");
    }

    public Context getContext()
    {
        return context;
    }

    public TopicConnection getConnection()
    {
        return connection;
    }

    public TopicSession getSession()
    {
        return session;
    }

    public Topic getTopic()
    {
        return topic;
    }

    public String getUsername()
    {
        return username;
    }
}
