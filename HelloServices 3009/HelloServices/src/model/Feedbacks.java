package model;

public class Feedbacks {
	private int feedbackID;
	private String request;
	private String botResponse;
	private String customerResponse;
	private int rate;
	public Feedbacks(int feedbackID, String request, String botResponse, String customerResponse, int rate) {
		super();
		this.feedbackID = feedbackID;
		this.request = request;
		this.botResponse = botResponse;
		this.customerResponse = customerResponse;
		this.rate = rate;
	}
	public Feedbacks() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getFeedbackID() {
		return feedbackID;
	}
	public void setFeedbackID(int feedbackID) {
		this.feedbackID = feedbackID;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getBotResponse() {
		return botResponse;
	}
	public void setBotResponse(String botResponse) {
		this.botResponse = botResponse;
	}
	public String getCustomerResponse() {
		return customerResponse;
	}
	public void setCustomerResponse(String customerResponse) {
		this.customerResponse = customerResponse;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	
}
