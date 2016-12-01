package de.rhyznr.vaadin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.vaadin.spring.i18n.MessageProvider;
import org.vaadin.spring.i18n.ResourceBundleMessageProvider;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Application {
	public static void main(String [] args) {
		SpringApplication.run(Application.class, args);
	}
}
