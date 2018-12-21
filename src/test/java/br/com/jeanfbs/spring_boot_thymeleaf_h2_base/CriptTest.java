package br.com.jeanfbs.spring_boot_thymeleaf_h2_base;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class CriptTest {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    @Test
    public void teste(){

        String senha = encoder.encode("ap123");
        System.out.println(senha);
    }
}
