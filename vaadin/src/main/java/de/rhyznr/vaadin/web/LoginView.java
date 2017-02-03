package de.rhyznr.vaadin.web;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.vaadin.spring.i18n.I18N;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.AbstractErrorMessage.ContentMode;
import com.vaadin.server.ErrorMessage.ErrorLevel;
import com.vaadin.server.UserError;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.LoginForm;

@SpringView(name = LoginView.VIEW_NAME)
@UIScope
public class LoginView extends LoginForm implements View {

	private static final Logger log = LoggerFactory.getLogger(LoginView.class);
	
	public final static String VIEW_NAME = "Login";
	
	@Autowired
	private Presenter presenter;
	
	@Autowired
	private I18N i18n;
	
	@PostConstruct
	public void init() {
		
		setCaption(i18n.get("login.caption"));
		setUsernameCaption(i18n.get("login.username.caption"));
		setPasswordCaption(i18n.get("login.password.caption"));
		setLoginButtonCaption(i18n.get("login.button.caption"));
		
		addLoginListener(new LoginListener() {

			@Override
			public void onLogin(LoginEvent event) {
				if (!presenter.isUserAuthenticated()) {
					String username = event.getLoginParameter("username");
					String password = event.getLoginParameter("password");
					log.info("login attempt with username "+username+" password "+password);
					try {
						presenter.onUsernamePasswordEntered(username, password);
					} catch (AuthenticationException e) {
						setComponentError(new UserError(i18n.get(e.getMessage()), ContentMode.TEXT, ErrorLevel.ERROR));
					}
				} else {
					log.info("user "+presenter.getLoggedInUser()+" is already logged in");
				}
			}
			
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// reset the fields here
	}

}
