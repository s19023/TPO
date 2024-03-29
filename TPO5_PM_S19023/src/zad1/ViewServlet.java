package zad1;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class ViewServlet extends HttpServlet
{

    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletContext context = getServletContext();
        String parameterServer = context.getInitParameter("parametersServlet");
        RequestDispatcher dispatcher = context.getRequestDispatcher(parameterServer);
        dispatcher.include(request, response);

        HttpSession session = request.getSession();
        Lock lock = (Lock) session.getAttribute("Lock");
        lock.unlock();
        int statusCode = (int) session.getAttribute("StatusCode");
        List results = (List) session.getAttribute("Results");
        PrintWriter writer = response.getWriter();
        writer.println("<br>");
        writer.println("<h3>Status komendy: " + statusCode + "</h3>");
        writer.println("<h2>Wyniki:</h2>");
        writer.println("<ul>");

        for (Iterator iterator = results.iterator(); iterator.hasNext();)
        {
            writer.println("<li>");
            Object result = iterator.next();
            writer.print(result.toString());
            writer.println("</li>");
        }

        writer.println("</ul>");
        writer.println("</body></html>");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        handleRequest(req, resp);
    }
}
