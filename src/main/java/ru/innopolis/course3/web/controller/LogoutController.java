package ru.innopolis.course3.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by korot on 04.01.2017.
 */
@RequestMapping(value = { "/logout" })
@Controller
public class LogoutController {

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/subject");
        return model;
    }
}
