package br.com.jeanfbs.config;

import br.com.jeanfbs.auth.AuthSessionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
        .antMatchers("/css/**","/js/**", "/images/**", "/vendor/**", "/console/**", "/actuator/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

            http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/cpanel/produtos/*").hasAnyRole("ADMIN")
                    .antMatchers("/cpanel/participante/*").hasAnyRole("ADMIN", "CAIXA")
                    .antMatchers("/cpanel/credito/*").hasAnyRole("ADMIN", "CAIXA")
                    .antMatchers("/cpanel/bar/*").hasAnyRole("ADMIN", "APOIO")
                .anyRequest()
                    .denyAll().and()
                .formLogin()
                    .loginProcessingUrl("/auth")
                    .loginPage("/auth")
                    .failureUrl("/login-error")
                    .successHandler( new AuthSessionHandler())
                .permitAll()
                .and()
                    .exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
                @Override
                public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                    response.sendRedirect(request.getContextPath() + "/acessoNegado");
                }
            })
                .and()
                .logout()
                .invalidateHttpSession(true);
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        // senha ad123
        auth.inMemoryAuthentication().withUser("admin").password("$2a$10$4rOiTpY8gWDpjQO.K20A/O9faF3D3PxpbuJ2rTgk78wol/mCmzVlC").roles("ADMIN");
        // senha cx123
        auth.inMemoryAuthentication().withUser("caixa").password("$2a$10$m8K5tQuv4ISZKR.QP/i98eAm4AORqrNtLGqHjPFTQiRsVRylj/8DS").roles("CAIXA");
        // senha ap123
        auth.inMemoryAuthentication().withUser("apoio").password("$2a$10$8Qyf2CLcLuqFPY96/slHEeZ7x.1BGzNrWFKILXsYcmbRvDAUHICD.").roles("APOIO");
    }
}
