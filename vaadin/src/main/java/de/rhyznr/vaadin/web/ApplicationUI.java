package de.rhyznr.vaadin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.i18n.I18N;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
@Theme("valo")
public class ApplicationUI extends UI implements MainView {

	@Value("${window.title:Application UI}")
	private String windowTitle;

	@Autowired
	private ContentDisplay content;
	
	@Autowired
	@Lazy
	SpringNavigator navigator;
	
	@Autowired
	@Lazy
	private Presenter presenter;
	
	@Autowired
	I18N i18n;

	@Autowired
	EventBus.UIEventBus eventBus;
	
	private Label loggedInUser;

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle(windowTitle);
		loggedInUser = new Label();
		loggedInUser.setSizeUndefined();
		Button logout = new Button(i18n.get("button.logout", "Logout"));
		logout.addClickListener(event -> presenter.onLogoutButtonPressed());
		final HorizontalLayout header = new HorizontalLayout(loggedInUser, logout);
		header.addStyleName("header");
		header.setExpandRatio(loggedInUser, 1);
		header.setComponentAlignment(loggedInUser, Alignment.BOTTOM_RIGHT);
		header.setExpandRatio(logout, 0);
		header.setSpacing(true);
		header.setHeightUndefined();
		header.setWidth("100%");
		final VerticalLayout root = new VerticalLayout(header, content);
		root.setSizeFull();
		root.setExpandRatio(header, 0);
		root.setExpandRatio(content, 1);
		setContent(root);
		root.setSizeFull();
		
		navigator.getState();

		presenter.onInit();
	}

	@Override
	public void showAnother() {
		getNavigator().navigateTo(AnotherView.VIEW_NAME);
		showLoggedInUser();
	}
	
	@Override
	public void showLoginView() {
		getNavigator().navigateTo(LoginView.VIEW_NAME);
	}

	@Override
	public void showLoggedInUser() {
		loggedInUser.setCaption(presenter.getLoggedInUser().orElse(""));
	}

	@Override
	public void reinitializeSession() {
		//FIXME: will not work on its own with @Push
		VaadinService.reinitializeSession(VaadinService.getCurrentRequest());
	}
}
