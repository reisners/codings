package de.rhyznr.vaadin.web;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = AnotherView.VIEW_NAME)
@SecuredView(requiredRoles = {"ROLE_USER"})
public class AnotherView extends VerticalLayout implements ApplicationView {

	public final static String VIEW_NAME = "another";
	
	@Autowired
	I18N i18n;
	
	@PostConstruct
	public void init() {
		setCaption(i18n.get("another.view.caption", "Another View"));
		Button btn = new Button(i18n.get("button.clickMe", "Click me"));
		btn.addStyleName("primary");
		btn.setId("abc123");
		addComponent(btn);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
	}

}
