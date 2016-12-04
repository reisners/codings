package de.rhyznr.vaadin.security;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Authority {
	@Id
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Authority withName(String name) {
		setName(name);
		return this;
	}
}
