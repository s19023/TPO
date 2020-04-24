/**
 *
 *  @author Pazur Michał S19023
 *
 */

package S_PASSTIME_SERVER1;


import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ClientTask extends FutureTask<String>
{
    private Client c;
    private List<String> reqs;
    private boolean showSendRes;

    public static ClientTask create(Client c, List<String> reqs, boolean showSendRes)
    {
        return new ClientTask(c, reqs, showSendRes);
    }

    public ClientTask(Client c, List<String> reqs, boolean showSendRes)
    {
        this.c = c;
        this.reqs = reqs;
        this.showSendRes = showSendRes;

        c.connect();
        c.send("login " + id);
    }

    public String get() throws InterruptedException, ExecutionException
    {

    }
}
