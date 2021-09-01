package com.localhost.javaservlet;
import com.localhost.queryoperation.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.*;
public class CreateAccount extends HttpServlet {

public void doPost(HttpServletRequest req,
                      HttpServletResponse res)
        throws IOException, ServletException
    {
		ServletOutputStream out=res.getOutputStream();
		out.println("Successfully login");
		String createname=req.getParameter("createname");
		String createusername=req.getParameter("createusername");
		String createpassword=req.getParameter("createpassword");
		out.println(createname+" "+createusername+" "+createpassword);
		TaskListBackend task=new TaskListBackend();
		task.adduser(createname,createusername,createpassword);


    }
}
