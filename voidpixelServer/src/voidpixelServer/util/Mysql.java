package voidpixelServer.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Mysql {
	
	public static String connectionSql(String sel) throws SQLException{
		
		try{
			
		   Class.forName("com.mysql.jdbc.Driver");
		   
		} catch (Exception e){
			
		   e.printStackTrace();
		   
		}
		
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/voidpixel_db","root", "19Abril1994");
		
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery(sel);
		
		String w = "";
		
		while (rs.next()){
			
			w = rs.getString(1);
			
		}
		
		return w;
		
	}

}
