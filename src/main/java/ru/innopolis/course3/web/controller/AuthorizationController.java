package ru.innopolis.course3.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.innopolis.course3.bl.IUserBL;
import ru.innopolis.course3.dao.DataException;
import ru.innopolis.course3.pojo.Role;
import ru.innopolis.course3.pojo.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by korot on 04.01.2017.
 */
@RequestMapping(value ="/authorization")
@Controller
public class AuthorizationController {
    public static Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    private BCryptPasswordEncoder bcryptEncoder;

    IUserBL userBL;

    @Autowired
    public AuthorizationController(IUserBL userBL, BCryptPasswordEncoder bcryptEncoder) {
        this.userBL = userBL;
        this.bcryptEncoder = bcryptEncoder;
    }

    private void ErrorProcessing(String errorStr, Exception e, ModelAndView model){
        logger.error(errorStr, e);
        model.addObject("error", errorStr);
    }

    @RequestMapping(method =  {RequestMethod.POST})
    public ModelAndView getAuthorizationSave(HttpServletRequest req) {
        ModelAndView model = new ModelAndView();
        model.setViewName("subject");

        if (req.getParameter("save") != null){
            String name = req.getParameter("user");
            String password = req.getParameter("password");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");

            User user = new User(name, bcryptEncoder.encode(password), email,phone, Role.ROLE_USER);
            try {
                user = userBL.create(user);
            }
            catch (DataException e){
                ErrorProcessing("Ошибка при создании пользователя", e, model);
                model.setViewName("error");
                return model;
            }
            model.addObject("user", user);

            model.setViewName("redirect:/subject");

        } else if (req.getParameter("cancel") != null){
            model.setViewName("redirect:/login");
        } else if (req.getParameter("login") != null){
        /*    String name = req.getParameter("user");
            String password = req.getParameter("password");
            Integer id = null;
            try {
                id = userBL.getIdUser(name,bcryptEncoder.encode(password));
            }
            catch (DataException e){
                ErrorProcessing("Ошибка при получении польвателя по логину и паролю", e, model);
                model.setViewName("error");
                return model;
            }*/
            model.setViewName("redirect:/subject");
        }
        else if (req.getParameter("registration") != null) {
            model.setViewName("registration");
        }
        return model;
    }
}
