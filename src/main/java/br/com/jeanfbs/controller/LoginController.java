package br.com.jeanfbs.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Value("${application.name}")
    private String applicationName;

    @GetMapping("/auth")
    public ModelAndView login() {

        ModelAndView view = new ModelAndView("pages/login");
        view.addObject("applicationName", applicationName);
        return view;
    }

    @GetMapping("/login-error")
    public ModelAndView loginError(Model model) {
        model.addAttribute("loginError", true);
        return new ModelAndView("pages/login");
    }
}
