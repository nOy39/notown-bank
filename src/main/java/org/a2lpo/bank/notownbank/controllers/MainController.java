package org.a2lpo.bank.notownbank.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String main() {
        return "main";
    }git
}
