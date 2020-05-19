package zad1;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class ParametersServlet extends HttpServlet
{
    private ServletContext context;

    @Override
    public void init()
    {
        context = getServletContext();
    }

    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("ISO-8859-2");
        response.setCharacterEncoding("ISO-8859-2");
        HttpSession session = request.getSession();

        PrintWriter writer = response.getWriter();
        writer.println("<center><h2>Książki?</h2></center>");
        writer.println("<form method = \"post\">");
        writer.print("<input type = \"text\" size = 30 name = \"search\"");
        String val = (String) session.getAttribute("search");
        if (val != null)
            writer.print(" value = \"" + val + "\"");
        writer.println("><br>");
        writer.println("<input type = \"submit\" value = \"Wyszukaj\">");
        writer.println("</form");

        String search = request.getParameter("search");
        if (search == null)
            return;
        session.setAttribute("search", search);
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
