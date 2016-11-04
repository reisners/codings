package de.rhyznr.vaadin.web;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = AnotherView.VIEW_NAME)
public class AnotherView extends VerticalLayout implements View {

	public final static String VIEW_NAME = "Another";
	
	@PostConstruct
	public void init() {
		setCaption("Another View");
		Button btn = new Button("Klick me");
		btn.addStyleName("default");
		btn.setId("abc123");
		addComponent(btn);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
	}

}
