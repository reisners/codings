package de.rhyznr.vaadin.web;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.LoginForm;

@SpringView(name = LoginView.VIEW_NAME)
public class LoginView extends LoginForm implements View {

	private static final Logger log = LoggerFactory.getLogger(LoginView.class);
	
	public final static String VIEW_NAME = "Login";
	
	@Autowired
	private Presenter presenter;
	
	@PostConstruct
	public void init() {
		
		setCaption("Login");
		addLoginListener(new LoginListener() {

			@Override
			public void onLogin(LoginEvent event) {
				log.info("login attempt with username "+event.getLoginParameter("username")+" password "+event.getLoginParameter("password"));
				presenter.login(event.getLoginParameter("username"), event.getLoginParameter("password"));
			}
			
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// reset the fields here
	}

}
