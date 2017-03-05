package services;

import dao.FeedbackDAO;
import model.Feedbacks;

public class FeedBackServices {
	private FeedbackDAO feedbackDAO=new FeedbackDAO();
	public boolean insertFeedBack(Feedbacks feedbacks){
		return feedbackDAO.insertFeedbacks(feedbacks)>0;
	}
}
