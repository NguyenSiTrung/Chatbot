package services;

import dao.TeachDAO;
import model.Teach;

public class TeachServices {
	private TeachDAO TeachDAO=new TeachDAO();
	public boolean insertTeach(Teach Teach){
		String context=Teach.getRequest()+"\n"+Teach.getResponse()+"\n";
		System.out.println(context);
		return TeachDAO.insertTeach(Teach)>0;
	}
}
