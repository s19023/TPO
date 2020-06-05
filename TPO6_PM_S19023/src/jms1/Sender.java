package jms1;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Sender
{
    public static void main(String[] args)
    {
        Context context;

        {
            try
            {
                context = new InitialContext();
                QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context.lookup("JmsQueueConnectionFactory");
                QueueConnection connection = queueConnectionFactory.createQueueConnection();
                QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                connection.start();
                Queue queue = (Queue) context.lookup("queue1");
                QueueSender sender = session.createSender(queue);
                TextMessage message = session.createTextMessage("XD");
                sender.send(message);
                connection.close();
                context.close();
            }
            catch(NamingException | JMSException e)
            {
                e.printStackTrace();
            }
        }
    }

}
