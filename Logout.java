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

public class Logout extends HttpServlet {
public void doGet(HttpServletRequest req,
                      HttpServletResponse res)
        throws IOException, ServletException
    {
		ServletOutputStream out=res.getOutputStream(); 
		Cookie cuser=new Cookie("cookuser",null);
		Cookie cpass=new Cookie("cookpass",null);
	        cuser.setMaxAge(0);
	        cpass.setMaxAge(0);
	        res.addCookie(cuser);
	        res.addCookie(cpass);
		HttpSession session=req.getSession();
		session.invalidate();	
		System.out.println("Logout");
		out.print("logout successfull");
		out.close();
  }
}