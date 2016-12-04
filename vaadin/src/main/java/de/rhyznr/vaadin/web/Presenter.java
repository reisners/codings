package de.rhyznr.vaadin.web;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import de.rhyznr.vaadin.security.Account;

@Component
@Lazy
public class Presenter {

	private static final Logger log = LoggerFactory.getLogger(Presenter.class);
	
	private MainView mainView;

	@Autowired 
	AuthenticationManager authenticationManager;
	
	public Presenter(MainView mainView) {
		this.mainView = mainView;
	}

	public void onUsernamePasswordEntered(String username, String password) throws AuthenticationException {
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

	public Optional<String> getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			Account account = (Account) principal;
			String username = account.getUsername();
			return Optional.of(username);
		} else {
			return Optional.empty();
		}
	}
	
	public void onInit() {
		mainView.showLoggedInUser();
		if (getLoggedInUser().isPresent()) {
			mainView.showAnother();
		} else {
			mainView.showLoginView();
		}
	}

	public void onLogoutButtonPressed() {
		getLoggedInUser().ifPresent(username -> {
			SecurityContextHolder.getContext().setAuthentication(null);
			log.info("user "+username+" logged off");
			onInit();
		});
	}

	
}
