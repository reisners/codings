package de.rhyznr.vaadin.web;

import com.vaadin.navigator.View;
import com.vaadin.ui.UI;

public interface ApplicationView extends View {
	default Presenter getPresenter() {
		return ((ApplicationUI)UI.getCurrent()).getPresenter();
	}
}
