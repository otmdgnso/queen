package com.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn {
	private static Connection conn = null;

	private DBConn() {
	}
	
	public static Connection getConnection() {
		String url="jdbc:mysql://211.238.142.226:3306/sistboard";
		String user="root";
		String pwd="java$!";

		if(conn==null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(url, user, pwd);
			}catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		
		return conn;
	}
	
	public static void close() {
		if(conn==null)
			return;
		try {
			if(!conn.isClosed())
				conn.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		conn=null;
	}
}
