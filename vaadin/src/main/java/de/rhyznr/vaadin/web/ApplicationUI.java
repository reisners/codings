package de.rhyznr.vaadin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventBusListener;
import org.vaadin.spring.i18n.I18N;

import com.vaadin.annotations.Theme;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.rhyznr.vaadin.security.SecurityUtils;
import de.rhyznr.vaadin.web.Presenter.AcknowledgeErrorEvent;
import de.rhyznr.vaadin.web.Presenter.UserSessionEndedEvent;
import de.rhyznr.vaadin.web.Presenter.UserSessionStartedEvent;

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

	private Button navToAdmin;

	private Button navToAnother;

	private VerticalLayout sidebar;

	private AbstractComponent navToDefault;
	
	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle(windowTitle);
		loggedInUser = new Label();
		loggedInUser.setSizeUndefined();
		Button logout = new Button();
		logout.setIcon(FontAwesome.SIGN_OUT, i18n.get("button.logout", "Logout"));
		logout.setDescription(i18n.get("button.logout", "Logout"));
		logout.addClickListener(event -> presenter.onLogoutButtonPressed());
		final HorizontalLayout header = new HorizontalLayout(loggedInUser, logout);
		header.addStyleName("header");
		header.setExpandRatio(loggedInUser, 1);
		header.setComponentAlignment(loggedInUser, Alignment.BOTTOM_RIGHT);
		header.setExpandRatio(logout, 0);
		header.setSpacing(true);
		header.setHeightUndefined();
		header.setWidth("100%");
		navToAdmin = new Button(i18n.get("sidebar.nav.to.admin"));
		navToAdmin.addStyleName("borderless");
		navToAnother = new Button(i18n.get("sidebar.nav.to.another"));
		navToAnother.addStyleName("borderless");
		navToDefault = new Button(i18n.get("sidebar.nav.to.default"));
		navToDefault.addStyleName("borderless");
		sidebar = new VerticalLayout(navToDefault, navToAnother, navToAdmin);
		sidebar.setSizeUndefined();
		hideSidebar();
		final HorizontalLayout center = new HorizontalLayout(sidebar, content);
		center.setExpandRatio(sidebar, 0);
		center.setExpandRatio(content, 1);
		final VerticalLayout root = new VerticalLayout(header, center);
		root.setSizeFull();
		root.setExpandRatio(header, 0);
		root.setExpandRatio(center, 1);
		setContent(root);
		root.setSizeFull();
		
//		navigator.getState();
		
		navigator.setErrorView(ErrorView.class);

		eventBus.subscribe(new EventBusListener<Presenter.ReinitializeSessionEvent>() {
			@Override
			public void onEvent(org.vaadin.spring.events.Event<Presenter.ReinitializeSessionEvent> event) {
				reinitializeSession();
			}
		});
		
		eventBus.subscribe(new EventBusListener<Presenter.UserSessionStartedEvent>() {
			@Override
			public void onEvent(org.vaadin.spring.events.Event<UserSessionStartedEvent> event) {
				showLoggedInUser();
				showSidebar();
				showAnother();
			}
		});
		
		eventBus.subscribe(new EventBusListener<Presenter.AcknowledgeErrorEvent>() {
			@Override
			public void onEvent(org.vaadin.spring.events.Event<AcknowledgeErrorEvent> event) {
				showLoggedInUser();
				showAnother();
			}
		});
		
		eventBus.subscribe(new EventBusListener<Presenter.UserSessionEndedEvent>() {
			@Override
			public void onEvent(org.vaadin.spring.events.Event<UserSessionEndedEvent> event) {
				showLoggedInUser();
				hideSidebar();
				showLoginView();
			}
		});
	}
	
	private void showSidebar() {
		navToAdmin.setEnabled(SecurityUtils.hasRole(presenter.getAuthentication(), "ROLE_ADMIN"));
		sidebar.setVisible(true);
	}
	
	private void hideSidebar() {
		sidebar.setVisible(false);
	}

	public void showDefault() {
		getNavigator().navigateTo(DefaultView.VIEW_NAME);
	}

	public void showAnother() {
		getNavigator().navigateTo(AnotherView.VIEW_NAME);
		showLoggedInUser();
	}
	
	public void showLoginView() {
		getNavigator().navigateTo(LoginView.VIEW_NAME);
	}

	@Override
	public void showLoggedInUser() {
		loggedInUser.setCaption(presenter.getLoggedInUser().orElse(""));
	}

	private void reinitializeSession() {
		//FIXME: will not work on its own with @Push
		VaadinService.reinitializeSession(VaadinService.getCurrentRequest());
	}

	Presenter getPresenter() {
		return presenter;
	}
}
