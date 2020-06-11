package jms1;

import javax.jms.*;
import javax.naming.Context;

public class Receiver
{
    private JMSConnection connection;

    Receiver(JMSConnection connection)
    {
        this.connection = connection;
        createReceiver();
    }

    private void createReceiver()
    {
        Context context = connection.getContext();
        TopicSession session = connection.getSession();

        if (context == null)
        {
            System.err.println("Context is not set. Unable to create receiver connection.");
            return;
        }

        try
        {
            Topic topic = connection.getTopic();
            MessageConsumer consumer = session.createDurableSubscriber(topic, connection.getUsername());
            consumer.setMessageListener(message -> System.out.println("Received: " + message + "."));
            System.out.println("Receiver connected to JMS server.");
        }
        catch (JMSException jmsException)
        {
            System.err.println("JMS error while establishing connection. Cause: " + jmsException.getMessage() + ".");
        }
    }
}
