package com.localhost.javaservlet;
import com.localhost.queryoperation.*;
import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.sql.*;  
import org.json.*;
public class Assignuser extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		final String url = "jdbc:postgresql://localhost:5432/tasklistapplication";
        final String user = "postgres";
        final String password = "kuna271999";
       Connection conn = null;
       Statement stmt=null;
       ResultSet rs=null;
       PrintWriter out = res.getWriter();  
       res.setContentType("application/json"); 
       try {
    	   Class.forName("org.postgresql.Driver");
           conn = DriverManager.getConnection(url, user, password);
           stmt = conn.createStatement();
			rs = stmt.executeQuery("select createUserName from newUser;");
			JSONArray jarray=new JSONArray(); 
			while(rs.next()) {
			        	String createusername=rs.getString("createUserName");
			        	System.out.println(createusername+"***");
			        	JSONObject jobj=new JSONObject();
						jobj.put("username",createusername);
				        jarray.put(jobj);
			 }
	           out.println(jarray.toString()); 
		}catch(Exception e) {
    	   e.printStackTrace();
       }
   	finally {
		try {
			if(rs!=null) {
				rs.close();
			}
			if(stmt!=null) {
				stmt.close();
			}
			if(conn!=null) {
				conn.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
       }
}
