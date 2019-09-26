package com.example.eggsam;

public class MyQuestion {
	
	private String id,question;

	public MyQuestion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MyQuestion(String id, String question) {
		super();
		this.id = id;
		this.question = question;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "Question " + id;
	}
	

}
