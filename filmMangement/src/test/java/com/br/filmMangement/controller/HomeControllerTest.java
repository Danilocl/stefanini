package com.br.filmMangement.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class HomeControllerTest {

	@InjectMocks
	HomeController homeController;

	@Test
	public void home() {
		String server = "The server is UP!";
		String home = homeController.home();
		Assertions.assertThat(server).isNotNull().isEqualToIgnoringCase(home);

	}
}
