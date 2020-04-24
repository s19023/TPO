/**
 *
 *  @author Pazur Michał S19023
 *
 */

package S_PASSTIME_SERVER1;


import java.util.ArrayList;
import java.util.List;

public class Client
{
    private String host;
    private int port;
    private String id;
    private List<String> clientLog = new ArrayList<>();

    public Client(String host, int port, String id)
    {
        this.host = host;
        this.port = port;
        this.id = id;
    }

    void connect()
    {

    }

    String send(String msg)
    {
        return "XD";
    }

    String getId()
    {
        return id;
    }
}
