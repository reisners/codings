package de.rhyznr.vaadin.web;

import org.springframework.stereotype.Component;

@Component
public interface MainView {
	
	void showAnother();
	void showLoginView();
	void showLoggedInUser();
}
