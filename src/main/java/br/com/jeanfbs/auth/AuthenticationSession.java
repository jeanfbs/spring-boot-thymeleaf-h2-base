package br.com.jeanfbs.auth;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationSession {

    public static String getUserLogged(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        throw new RuntimeException("Usuário anonimo");
    }

    public static String getRoleLoggerd(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getAuthorities().stream().map(a -> ((GrantedAuthority) a).getAuthority()).findFirst().get();
        }
        throw new RuntimeException("Usuário anonimo");
    }
}
