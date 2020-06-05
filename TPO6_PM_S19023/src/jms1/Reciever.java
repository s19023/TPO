package jms1;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Reciever
{
    public static void main(String[] args)
    {
        Context context = null;
        QueueConnection connection = null;

        try
        {
            context = new InitialContext();
            QueueConnectionFactory connectionFactory = (QueueConnectionFactory) context.lookup("JmsQueueConnectionFactory");
            connection = connectionFactory.createQueueConnection();
            connection.start();
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = (Queue) context.lookup("queue1");
            QueueReceiver receiver = session.createReceiver(queue);
            receiver.setMessageListener((message) ->
            {
                try
                {
                    System.out.println("Received message: \"" + ((TextMessage)message).getText() + "\".");
                }
                catch (JMSException e)
                {
                    e.printStackTrace();
                }
            });
        }
        catch (NamingException | JMSException e)
        {
            e.printStackTrace();
        }
    }
}
