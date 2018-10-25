package org.a2lpo.bank.notownbank.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "main";
            }
            else if(statusCode == HttpStatus.FORBIDDEN.value()) {
                return "main";
            }
            else if(statusCode == HttpStatus.UNAUTHORIZED.value()) {
                return "main";
            }
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
