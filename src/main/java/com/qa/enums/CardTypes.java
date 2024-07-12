package com.qa.enums;

public enum CardTypes {
	
	LINK("article"), UPLOAD("upload"), POLL("poll"), TEXT("text"), SCORM("scorm"), QUIZ("quiz");

	private CardTypes(String name) {
		this.name = name;
	}

	String name;

	public String getName() {
		return this.name;
	}
}
