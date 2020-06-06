package jms1;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.NamingException;

public class Sender extends JMSConnectionBase
{
    private QueueSender sender;

    Sender()
    {
        createSender();
    }

    private void createSender()
    {
        Context context = getContext();
        QueueSession session = getSession();

        try
        {
            Queue queue = (Queue) context.lookup("chatQueue");
            sender = session.createSender(queue);

            System.out.println("Sender connected to JMS server.");
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

    public void sendMessage(String message)
    {
        QueueSession session = getSession();

        try
        {
            TextMessage textMessage = session.createTextMessage(message);
            sender.send(textMessage);
            System.out.println("Sent message \"" + message + "\".");
        }
        catch (JMSException jmsException)
        {
            System.out.println("JMS error while sending message! Cause: " + jmsException.getMessage() + ".");
        }
    }
}
