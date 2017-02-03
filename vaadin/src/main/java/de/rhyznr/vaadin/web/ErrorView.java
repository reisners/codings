package de.rhyznr.vaadin.web;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventBusListener;
import org.vaadin.spring.i18n.I18N;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.rhyznr.vaadin.web.Presenter.ErrorViewEvent;

@UIScope
@SpringView(name=ErrorView.VIEW_NAME)
public class ErrorView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "error";
	@Autowired
	Presenter presenter;
	@Autowired
	I18N i18n;
	@Autowired
	EventBus.UIEventBus eventBus;
	
	private Label message;
	private Button button;
	private Runnable clickHandler;
	
	@PostConstruct
	public void init() {
		setWidth("100%");
		setHeightUndefined();
		message = new Label();
		addComponent(message);
		button = new Button();
		button.addStyleName("link");
		button.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				clickHandler.run();
			}
		});
		addComponent(button);
		
		eventBus.subscribe(new EventBusListener<Presenter.ErrorViewEvent>() {
			@Override
			public void onEvent(org.vaadin.spring.events.Event<ErrorViewEvent> event) {
				message.setValue(String.format(i18n.get(event.getPayload().getMessageKey()), event.getPayload().getMessageParameters()));
				button.setCaption(i18n.get(event.getPayload().getButtonCaptionKey()));
				clickHandler = event.getPayload().getButtonClickHandler();
			}
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		presenter.onEnterErrorView(event.getViewName());
	}

}
