package de.rhyznr.vaadin.security;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Profile("SECURITY_MOCKED")
@Primary
@Service
public class AuthenticationProviderMock implements AuthenticationProvider {
 
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Account myUser = getUser(authentication.getPrincipal().toString());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(myUser, "", mockGrantedAuthorities());
        usernamePasswordAuthenticationToken.setDetails(myUser);
        return usernamePasswordAuthenticationToken;
    }
 
    private Account getUser(String principal) {
        Account account = new Account();
        account.setUsername(principal);
		return account;
    }
 
    @Override
    public boolean supports(final Class<?> authentication) {
        return true;
    }
 
    private List<GrantedAuthority> mockGrantedAuthorities() {
        return Collections.emptyList();
    }
}