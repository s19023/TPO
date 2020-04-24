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
            serverChannel.connect(new InetSocketAddress(host, port));
            serverChannel.configureBlocking(false);
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
            ByteBuffer outBuffer = charset.encode(msg + "\n");
            serverChannel.write(outBuffer);

            StringBuilder stringBuilder = new StringBuilder();
            while(true)
            {
                byteBuffer.clear();
                int r = serverChannel.read(byteBuffer);
                if (r == -1)
                {
                    break;
                }
                else if (r != 0)
                {
                    byteBuffer.flip();
                    CharBuffer charBuffer = charset.decode(byteBuffer);
                    stringBuilder.append(charBuffer);
                    break;
                }
            }
            return stringBuilder.toString();
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
