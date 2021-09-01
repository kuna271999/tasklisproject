package com.localhost.javaservlet;
import com.localhost.queryoperation.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;
import org.json.*;

public class LoginAccount extends HttpServlet {
public static String user;
public void doPost(HttpServletRequest req,
                      HttpServletResponse res)
        throws IOException, ServletException
    {
		ServletOutputStream out=res.getOutputStream();
		res.setContentType("application/json");
		String loginUsername=req.getParameter("loginUsername");
		user=loginUsername;
		String loginPassword=req.getParameter("loginPassword");
		String remember=req.getParameter("remember");
		TaskListBackend task=new TaskListBackend();
		String result=task.logincheck(loginUsername,loginPassword); 
		System.out.println(remember);
             System.out.println("cookie on"); 
                        Cookie crem=new Cookie("cookrem", remember);
			Cookie cuser=new Cookie("cookuser", loginUsername);
			Cookie cpass=new Cookie("cookpass",loginPassword);
                crem.setMaxAge(60*2);
	        cuser.setMaxAge(60*2);
	        cpass.setMaxAge(60*2);
 		res.addCookie(crem);
	        res.addCookie(cuser);
	        res.addCookie(cpass);
		HttpSession session=req.getSession();
		session.setAttribute("username", loginUsername);
        try{
	JSONArray jarraylog=new JSONArray();
	JSONObject jobjlog=new JSONObject();
	jobjlog.put("loginusername",loginUsername);
	jobjlog.put("loginpassword",loginPassword);
	jobjlog.put("result",result);
	jarraylog.put(jobjlog);
	out.println(jarraylog.toString());
     out.flush();
     }
    catch(Exception e){ 
 e.printStackTrace();
    }

  }
}