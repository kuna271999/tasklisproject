package com.localhost.queryoperation;
import com.localhost.dbconnect.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Collection;
import java.util.concurrent.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDateTime;  
public class TaskListBackend {   
	Connection conn = null;
	int ui=1033;
	double d=100;
	String dayq="";
	int flagtime=0;
	Date date=new Date();
	Calendar c = Calendar.getInstance();
    int dayno = c.get(Calendar.DAY_OF_WEEK);
    int dupday=dayno;
    int hours = c.get(Calendar.HOUR_OF_DAY);
    int minutes = c.get(Calendar.MINUTE);
    int seconds = c.get(Calendar.SECOND);
    String dayWeekText = new SimpleDateFormat("EEEE").format(date);
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
	public void adduser(String createname,String createusername,String createpassword){
		connect();
		if(conn==null) {
			System.out.println("DB connection not available");
	        }
		else {
		Statement stmt=null;
		ResultSet rs=null;
		ResultSet rw=null;
		try {
			stmt = conn.createStatement();
			String sql = "INSERT INTO newUser (createName,createUserName,createPassword,role) "
	                + "VALUES ('"+createname+"','"+createusername+"',"+"crypt('"+createpassword+"',gen_salt('bf')"+"),'s');";
	        stmt.executeUpdate(sql);
	        rs=stmt.executeQuery("select * from newuser");
	        while(rs.next()) {
	        	int userid=rs.getInt("userId");
			String name=rs.getString("createName");
	        	String username=rs.getString("createUserName");
	        	String userpassword=rs.getString("createPassword");
	        	System.out.println( "ID = " + userid );
	            System.out.println( "NAME = " + name);
			System.out.println( "USERNAME = " + username);
	            System.out.println( "PASSWORD = " + userpassword );
		   }
	        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();  
	        rw=stmt.executeQuery("select userId from newUser where createusername='"+createusername+"';");
            if(rw.next()) {                
			int uid=rw.getInt("userId");
			String sql1 = "INSERT INTO taskDetails (taskName,taskCreatedby,taskStatus,taskDescription,taskCreatedTime,userId,estimatedtime) "
	                + "VALUES ('Attendance','Manager','nottaken','Daily attendance','"+dtf.format(now)+"','"+uid+"','1');";
			stmt.executeUpdate(sql1);
			String sql2 = "INSERT INTO taskDetails (taskName,taskCreatedby,taskStatus,taskDescription,taskCreatedTime,userId,estimatedtime) "
	                + "VALUES ('Attendance','Manager','nottaken','Daily attendance','"+dtf.format(now)+"','"+uid+"','1');";
			stmt.executeUpdate(sql2);
            }
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
			if(rs!=null) {
				rs.close();
			}
			if(rw!=null) {
				rw.close();
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
		}}

	}
	public String logincheck(String loginUsername,String loginPassword) {
		connect(); 
		String result = " ";
		if(conn==null) {
			System.out.println("DB connection not available");
	     }
		else {
			Statement stmt=null;
			ResultSet rw=null;
			try {
				stmt = conn.createStatement();
System.out.println(loginUsername);
				 System.out.println(loginPassword);
				  rw=stmt.executeQuery("select createUserName,(createPassword=crypt('"+loginPassword+"',createPassword)) as password from newuser where createUsername='"+loginUsername+"'");
				 if(!rw.next()) {
					 result="Userinvalid";
				 }
				 else {
					 Boolean passmatch=rw.getBoolean("password");
					 if(passmatch) {
						 result="Successfullogin";
					 }
					 else {
						 result="Passinvalid";
					 }
					
				 }
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					if(rw!=null) {
						rw.close();
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
		return result;
	}
	public static String getDayOfWeek(int value){
	    String day = "";
	    switch(value){
	    case 1:
	        day="Sunday";
	        break;
	    case 2:
	        day="Monday";
	        break;
	    case 3:
	        day="Tuesday";
	        break;
	    case 4:
	        day="Wednesday";
	        break;
	    case 5:
	        day="Thursday";
	        break;
	    case 6:
	        day="Friday";
	        break;
	    case 7:
	        day="Saturday";
	        break;
	    }
	    return day;
	}
	public void autoassigntask(JSONArray jarr,String taskname,String taskdescription,String userN,int estitime,String tasktype) {
		int i=1033;
		int min=10000;
	    String role="";
		int uid=1033;
		int j=1036;
		String cname="";
		Boolean flag=true;
		connect();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 
		if(conn==null) {
			System.out.println("DB connection not available");
	     }
		else {
			ResultSet rs=null;
			ResultSet rw=null;
			ResultSet rq=null;
			Statement stmt=null;
    		try {
    		while(flag) {
    		if(tasktype.equals("trivial")) {
    			stmt=conn.createStatement();
    			rq=stmt.executeQuery("select userid from newuser where userid='"+j+"' and role='j';");
    			if(rq.next()) {
    				uid=rq.getInt("userid");
    				j=j+1;
    			}
    			else {
    				flag=false;
    				System.out.println("no junior");
    			}
    			System.out.println("junior id "+uid);
    			rs=stmt.executeQuery("select userid,sum(estimatedtime) as s from taskDetails where userid='"+uid+"' and taskstatus='nottaken' group by userid;");
        		
        		if(rs.next())
        		{
        			int et=rs.getInt("s");
        			System.out.println(j+" "+et);
        			if(et<min) {
        				min=et;
        				j=j-1;
        				ui=j;
        			}
        		}
        		else {
        			flag=false;
        			System.out.println("no");
        		}
    		}
    		else {
    			stmt = conn.createStatement();
    			if(tasktype.equals("new")) {
    			rq=stmt.executeQuery("select userid from newuser where userid='"+i+"' and role='s';");
    			if(rq.next()) {
    			 uid=rq.getInt("userid");
    			}else {
    				flag=false;
    				System.out.println("no senior");
    			}
    			System.out.println("senior id "+uid);
    			}
    			if(tasktype.equals("enhancement")) {
        			rq=stmt.executeQuery("select userid from newuser where userid='"+i+"';");
        			if(rq.next()) {
        			 uid=rq.getInt("userid");
        			}else {
        				flag=false;
        				System.out.println("no senior and junior");
        			}
        			System.out.println("both id "+uid);
        			}
    		rs=stmt.executeQuery("select userid,sum(estimatedtime) as s from taskDetails where userid='"+uid+"' and taskstatus='nottaken' group by userid;");
    		
    		if(rs.next())
    		{
    			int et=rs.getInt("s");
    			System.out.println(i+" "+et);
    			if(et<min) {
    				min=et;
    				ui=i;
    			}
    		}
    		else {
    			flag=false;
    			System.out.println("no");
    		}
    		i=i+1;
    		}
    		}
    		System.out.println("min "+min+" "+"id "+ui);
    		rs=stmt.executeQuery("select role from newuser where userid='"+ui+"';");
    		if(rs.next()) {
    			role=rs.getString("role");
    		}
    		if(role.equals("s")) {
    		if(dayno>=2 && dayno<=5) {
    			if(hours>9 && hours<17)
    			{
    				d=0;
    				dupday=dayno;
    			    dayq=getDayOfWeek(dupday);
    			    flagtime=0;
    			}
    			else if(hours>=17){
    				int r=24-hours;
    				int f=60-minutes;
    				double h=((double)f)/100;
    				d=(r+9+h)-1;
    				dupday=dayno+1;
    				dayq=getDayOfWeek(dupday);
    				flagtime=1;
    			}
    			else if(hours<9) {
    				int r=9-hours;
    				int f=60-minutes;
    				double h=((double)f)/100;
    				d=(r+h)-1;
    				dupday=dayno;
    				dayq=getDayOfWeek(dupday);
    				flagtime=1;
    			}
    		}
    		if(dayno==6) {
    			if(hours>9 && hours<17)
    			{
    				d=0;
    				dupday=dayno;
    				dayq=getDayOfWeek(dupday);
    				flagtime=0;
    			}
    			else if(hours>=17){
    				int r=24-hours;
    				int f=60-minutes;
    				double h=((double)f)/100;
    				int p=r+48;
    				d=(p+9+h)-1;
    				dupday=dayno+3;
    				if(dupday>7) {
    					dupday=Math.abs(dupday-7);
    				}
    				dayq=getDayOfWeek(dupday);
    				flagtime=1;
    			}
    			else if(hours<9) {
    				int r=9-hours;
    				int f=60-minutes;
    				double h=((double)f)/100;
    				d=(r+h)-1;
    				dupday=dayno;
    				dayq=getDayOfWeek(dupday);
    				flagtime=1;
    			}
    		}
    		if(dayno==1) {
    			int r=24-hours;
    			int f=60-minutes;
    			double h=((double)f)/100;
    			d=(r+9+h)-1;
    			dupday=dayno+1;
    			dayq=getDayOfWeek(dupday);
    			flagtime=1;
    		}
    		if(dayno==7) {
    			int r=24-hours;
    			int f=60-minutes;
    			double h=((double)f)/100;
    			int p=r+24;
    			d=(p+9)-1;
    			dupday=dayno+2;
    			if(dupday>7) {
					dupday=Math.abs(dupday-7);
				}
    			dayq=getDayOfWeek(dupday);
    			flagtime=1;
    		}
    		ScheduledExecutorService scheduler= Executors.newSingleThreadScheduledExecutor();
    		System.out.println("service start");
    		Runnable task=new Runnable() {
    			
    			public void run() {
    				Statement stmt2=null;
    				System.out.println("delayyyyyy");
    				try {
    					stmt2=conn.createStatement();
    				String sql = "INSERT INTO taskDetails (taskName,taskCreatedby,taskStatus,taskDescription,taskCreatedTime,userId,estimatedtime) "
    		                + "VALUES ('"+taskname+"','"+userN+"','nottaken','"+taskdescription+"','"+dtf.format(now)+"','"+ui+"','"+estitime+"');";
    				stmt2.executeUpdate(sql);
    				}catch(Exception e) {
    					e.printStackTrace();
    				}
    		}
    		};
    		double delay = d;
    		System.out.println("delayy "+ d);
            scheduler.schedule(task, (long) delay, TimeUnit.MINUTES);
            scheduler.shutdown();
    		System.out.println("delay complete");
    		}
    		if(role.equals("j")) {
    			if(dayno>=2 && dayno<=6) {
    				if(hours>9 && hours<18)
        			{
        				d=0;
        				dupday=dayno;
        				dayq=getDayOfWeek(dupday);
        				flagtime=0;
        			}
        			else if(hours>=18){
        				int r=24-hours;
        				int f=60-minutes;
        				double h=((double)f)/100;
        				d=(r+9+h)-1;
        				dupday=dayno+1;
        				dayq=getDayOfWeek(dupday);
        				flagtime=1;
        			}
        			else if(hours<9) {
        				int r=9-hours;
        				int f=60-minutes;
        				double h=((double)f)/100;
        				d=(r+h)-1;
        				dupday=dayno;
        				dayq=getDayOfWeek(dupday);
        				flagtime=1;
        			}
    			}
    			if(dayno==7) {
    				if(hours>9 && hours<14)
        			{
        				d=0;
        				dupday=dayno;
        				dayq=getDayOfWeek(dupday);
        				flagtime=0;
        			}
        			else if(hours>=14){
        				int r=24-hours;
        				int p=r+24;
        				int f=60-minutes;
        				double h=((double)f)/100;
        				d=(p+9+h)-1;
        				dayno=dayno+2;
        				if(dupday>7) {
        					dupday=Math.abs(dupday-7);
        				}
            			dayq=getDayOfWeek(dupday);
            			flagtime=1;
        			}
        			else if(hours<9) {
        				int r=9-hours;
        				int f=60-minutes;
        				double h=((double)f)/100;
        				d=(r+h)-1;
        				dupday=dayno;
        				dayq=getDayOfWeek(dupday);
        				flagtime=1;
        			}
    			}
    			if(dayno==1) {
    				int r=24-hours;
    				int f=60-minutes;
    				double h=((double)f)/100;
        			d=(r+9+h)-1;
        			dupday=dayno+1;
        			dayq=getDayOfWeek(dupday);
        			flagtime=1;
    			}
        		ScheduledExecutorService scheduler= Executors.newSingleThreadScheduledExecutor();
        		System.out.println("service start");
        		Runnable task=new Runnable() {
        			
        			public void run() {
        				Statement stmt2=null;
        				System.out.println("delayyyyyyjunior");
        				try {
        					stmt2=conn.createStatement();
        				String sql = "INSERT INTO taskDetails (taskName,taskCreatedby,taskStatus,taskDescription,taskCreatedTime,userId,estimatedtime) "
        		                + "VALUES ('"+taskname+"','"+userN+"','nottaken','"+taskdescription+"','"+dtf.format(now)+"','"+ui+"','"+estitime+"');";
        				stmt2.executeUpdate(sql);
        				}catch(Exception e) {
        					e.printStackTrace();
        				}
        		}
        		};
        		double delay = d;
        		System.out.println("delay "+d);
                scheduler.schedule(task, (long) delay, TimeUnit.HOURS);
                scheduler.shutdown();
        		System.out.println("delay complete");
        		}
    		rw=stmt.executeQuery("select createusername from newUser where userid='"+ui+"';");
			if(rw.next()) {
				cname=rw.getString("createusername");
			}
    		JSONObject jobj=new JSONObject();
	         jobj.put("result", "taskadded");
			jobj.put("assigntoo",cname);
			jobj.put("delay",d);
			jobj.put("delaydate",dayq);
			jobj.put("flagtime",flagtime);
	         jarr.put(jobj);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}finally {
				try {				
					if(rs!=null) {
						rs.close();
					}
					if(stmt!=null) {
						stmt.close();
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	public void addtask(JSONArray jarr,String taskname,String taskdescription,String taskassign,String userN,int estitime) {
		connect();
		if(conn==null) {
			System.out.println("DB connection not available");
	     }
		else {
			Statement stmt=null;
			ResultSet rs=null;
			ResultSet rw=null;
			ResultSet rq=null;
			try {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				LocalDateTime now = LocalDateTime.now();  
				stmt = conn.createStatement();
				rs=stmt.executeQuery("select userId from newUser where createusername='"+taskassign+"';");
                if(rs.next()) {                
				int uid=rs.getInt("userId");
				String sql = "INSERT INTO taskDetails (taskName,taskCreatedby,taskStatus,taskDescription,taskCreatedTime,userId,estimatedtime) "
		                + "VALUES ('"+taskname+"','"+userN+"','nottaken','"+taskdescription+"','"+dtf.format(now)+"','"+uid+"','"+estitime+"');";
				stmt.executeUpdate(sql);
				         JSONObject jobj=new JSONObject();
				         jobj.put("result", "taskadded");
				         jarr.put(jobj);
                }
        		               
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally {
				try {
					if(rs!=null) {
						rs.close();
					}
					if(rw!=null) {
						rw.close();
					}
					if(rq!=null) {
						rq.close();
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
	public void updatetask(int idtask,String tstatus) {
		connect();
		if(conn==null) {
			System.out.println("DB connection not available");
	     }
		else {
			Statement stmt=null;
			try {
				stmt = conn.createStatement();
				String sql = "update taskDetails set taskstatus='"+tstatus+"' where taskid='"+idtask+"';";  
		        stmt.executeUpdate(sql);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
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
}