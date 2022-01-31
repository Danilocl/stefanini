package com.br.booksManagement.util;

import com.br.booksManagement.dto.FilmDTO;
import com.google.gson.Gson;

import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.HttpResponse;

public class FilmApi {

	public static FilmDTO getFilm(String title) throws UnirestException {
		Gson gson = new Gson();

		HttpResponse<String> getTitle = Unirest.get("http://localhost:8081/films/title/{title}")
				.routeParam("title", title).asString();

		if (getTitle.getStatus() == 200) {
			FilmDTO dto = gson.fromJson(getTitle.getBody(), FilmDTO.class);
			return dto;
		} else {
			return null;
		}
	}
}
