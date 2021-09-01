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
public class taskadd extends HttpServlet {

public void doPost(HttpServletRequest req,
                      HttpServletResponse res)
        throws IOException, ServletException
    {
		LoginAccount logacc=new LoginAccount();
		res.setContentType("application/json"); 
		String userN=logacc.user;
		ServletOutputStream out=res.getOutputStream();
		String taskname=req.getParameter("taskname");
               System.out.println(taskname);
		String taskdescription=req.getParameter("taskdescription");
                String taskestitime=req.getParameter("taskestitime");
                int estitime=Integer.parseInt(taskestitime);
		String taskassign=req.getParameter("taskassign");
		TaskListBackend task=new TaskListBackend();
		JSONArray jarr=new JSONArray();
                task.addtask(jarr,taskname,taskdescription,taskassign,userN,estitime);
		out.println(jarr.toString());

    }
}
