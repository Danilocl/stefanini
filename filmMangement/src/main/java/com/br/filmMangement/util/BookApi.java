package com.br.filmMangement.util;

import com.br.filmMangement.dto.BookDTO;
import com.google.gson.Gson;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

public class BookApi {

	public static BookDTO getBook(String title) throws UnirestException {
		Gson gson = new Gson();

		HttpResponse<String> getTitle = Unirest.get("http://localhost:8080/books/title/{title}")
				.routeParam("title", title).asString();

		if (getTitle.getStatus() == 200) {
			BookDTO dto = gson.fromJson(getTitle.getBody(), BookDTO.class);
			return dto;
		} else {
			return null;
		}

	}

}
