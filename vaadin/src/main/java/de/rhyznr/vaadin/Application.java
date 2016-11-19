package de.rhyznr.vaadin;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.spring.i18n.MessageProvider;
import org.vaadin.spring.i18n.ResourceBundleMessageProvider;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Application {
	public static void main(String [] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	I18N i18n(@Autowired ApplicationContext applicationContext) {
		return new I18N(applicationContext);
	}
	
	@Bean
	MessageProvider communicationMessages() {
		return new ResourceBundleMessageProvider("messages"); // refers to src/main/resources/messages.properties
	}
}
