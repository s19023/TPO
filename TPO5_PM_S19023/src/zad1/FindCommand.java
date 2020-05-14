package zad1;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
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
            dataSource = (DataSource) context.lookup("jdbc/books");
        }
        catch(NamingException e)
        {
            setStatusCode(1);
        }
    }

    @Override
    public void execute()
    {
        String nameToFind = (String) getParameter("nameToFind");
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
                String title = resultSet.getString("b.name");
                float price = resultSet.getFloat("b.price");
                String author = resultSet.getString("a.name");
                String result = author + ", " + title + ": " + price;
                addResult(result);
            }
            resultSet.close();
            statement.close();
        }
        catch(Exception e)
        {
            setStatusCode(2);
        }
        finally
        {
            try
            {
                connection.close();
            } catch(SQLException e)
            {
                setStatusCode(3);
            }
        }
    }
}
