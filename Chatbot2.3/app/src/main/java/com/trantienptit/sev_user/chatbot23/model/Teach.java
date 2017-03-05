package com.trantienptit.sev_user.chatbot23.model;

public class Teach {
	private int contextID;
	private String request;
	private String response;
	private int state;
	public Teach(int contextID, String request, String response, int state) {
		super();
		this.contextID = contextID;
		this.request = request;
		this.response = response;
		this.state = state;
	}
	public Teach() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getContextID() {
		return contextID;
	}
	public void setContextID(int contextID) {
		this.contextID = contextID;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
}
