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
import org.vaadin.spring.events.EventBus;

import de.rhyznr.vaadin.security.SecurityUtils;

@Component
@Lazy
public class Presenter {

	public class ErrorViewEvent {

		private String messageKey;
		private String[] messageParameters;
		private String buttonCaptionKey;
		private Runnable buttonClickHandler;

		
		public ErrorViewEvent(String messageKey, String[] messageParameters, String buttonCaptionKey, Runnable buttonClickHandler) {
			super();
			this.messageKey = messageKey;
			this.messageParameters = messageParameters;
			this.buttonCaptionKey = buttonCaptionKey;
			this.buttonClickHandler = buttonClickHandler;
		}

		public String getMessageKey() {
			return messageKey;
		}

		public String[] getMessageParameters() {
			return messageParameters;
		}

		public String getButtonCaptionKey() {
			return buttonCaptionKey;
		}

		public Runnable getButtonClickHandler() {
			return buttonClickHandler;
		}
	}
	public class AcknowledgeErrorEvent {

	}
	public class UserSessionEndedEvent {}
	public class UserSessionStartedEvent {}
	public static class ReinitializeSessionEvent {}

	private static final Logger log = LoggerFactory.getLogger(Presenter.class);
	
	private MainView mainView;

	@Autowired 
	AuthenticationManager authenticationManager;
	
    @Autowired
    EventBus.UIEventBus eventBus;

	public Presenter(MainView mainView) {
		this.mainView = mainView;
	}

	public void onUsernamePasswordEntered(String username, String password) throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authenticationManager.authenticate(token);
		eventBus.publish(this, new Presenter.ReinitializeSessionEvent());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		log.info("user "+username+" successfully logged in");
		eventBus.publish(this, new UserSessionStartedEvent());
	}

	public boolean isUserAuthenticated() {
		return getAuthentication() != null;
	}

	public Optional<String> getLoggedInUser() {
		Authentication authentication = getAuthentication();
		if (authentication != null) {
			return Optional.of(SecurityUtils.getPrincipal(authentication).getUsername());
		} else {
			return Optional.empty();
		}
	}

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public void onLogoutButtonPressed() {
		getLoggedInUser().ifPresent(username -> {
			SecurityContextHolder.getContext().setAuthentication(null);
			log.info("user "+username+" logged off");
			eventBus.publish(this, new UserSessionEndedEvent());
		});
	}

	public void onErrorBackButtonClicked() {
		eventBus.publish(this, new AcknowledgeErrorEvent());
	}

	public void onEnterErrorView(String viewName) {
		// find out what the problem is
		if (!isUserAuthenticated()) {
			eventBus.publish(this, new ErrorViewEvent("error.not.logged.in", new String[] {}, "button.to.login", () -> eventBus.publish(Presenter.this, new UserSessionEndedEvent())));
		}
	}

	
}
