package dao;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class DbConnect {
	private String dbClass="com.mysql.jdbc.Driver";
	private String url="jdbc:mysql://localhost:3306/chatbot1.0";
	private String user="root";
	private String pass="trantien";
	private Connection conn;
	public static DbConnect dbConnect;
	public static DbConnect getInstanceDbConnect(){
		if(dbConnect==null){
			dbConnect=new DbConnect();
		}
		return dbConnect;
	}
	public DbConnect() {
		try {
			Class.forName(dbClass);
			conn=(Connection) DriverManager.getConnection(url, user, pass);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Connection getConn(){
		return conn;
	}
	public ResultSet executeSelect(String sql){
		ResultSet rs=null;
		try {
			Statement stm=(Statement) conn.createStatement();
			rs=(ResultSet) stm.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public static void main(String[] args) {
		if(DbConnect.getInstanceDbConnect()==null){
			System.out.println("NO");
		}else System.out.println("Yes");
	}
}
