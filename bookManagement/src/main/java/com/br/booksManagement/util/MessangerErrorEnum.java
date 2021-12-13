package com.br.booksManagement.util;

public enum MessangerErrorEnum {

	REPEATED("Book already registered, please insert another Title"), EMPTY("There is no registered book"),
	NOTFOUND("Book not found"),
	FILMNOTFOUND("Film not found"),
	RELATEDFILMNOTFOUND("The book searched does not have any corresponding book registered in the database.");

	private String messenger;

	MessangerErrorEnum(String messenger) {
		this.messenger = messenger;
	}

	public String getMessenger() {
		return messenger;
	}

}
