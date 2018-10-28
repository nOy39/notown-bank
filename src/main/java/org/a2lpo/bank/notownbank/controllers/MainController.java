package org.a2lpo.bank.notownbank.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    @Value("${spring.profiles.active}")
    private String profile;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String main(Model model) {
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "main";
    }

//    @RequestMapping(value = "/{[path:[^\\.]*}")
//    public String redirect() {
//        return "forward:/";
//    }
}
