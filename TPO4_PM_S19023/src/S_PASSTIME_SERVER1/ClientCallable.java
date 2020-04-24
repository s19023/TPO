package S_PASSTIME_SERVER1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ClientCallable implements Callable<String>
{
    private Client c;
    private List<String> reqsList;
    private boolean showSendRes;

    public ClientCallable(Client c, List<String> reqsList, boolean showSendRes)
    {
        this.c = c;
        this.reqsList = reqsList;
        this.showSendRes = showSendRes;
    }

    @Override
    public String call() throws Exception
    {
        List<String> responseList = new ArrayList<>();
        for (String request : reqsList)
        {
            String response = c.send(request);
            if(showSendRes)
            {
                System.out.println(response);
            }
            responseList.add(response);
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String response : responseList)
        {
            stringBuilder.append(response);
            stringBuilder.append('\n');
        }

        return stringBuilder.toString();
    }
}
