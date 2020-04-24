/**
 *
 *  @author Pazur Michał S19023
 *
 */

package S_PASSTIME_SERVER1;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Client
{
    private String host;
    private int port;
    private String id;
    private List<String> clientLog = new ArrayList<>();
    private SocketChannel serverChannel;
    private Charset charset = StandardCharsets.UTF_8;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    public Client(String host, int port, String id)
    {
        this.host = host;
        this.port = port;
        this.id = id;
    }

    void connect()
    {
        try
        {
            serverChannel = SocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.connect(new InetSocketAddress(host, port));

            System.out.println("connected!");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    String send(String msg)
    {
        try
        {
            if (serverChannel.isConnected())
            {
                byteBuffer = charset.encode(msg + '\n');
                serverChannel.write(byteBuffer);
                return "XD";
            }
            return "?";
        }
        catch(IOException e)
        {
            return e.getMessage();
        }
    }

    String getId()
    {
        return id;
    }
}
