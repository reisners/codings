package de.rhyznr.vaadin.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Presenter {

	private static final Logger log = LoggerFactory.getLogger(LoginView.class);
	
	private MainView mainView;

	@Autowired 
	AuthenticationManager authenticationManager;
	
	public Presenter(MainView mainView) {
		this.mainView = mainView;
	}

	public void login(String username, String password) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authenticationManager.authenticate(token);
		mainView.reinitializeSession();
		SecurityContextHolder.getContext().setAuthentication(authentication);
		log.info("user "+username+" successfully logged in");
		onInit();
	}

	public boolean isUserAuthenticated() {
		return SecurityContextHolder.getContext().getAuthentication() != null;
	}

	public String getLoggedInUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = (String) principal;
		return username;
	}
	
	public void onInit() {
		if (isUserAuthenticated()) {
			mainView.showAnother();
		} else {
			mainView.showLoginView();
		}
	}

	
}
