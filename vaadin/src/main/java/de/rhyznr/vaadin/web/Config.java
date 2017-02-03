package de.rhyznr.vaadin.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.vaadin.spring.annotation.EnableVaadinExtensions;
import org.vaadin.spring.events.annotation.EnableEventBus;
import org.vaadin.spring.i18n.MessageProvider;
import org.vaadin.spring.i18n.ResourceBundleMessageProvider;
import org.vaadin.spring.i18n.annotation.EnableI18N;

import com.vaadin.spring.access.ViewAccessControl;
import com.vaadin.spring.annotation.EnableVaadinNavigation;
import com.vaadin.ui.UI;

import de.rhyznr.vaadin.security.SecurityUtils;

@Configuration
@EnableEventBus
@EnableVaadinNavigation
@EnableVaadinExtensions
@EnableI18N
public class Config {
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	@Lazy
	Presenter presenter;
	
	@Bean
	MessageProvider communicationMessages() {
		return new ResourceBundleMessageProvider("messages"); // refers to src/main/resources/messages.properties
	}

	@Bean
	ViewAccessControl vaadinAccessControl() {
		return new ViewAccessControl() {
			private Logger log = LoggerFactory.getLogger(this.getClass());
			
			@Override
			public boolean isAccessGranted(UI ui, String beanName) {
				SecuredView securedView = applicationContext.findAnnotationOnBean(beanName, SecuredView.class);
				if (securedView != null) {
					Authentication authentication = presenter.getAuthentication();
					if (authentication == null) {
						log.info("access to "+beanName+" denied (no user is logged in)");
						return false;
					}
					Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
					Set<String> authorities = grantedAuthorities.stream().map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.toSet());
					List<String> requiredRoles = new ArrayList(Arrays.asList(securedView.requiredRoles()));
					requiredRoles.removeAll(authorities);
					if (!requiredRoles.isEmpty()) {
						log.info("access to "+beanName+" denied (user "+SecurityUtils.getPrincipal(authentication).getUsername()+" is missing authorities "+requiredRoles);
						return false;
					}
				}
				log.info("granting access to "+beanName);
				return true;
			}
			
		};
	}
}
