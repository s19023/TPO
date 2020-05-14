package zad1;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Books extends javax.servlet.http.HttpServlet
{
    private String presentationServlet;
    private Command command;
    private String parametersServlet;
    private ServletContext context;
    private Object lock = new Object();

    public void init() throws ServletException
    {
        try
        {
            context = getServletContext();
            presentationServlet = context.getInitParameter("presentationServlet");
            parametersServlet = context.getInitParameter("parametersServlet");
            String dbName = context.getInitParameter("dbName");
            String commandClassName = context.getInitParameter("commandClassName");

            Class commandClass = Class.forName(commandClassName);
            command = (Command) commandClass.newInstance();
            command.setParameter("dbName", dbName);
        }
        catch(Exception e)
        {
            throw new ServletException(e);
        }
    }

    void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("text/html");
        RequestDispatcher requestDispatcher = context.getRequestDispatcher(parametersServlet);
        requestDispatcher.include(req, resp);
        HttpSession session = req.getSession();
        String nameToSearch = (String) session.getAttribute("search");
        command.setParameter("nameToSearch", nameToSearch);

        Lock mainLock = new ReentrantLock();
        mainLock.lock();
        command.execute();
        List results = command.getResults();
        session.setAttribute("StatusCode", command.getStatusCode());
        session.setAttribute("Results", results);
        session.setAttribute("Lock", mainLock);
        requestDispatcher = context.getRequestDispatcher(presentationServlet);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        handleRequest(req, resp);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException
    {
        handleRequest(request, response);
    }
}
