package de.rhyznr.vaadin.web;

import javax.annotation.PostConstruct;

import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.HorizontalLayout;

@SpringViewDisplay
public class ContentDisplay extends HorizontalLayout {

	@PostConstruct
	public void init() {
		setSizeFull();
	}
}
