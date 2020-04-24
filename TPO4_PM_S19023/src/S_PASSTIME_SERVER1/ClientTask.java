/**
 *
 *  @author Pazur Michał S19023
 *
 */

package S_PASSTIME_SERVER1;


import java.util.List;
import java.util.concurrent.FutureTask;

public class ClientTask extends FutureTask<String>
{
    private Client c;

    public static ClientTask create(Client c, List<String> reqs, boolean showSendRes)
    {
        return new ClientTask(c, new ClientCallable(c, reqs, showSendRes));
    }

    public ClientTask(Client c, ClientCallable callable)
    {
        super(callable);

        this.c = c;
        this.c.connect();
        this.c.send("login " + c.getId());
    }
}
