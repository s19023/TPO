package zad1;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FindCommand extends CommandImplememtation
{

    private DataSource dataSource;

    @Override
    public void init()
    {
        try
        {
            Context init = new InitialContext();
            Context context = (Context) init.lookup("java:comp/env");
            String dbName = (String) getParameter("dbName");
            dataSource = (DataSource) context.lookup(dbName);
        }
        catch(NamingException e)
        {
            setStatusCode(500);
        }
    }

    @Override
    public void execute()
    {
        clearResults();
        setStatusCode(200);
        String nameToFind = (String) getParameter("nameToFind");
        if (nameToFind == null)
            nameToFind = "%";
        String command = "select k.name, k.price, a.name from book k, author a where k.id_author = a.idauthor and (k.name like '%" + nameToFind + "%' or a.name like '%" + nameToFind + "%')";
        Connection connection = null;
        try
        {
            synchronized(dataSource)
            {
                connection = dataSource.getConnection();
            }

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(command);


            while(resultSet.next())
            {
                String title = resultSet.getString("k.name");
                float price = resultSet.getFloat("k.price");
                String author = resultSet.getString("a.name");
                String result = author + ", " + title +  String.format(": %.2f", price);
                addResult((Object) result);
            }

            if (getResults().size() == 0)
                setStatusCode(404);

            resultSet.close();
            statement.close();
        }
        catch(Exception e)
        {
            setStatusCode(501);
        }
        finally
        {
            try
            {
                connection.close();
            }
            catch(Exception e)
            {
                setStatusCode(502);
            }
        }
    }
}
