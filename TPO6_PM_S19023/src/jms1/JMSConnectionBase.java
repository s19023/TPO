package jms1;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSConnectionBase
{
    private Context context;
    private QueueConnection connection;
    private QueueSession session;
    private Queue queue;

    public JMSConnectionBase()
    {
        connect();
    }

    public void connect()
    {
        try
        {
            context = new InitialContext();

            QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context.lookup("JmsQueueConnectionFactory");
            connection = queueConnectionFactory.createQueueConnection();
            connection.start();

            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            queue = (Queue) context.lookup("chatQueue");


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

    public void disconnect()
    {
        try
        {
            if (context != null)
                context.close();

            if (connection != null)
                connection.close();
        }
        catch (NamingException namingException)
        {
            System.out.println("JMS server not configured properly. Cause: " + namingException.getMessage() + ".");
        }
        catch (JMSException jmsException)
        {
            System.out.println("JMS error while closing connection. Cause: " + jmsException.getMessage() + ".");
        }
    }

    public Context getContext()
    {
        return context;
    }

    public QueueConnection getConnection()
    {
        return connection;
    }

    public QueueSession getSession()
    {
        return session;
    }

    public Queue getQueue()
    {
        return queue;
    }
}
