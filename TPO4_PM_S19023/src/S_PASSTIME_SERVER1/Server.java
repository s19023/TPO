/**
 *
 *  @author Pazur Michał S19023
 *
 */

package S_PASSTIME_SERVER1;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.FutureTask;

public class Server
{
    private String host;
    private int port;
    private List<String> serverLogs = new ArrayList<>();
    private Map<String, List<String>> clientLogs = new HashMap<>();
    private Map<SocketChannel, String> clientSockets = new HashMap<>();
    private boolean isRunning = false;
    private Charset charset = StandardCharsets.UTF_8;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    public Server(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    void startServer()
    {
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open())
        {
            isRunning = true;
            serverChannel.socket().bind(new InetSocketAddress(host, port));
            serverChannel.configureBlocking(false);
            Selector selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

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
                                handleRequest(clientChannel);
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

    private void handleRequest(SocketChannel socketChannel) throws IOException
    {
        if (!socketChannel.isOpen()) return;
        StringBuilder stringBuilder = new StringBuilder();
        boolean wasEndReached = false;

        try
        {
            while(!wasEndReached)
            {
                int r = socketChannel.read(byteBuffer);
                if (r > 0)
                {
                    byteBuffer.flip();
                    CharBuffer charBuffer = charset.decode(byteBuffer);
                    while(charBuffer.hasRemaining())
                    {
                        char c = charBuffer.get();
                        if (c == '\n')
                            wasEndReached = true;
                        else
                            stringBuilder.append(c);
                    }
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        String command = stringBuilder.toString();

        if (command.startsWith("login"))
        {
            String clientId = command.split(" ")[1];
            clientLogs.getOrDefault(clientId, new ArrayList<>()).add("logged in");
            clientSockets.putIfAbsent(socketChannel, clientId);
            serverLogs.add(clientId + "logged in at " + LocalTime.now());
            writeResponse(socketChannel, "logged in");
        }
        else if (command.startsWith("bye"))
        {
            String clientId = clientSockets.get(socketChannel);
            clientLogs.get(clientId).add("logged out");
            serverLogs.add(clientId + " logged out at " + LocalTime.now());
            if (command.equals("bye and log transfer"))
            {
                writeResponse(socketChannel, getClientLogs(clientId));
            }
            else
            {
                writeResponse(socketChannel, "logged out");
            }
        }
        else
        {
            String clientId = clientSockets.get(socketChannel);
            clientLogs.get(clientId).add("Request: " + command);
            serverLogs.add(clientId + " request at " + LocalTime.now() + ": \"" + command + "\"");
            String[] dates = command.split(" ");
            String response = Time.passed(dates[0], dates[1]);
            clientLogs.get(clientId).add("Result:");
            clientLogs.get(clientId).add(response);
            writeResponse(socketChannel, response);
        }

    }

    private void writeResponse(SocketChannel socketChannel, String response) throws IOException
    {
        ByteBuffer buffer = charset.encode(CharBuffer.wrap(response));
        socketChannel.write(buffer);
    }

    private String getClientLogs(String client)
    {
        List<String> clientLog = clientLogs.get(client);
        StringBuilder stringBuilder = new StringBuilder("=== " + client + " log start ===");
        for (String logEntry : clientLog)
        {
            stringBuilder.append(logEntry);
            stringBuilder.append('\n');
        }
        stringBuilder.append("=== " + client + " log end ===\n");
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
