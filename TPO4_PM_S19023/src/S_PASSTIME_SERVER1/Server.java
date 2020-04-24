/**
 *
 *  @author Pazur Michał S19023
 *
 */

package S_PASSTIME_SERVER1;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Server
{
    private String host;
    private int port;
    private List<String> serverLogs = new ArrayList<>();
    private Map<String, List<String>> clientLogs = new HashMap<>();
    private boolean isRunning = false;

    public Server(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    void startServer()
    {
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open())
        {
            serverChannel.bind(new InetSocketAddress(host, port));
            serverChannel.configureBlocking(false);
            Selector selector = Selector.open();
            SelectionKey selectionKey = serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            new Thread(() ->
            {
                while(isRunning)
                {
                    try
                    {
                        selector.select();
                        Set keys = selector.selectedKeys();
                        Iterator keyIterator = keys.iterator();
                        while(keyIterator.hasNext())
                        {
                            SelectionKey key = (SelectionKey) keyIterator.next();
                            keyIterator.remove();
                            if (key.isAcceptable())
                            {
                                SocketChannel clientChannel = serverChannel.accept();
                                clientChannel.configureBlocking(false);
                                clientChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                            }
                            else if (key.isReadable())
                            {
                                SocketChannel clientChannel = (SocketChannel) key.channel();
                                //TODO: reading client requests
                            }
                            else if (key.isWritable())
                            {
                                SocketChannel clientChannel = (SocketChannel) key.channel();
                                //TODO: writing data to clients
                            }
                        }
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

                }
            }).start();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    void stopServer()
    {
        isRunning = false;
    }

    private String getClientLogs(String client)
    {
        List<String> clientLog = clientLogs.get(client);
        StringBuilder stringBuilder = new StringBuilder();
        for (String logEntry : clientLog)
        {
            stringBuilder.append(logEntry);
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    String getServerLog()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (String logEntry : serverLogs)
        {
            stringBuilder.append(logEntry);
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
