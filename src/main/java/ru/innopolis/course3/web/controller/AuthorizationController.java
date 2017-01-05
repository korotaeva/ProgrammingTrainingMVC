package ru.innopolis.course3.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.innopolis.course3.bl.UserBL;
import ru.innopolis.course3.dao.DataException;
import ru.innopolis.course3.pojo.Role;
import ru.innopolis.course3.pojo.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by korot on 04.01.2017.
 */
@RequestMapping(value = { "/", "/authorization" })
@Controller
public class AuthorizationController {
    public static Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    @RequestMapping(method =  {RequestMethod.GET})
    public String getAuthorization() {
        return "index";
    }

    private void ErrorProcessing(String errorStr, Exception e, ModelAndView model){
        logger.error(errorStr, e);
        model.addObject("error", errorStr);
    }

    @RequestMapping(method =  {RequestMethod.POST})
    public ModelAndView getAuthorizationSave(HttpServletRequest req) {
        ModelAndView model = new ModelAndView();
        model.setViewName("subject");

        UserBL userBL = null;
        try {
            userBL = new UserBL();
        } catch (DataException e) {
            ErrorProcessing("Ошибка при чтении пользователя", e, model);
            model.setViewName("error");
            return model;
        }

        if (req.getParameter("save") != null){
            String name = req.getParameter("user");
            String password = req.getParameter("password");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            User user = new User(name,userBL.md5Apache(password,name),email,phone, Role.ROLE_USER);
            try {
                user = userBL.create(user);
            }
            catch (DataException e){
                ErrorProcessing("Ошибка при создании пользователя", e, model);
                model.setViewName("error");
                return model;
            }
            model.addObject("user", user);

            if (user != null){
                HttpSession httpSession = req.getSession();
                httpSession.setAttribute("id", user.getId());
            }
            model.setViewName("redirect:/subject");

        } else if (req.getParameter("cancel") != null){
            model.setViewName("index");
        }
        else if (req.getParameter("login") != null){
            String name = req.getParameter("user");
            String password = req.getParameter("password");
            Integer id = null;
            try {
                id = userBL.getIdUser(name,userBL.md5Apache(password, name));
            }
            catch (DataException e){
                ErrorProcessing("Ошибка при получении польвателя по логину и паролю", e, model);
                model.setViewName("error");
                return model;
            }

            if (id != null){
                HttpSession httpSession = req.getSession();
                httpSession.setAttribute("id", id);
            }
            else {
                model.setViewName("index");
            }
            model.setViewName("redirect:/subject");

        } else if (req.getParameter("registration") != null) {
            model.setViewName("registration");
        }
        return model;
    }
}
