package ru.innopolis.course3.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by korot on 04.01.2017.
 */
@RequestMapping(value = { "/registration" })
@Controller
public class RegistrationController {
    @RequestMapping(method =  {RequestMethod.GET, RequestMethod.POST})
    public String getRegistration() {
        return "registration";
    }
}
