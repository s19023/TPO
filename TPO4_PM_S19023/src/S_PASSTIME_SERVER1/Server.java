/**
 *
 *  @author Pazur Michał S19023
 *
 */

package S_PASSTIME_SERVER1;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server
{
    private String host;
    private int port;
    private List<String> serverLogs = new ArrayList<>();
    private Map<String, List<String>> clientLogs = new HashMap<>();

    public Server(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    void startServer()
    {

    }

    void stopServer()
    {

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
