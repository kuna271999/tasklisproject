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
public class Updatetask extends HttpServlet {

public void doPost(HttpServletRequest req,
                      HttpServletResponse res)
        throws IOException, ServletException
    {
		ServletOutputStream out=res.getOutputStream();
		out.println("Successfully login");
		String tid=req.getParameter("tid");
		int idtask=Integer.parseInt(tid); 
		String tstatus=req.getParameter("tstatus");
		out.println(idtask+" "+tstatus);
		System.out.println(idtask+" "+tstatus);
		TaskListBackend task=new TaskListBackend();
		task.updatetask(idtask,tstatus);
		
    }
}
