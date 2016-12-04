package de.rhyznr.vaadin.security;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Account {
	@Id
	private String username;
	private String password;
	@ManyToMany
	private Set<Authority> authorities = new HashSet<>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public Account withUsername(String userrname) {
		setUsername(userrname);
		return this;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}
	
	public Account withAuthority(Authority authority) {
		getAuthorities().add(authority);
		return this;
	}
}