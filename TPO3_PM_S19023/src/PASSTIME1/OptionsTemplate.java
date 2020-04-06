package PASSTIME1;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OptionsTemplate
{
    private String host;
    private int port;
    private boolean concurMode;
    private boolean showSendRes;
    private Map<String, List<String>> clientsMap = new LinkedHashMap<>();

    public void setHost(String host)
    {
        this.host = host;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public void setConcurMode(boolean concurMode)
    {
        this.concurMode = concurMode;
    }

    public void setShowSendRes(boolean showSendRes)
    {
        this.showSendRes = showSendRes;
    }

    public void setClientsMap(Map<String, List<String>> clientsMap)
    {
        this.clientsMap = clientsMap;
    }

    public Options createOptionsFromTemplate()
    {
        return new Options(host, port, concurMode, showSendRes, clientsMap);
    }
}
