package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/to_influx")
public class to_influx extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }
    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        /*Параметры по списку
        host
        status
        start_time
        end_time
        waisted_time
        */



        response.setContentType("text/html");
        response.setCharacterEncoding("windows-1251");
        PrintWriter out = response.getWriter();

        out.write("");
        out.flush();
    }
}
