package de.rhyznr.vaadin.security;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Profile("SECURITY_MOCKED")
@Primary
@Service
public class AuthenticationProviderMock implements AuthenticationProvider {
 
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Account myUser = getAccount(authentication.getPrincipal());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(myUser, "", mockGrantedAuthorities(myUser));
        usernamePasswordAuthenticationToken.setDetails(myUser);
        return usernamePasswordAuthenticationToken;
    }
 
    private Account getAccount(Object principal) throws AuthenticationException {
    	if (principal != null && principal instanceof String) {
    		switch ((String)principal) {
    		case "uuser":
    			return new Account().withUsername("uuser").withAuthority(new Authority().withName("ROLE_USER"));
			case "aadmin":
				return new Account().withUsername("aadmin").withAuthority(new Authority().withName("ROLE_ADMIN"));
    		}
    	}
    	throw new UsernameNotFoundException("error.invalid.username.password");
    }
 
    @Override
    public boolean supports(final Class<?> authentication) {
        return true;
    }
 
    private List<GrantedAuthority> mockGrantedAuthorities(Account account) {
    	List<String> roles = account.getAuthorities().stream().map(authority -> authority.getName()).collect(Collectors.toList());
		return AuthorityUtils.createAuthorityList(roles.toArray(new String[] {}));
    }
}