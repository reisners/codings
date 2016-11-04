package de.rhyznr.vaadin.web;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.LoginForm;

@SpringView(name = LoginView.VIEW_NAME)
public class LoginView extends LoginForm implements View {

	private static final Logger log = LoggerFactory.getLogger(LoginView.class);
	
	public final static String VIEW_NAME = "Login";
	
	@PostConstruct
	public void init() {
		setCaption("Login");
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// reset the fields here
	}

}
