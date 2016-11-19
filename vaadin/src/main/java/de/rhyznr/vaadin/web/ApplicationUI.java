package de.rhyznr.vaadin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
@Theme("valo")
public class ApplicationUI extends UI implements MainView {

	@Value("${window.title:Application UI}")
	private String windowTitle;

	@Autowired
	private SpringViewProvider viewProvider;

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle(windowTitle);
		final VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		root.setMargin(true);
		root.setSpacing(true);
		setContent(root);

		final ViewDisplay viewDisplay = new Navigator.ComponentContainerViewDisplay(root);
		root.setSizeFull();

		Navigator navigator = new Navigator(this, viewDisplay);
		navigator.addProvider(viewProvider);
	}

	@Override
	public void showAnother() {
		getNavigator().navigateTo(AnotherView.VIEW_NAME);
	}
}
