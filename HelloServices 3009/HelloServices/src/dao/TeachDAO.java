package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

import FileUtils.WritingFile;
import model.Teach;
import services.ConnectServerSocketTraining;

public class TeachDAO {
	public int insertTeach(Teach teach){
		DbConnect dbConnect=DbConnect.getInstanceDbConnect();
		String sql="INSERT INTO `teach` (`request`, `response`, `state`) "
				+ "VALUES (?, ?, ?);";
//		String context=teach.getRequest()+"\n"+teach.getResponse()+"\n";
		ConnectServerSocketTraining connectServerSocketTraining=ConnectServerSocketTraining.getIntance();
		connectServerSocketTraining.process(teach.getRequest());
		connectServerSocketTraining.process(teach.getResponse());
		try {
			PreparedStatement preparedStatement=(PreparedStatement) dbConnect.getConn().prepareStatement(sql);
			System.out.println("TAG: "+teach.getRequest());
			preparedStatement.setString(1, teach.getRequest());
			preparedStatement.setString(2, teach.getResponse());
			preparedStatement.setInt(3, teach.getState());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	public ArrayList<Teach> getAll(){
		ArrayList<Teach> list=null;
		DbConnect dbConnect=DbConnect.getInstanceDbConnect();
		String sql="SELECT * FROM teach";
		ResultSet rs=dbConnect.executeSelect(sql);
		if(rs!=null){
			try {
				Teach teach=null;
				list=new ArrayList<>();
				while(rs.next()){
					teach=new Teach(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
					list.add(teach);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	public int delete(Teach teach){
		String sql="DELETE FROM `teach` WHERE `idteach`="+teach.getContextID();
		DbConnect dbConnect=DbConnect.getInstanceDbConnect();
		try {
			return dbConnect.getConn().prepareStatement(sql).executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	public int update(Teach teach){
		DbConnect dbConnect=DbConnect.getInstanceDbConnect();
		String sql="UPDATE `teach` SET ``request`=?,`response`=?,`state`=? WHERE `idteach`=?";
		
		try {
			PreparedStatement preparedStatement=(PreparedStatement) dbConnect.getConn().prepareStatement(sql);
			preparedStatement.setNString(1, teach.getRequest());
			preparedStatement.setNString(2, teach.getResponse());
			preparedStatement.setInt(3, teach.getState());
			preparedStatement.setInt(4, teach.getContextID());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	public static void main(String[] args) {
		TeachDAO teachDAO=new TeachDAO();
		ArrayList<Teach> arr=new ArrayList<>();
		arr=teachDAO.getAll();
		for(int i=0;i<arr.size();i++){
			System.out.println(arr.get(i).getRequest());
		}
	}
}
