package jms1;
import javax.jms.*;
import javax.naming.Context;

public class Sender
{
    private JMSConnection connection;
    private TopicPublisher sender;

    Sender(JMSConnection connection)
    {
        this.connection = connection;
        createSender();
    }

    private void createSender()
    {
        Context context = connection.getContext();
        TopicSession session = connection.getSession();

        if (context == null)
        {
            System.err.println("Context is not set. Unable to create sender connection.");
            return;
        }

        try
        {
            Topic topic = connection.getTopic();
            sender = session.createPublisher(topic);

            System.out.println("Sender connected to JMS server.");
        }
        catch (JMSException jmsException)
        {
            System.err.println("JMS error while establishing connection. Cause: " + jmsException.getMessage() + ".");
        }
    }

    public void sendMessage(String message)
    {
        TopicSession session = connection.getSession();

        try
        {
            TextMessage textMessage = session.createTextMessage(message);
            sender.publish(textMessage);
            System.out.println("Sent message \"" + message + "\".");
        }
        catch (JMSException jmsException)
        {
            System.err.println("JMS error while sending message! Cause: " + jmsException.getMessage() + ".");
        }
    }
}
