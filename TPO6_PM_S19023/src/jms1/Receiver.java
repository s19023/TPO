package jms1;

import netscape.javascript.JSException;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.NamingException;

public class Receiver extends JMSConnectionBase
{
    private Context context;
    private QueueConnection connection;
    private QueueSession session;
    private QueueReceiver receiver;

    Receiver()
    {
        createReceiver();
    }

    private void createReceiver()
    {
        Context context = getContext();
        QueueSession session = getSession();

        try
        {
            Queue queue = (Queue) context.lookup("chatQueue");
            receiver = session.createReceiver(queue);
            receiver.setMessageListener((message -> {
                try
                {
                    System.out.println("Received a message: \"" + ((TextMessage) message).getText() + "\".");
                }
                catch (JMSException jmsException)
                {
                    System.out.println("Error while reading message.");
                }
            }));

            System.out.println("Receiver connected to JMS server.");
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
}
