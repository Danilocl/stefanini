package com.br.filmMangement.util;

public enum MessangerErrorEnum {

	REPEATED("Film already registered, please insert another Title"), EMPTY("There is no registered film"),
	NOTFOUND("Film not found"),
	BOOKNOTFOUND("Books not found"),
	RELATEDBOOKNOTFOUND("The film searched does not have any corresponding book registered in the database.");

	private String messenger;

	MessangerErrorEnum(String messenger) {
		this.messenger = messenger;
	}

	public String getMessenger() {
		return messenger;
	}
}
