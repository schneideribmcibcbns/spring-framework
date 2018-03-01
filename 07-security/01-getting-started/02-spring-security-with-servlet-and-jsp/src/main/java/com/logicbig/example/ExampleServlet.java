package com.logicbig.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "exampleServlet", urlPatterns = {"/example"})
public class ExampleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String servletName = getServletConfig().getServletName();
        writer.println("handling request, servlet name: " + servletName);
        writer.println("<br/>");
        writer.println("user: "+req.getUserPrincipal().getName());
        writer.println( "<br/><a href=\"/index.jsp\">Main Page</a>");
    }
}