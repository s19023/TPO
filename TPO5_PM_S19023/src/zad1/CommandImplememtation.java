package zad1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CommandImplememtation implements Serializable, Command
{
    private Map parameterMap = new HashMap();
    private List resultList = new ArrayList();
    private int statusCode;

    @Override
    public void setParameter(String name, Object parameter)
    {
        parameterMap.put(name, parameter);
    }

    @Override
    public Object getParameter(String name)
    {
        return parameterMap.get(name);
    }

    public void addResult(Object o)
    {
        resultList.add(o);
    }

    public void addResult(String s)
    {
        resultList.add(new Object[] {s});
    }

    @Override
    public List getResults()
    {
        return resultList;
    }

    @Override
    public void clearResults()
    {
        resultList.clear();
    }

    public void clearResult()
    {
        resultList.clear();
    }

    @Override
    public void setStatusCode(int code)
    {
        statusCode = code;
    }

    @Override
    public int getStatusCode()
    {
        return statusCode;
    }
}
