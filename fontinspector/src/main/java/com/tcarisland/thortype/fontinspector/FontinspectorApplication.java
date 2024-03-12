package com.tcarisland.thortype.fontinspector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class FontinspectorApplication {

	public static void main(String[] args) {

		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty(
				"com.apple.mrj.application.apple.menu.about.name", "Name");
		SpringApplicationBuilder builder = new SpringApplicationBuilder(FontinspectorApplication.class);
		builder.headless(false);
		builder.run(args);
	}

}
