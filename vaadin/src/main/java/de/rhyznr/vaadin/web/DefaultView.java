package de.rhyznr.vaadin.web;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View {
	public final static String VIEW_NAME = "";

	private static final Logger log = LoggerFactory.getLogger(LoginView.class);
	@PostConstruct
    void init() {
        addComponent(new Label("This is the default view"));
    }
	@Override
	public void enter(ViewChangeEvent event) {
		log.debug("enter()");
		getUI().getNavigator().navigateTo("Login");
	}
}