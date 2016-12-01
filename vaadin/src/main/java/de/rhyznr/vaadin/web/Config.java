package de.rhyznr.vaadin.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vaadin.spring.annotation.EnableVaadinExtensions;
import org.vaadin.spring.events.annotation.EnableEventBus;
import org.vaadin.spring.i18n.MessageProvider;
import org.vaadin.spring.i18n.ResourceBundleMessageProvider;
import org.vaadin.spring.i18n.annotation.EnableI18N;

import com.vaadin.spring.annotation.EnableVaadinNavigation;

@Configuration
@EnableEventBus
@EnableVaadinNavigation
@EnableVaadinExtensions
@EnableI18N
public class Config {
	
	@Bean
	MessageProvider communicationMessages() {
		return new ResourceBundleMessageProvider("messages"); // refers to src/main/resources/messages.properties
	}

}
