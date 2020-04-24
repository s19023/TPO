/**
 *
 *  @author Pazur Michał S19023
 *
 */

package S_PASSTIME_SERVER1;


import java.util.List;
import java.util.Map;

public class Server
{
    private String host;
    private int port;
    private Map<String, List<String>> userLogs;
    private List<String> serverLogs;

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
