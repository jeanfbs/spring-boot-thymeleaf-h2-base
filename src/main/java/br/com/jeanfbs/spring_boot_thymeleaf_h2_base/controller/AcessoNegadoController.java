package br.com.jeanfbs.spring_boot_thymeleaf_h2_base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/acessoNegado")
public class AcessoNegadoController {

    @GetMapping
    public ModelAndView acessoNegado() {

        return new ModelAndView("pages/acessoNegado");
    }
}
