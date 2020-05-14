package zad1;

import java.util.List;

public interface Command
{
    void init();
    void setParameter(String name, Object parameter);
    Object getParameter(String name);
    void execute();
    List getResults();
    void setStatusCode(int code);
    int getStatusCode();
}
