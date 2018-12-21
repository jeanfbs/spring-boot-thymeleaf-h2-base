package br.com.jeanfbs.spring_boot_thymeleaf_h2_base.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthSessionHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
        if(role.equals("ROLE_ADMIN") || role.equals("ROLE_APOIO")){
            response.sendRedirect("/cpanel/bar/");
        }
        else if(role.equals("ROLE_CAIXA")){
            response.sendRedirect("/cpanel/participante/");
        }
    }
}
