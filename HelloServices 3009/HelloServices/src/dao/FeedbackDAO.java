package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

import model.Feedbacks;

public class FeedbackDAO {
	public int insertFeedbacks(Feedbacks Feedbacks){
		DbConnect dbConnect=DbConnect.getInstanceDbConnect();
		String sql="INSERT INTO `chatbot1.0`.`feedbacks`(`request`,`botResponse`,`customerResponse`,`rate`)"
				+ "VALUES (?,?,?,?)";
		try {
			PreparedStatement preparedStatement=(PreparedStatement) dbConnect.getConn().prepareStatement(sql);
			preparedStatement.setString(1, Feedbacks.getRequest());
			preparedStatement.setString(2, Feedbacks.getBotResponse());
			preparedStatement.setString(3, Feedbacks.getCustomerResponse());
			preparedStatement.setInt(4, Feedbacks.getRate());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	public ArrayList<Feedbacks> getAll(){
		ArrayList<Feedbacks> list=null;
		DbConnect dbConnect=DbConnect.getInstanceDbConnect();
		String sql="SELECT * FROM feedbacks";
		ResultSet rs=dbConnect.executeSelect(sql);
		if(rs!=null){
			try {
				Feedbacks Feedbacks=null;
				list=new ArrayList<>();
				while(rs.next()){
					Feedbacks=new Feedbacks(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getInt(5));
					list.add(Feedbacks);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	public int delete(Feedbacks Feedbacks){
		String sql="DELETE FROM `feedbacks` WHERE `idfeedbacks`="+Feedbacks.getFeedbackID();
		DbConnect dbConnect=DbConnect.getInstanceDbConnect();
		try {
			return dbConnect.getConn().prepareStatement(sql).executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	public int update(Feedbacks Feedbacks){
		DbConnect dbConnect=DbConnect.getInstanceDbConnect();
		String sql="UPDATE `feedbacks` SET `request`=?,`botResponse`=?,`customerResponse`=?,`rate`=? WHERE `idfeedbacks`=?";
		try {
			PreparedStatement preparedStatement=(PreparedStatement) dbConnect.getConn().prepareStatement(sql);
			preparedStatement.setString(1, Feedbacks.getRequest());
			preparedStatement.setString(2, Feedbacks.getBotResponse());
			preparedStatement.setString(3, Feedbacks.getCustomerResponse());
			preparedStatement.setInt(4, Feedbacks.getRate());
			preparedStatement.setInt(5, Feedbacks.getFeedbackID());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
}
