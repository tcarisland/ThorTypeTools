package com.tcarisland.thortype.fontinspector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class FontinspectorApplication {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false");
		initProperties();
		SpringApplicationBuilder builder = initBuilder();
		builder.run(args);
	}

	public static SpringApplicationBuilder initBuilder() {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(FontinspectorApplication.class);
		builder.headless(false);
		return builder;
	}

	public static void initProperties() {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Name");
	}

}
