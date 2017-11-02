package org.demostuff;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

	// inject via application.properties
	@Value("${app.welcome.message:default}")
	private String message;

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		return "index.html";
	}

}