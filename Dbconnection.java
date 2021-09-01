package com.localhost.dbconnect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Dbconnection{
Connection conn = null;
	public Connection connect() {
   	 final String url = "jdbc:postgresql://localhost:5432/tasklistapplication";
        final String user = "postgres";
        final String password = "kuna271999";
      
       
       try {
	Class.forName("org.postgresql.Driver");
           conn = DriverManager.getConnection(url, user, password);
           System.out.println("Connected to the PostgreSQL server successfully.");
           
           }
       catch (Exception e) {
           System.out.println(e.getMessage());
       }

       return conn;
   }
}