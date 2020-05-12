package zad1;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Books extends javax.servlet.http.HttpServlet
{
    private DataSource dataSource;

    public void init() throws ServletException
    {
        try
        {
            Context init = new InitialContext();
            Context context = (Context) init.lookup("java:comp/env");
            dataSource = (DataSource) context.lookup("jdbc/books");
        }
        catch(NamingException e)
        {
            throw new ServletException(e);
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws
            javax.servlet.ServletException,
            IOException
    {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.println("<!DOCTYPE html><html>");
        writer.println("<head>");
        writer.println("<meta charset=\"UTF-8\" />");
        writer.println("<title>Książki!</title>");
        writer.println("</head>");
        writer.println("<body>");

        writer.println("<h1>Lista dostępnych książek:</h1>");
        Connection connection = null;
        try
        {
            synchronized(dataSource)
            {
                connection = dataSource.getConnection();
            }

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select b.name, b.price, a.name from book b, author a where b.id_author = a.idauthor");
            writer.println("<ol>");

            while(resultSet.next())
            {
                String title = resultSet.getString("b.name");
                float price = resultSet.getFloat("b.price");
                String author = resultSet.getString("a.name");
                writer.println("<li>" + author + " - " + title + ", " + price + "</li>");
            }
            resultSet.close();
            statement.close();
        }
        catch(Exception e)
        {
            writer.println(e.getMessage());
        }
        finally
        {
            try
            {
                connection.close();
            }
            catch(SQLException e)
            {
                writer.println(e.getMessage());
            }
        }

        writer.println("</body>");
        writer.println("</html>");
    }
}
