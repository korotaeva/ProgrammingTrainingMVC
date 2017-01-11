package ru.innopolis.course3.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.innopolis.course3.bl.*;
import ru.innopolis.course3.pojo.PracticalAssignments;
import ru.innopolis.course3.pojo.Subject;
import ru.innopolis.course3.dao.DataException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by korot on 04.01.2017.
 */
@RequestMapping(value = { "/subject**" })
@Controller
public class SubjectController {

    ISubjectBL subjectBL;

    @Autowired
    public SubjectController(ISubjectBL subjectbl) {
        this.subjectBL = subjectbl;
    }

    public static Logger logger = LoggerFactory.getLogger(SubjectController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getSubject(@RequestParam(value = "operation", required = false) String operation,
                                   @RequestParam(value = "pk", required = false) String pk){
       
        ModelAndView model = new ModelAndView();
        model.setViewName("subject");

        Subject subject = null;
        try {
            subject = (Subject)subjectBL.getFromPK(pk);
        }
        catch (DataException e){
            ErrorProcessing("Ошибка при чтении по ключу", e, model);
            model.setViewName("error");
            return model;
        }
        catch (NumberFormatException e) {
            ErrorProcessing("Не корректный формат ключа", e, model);
            model.setViewName("error");
            return model;
        }


        if (operation != null) {
            switch (operation){
                case "create":
                    model.setViewName("editsubject");
                    break;
                case "edit":
                    model.setViewName("editsubject");
                    model.addObject("subject", subject);
                    try {
                        if(subject != null){
                            List<PracticalAssignments> practicals= new PracticalAssignmentsBL().getAllByKey(subject.getId().toString(),"subject");
                            model.addObject("Practicals", practicals);
                        }
                    }
                    catch (DataException e){
                        ErrorProcessing("Ошибка при получении списка практичексих заданий", e, model);
                        model.setViewName("error");
                        return model;
                    }
                    break;
                case "delete":
                    try {
                        subjectBL.delete(subject);
                    }
                    catch (DataException e){
                        ErrorProcessing("Ошибка при удалении темы", e, model);
                        model.setViewName("error");
                        return model;
                    }
                    break;
            }
        }
        try {
            List<Subject> subjects= subjectBL.getAll();
            model.addObject("Subjects", subjects);
        }
        catch (DataException e){
            ErrorProcessing("Ошибка при получении списка тем", e, model);
            model.setViewName("error");
            return model;
        }
        return model;
    }

    private void ErrorProcessing(String errorStr, Exception e, ModelAndView model){
        logger.error(errorStr, e);
        model.addObject("error", errorStr);
    }


    @RequestMapping(method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public ModelAndView getSubjectSave(HttpServletRequest req,  @RequestParam(value = "operation", required = false) String operation) {
        ModelAndView model = new ModelAndView();

        if(operation != null && operation.equals("save")){
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            String id = req.getParameter("id");
            Subject subject;

            if (id == null || id.isEmpty()) {
                subject = new Subject(name, description);
                try {
                    subjectBL.create(subject);
                }
                catch (DataException e){
                    ErrorProcessing("Ошибка при создании темы", e, model);
                    model.setViewName("error");
                    return model;
                }

            } else {
                subject = new Subject(name, description, Integer.parseInt(id));
                try {
                    subjectBL.update(subject);
                }
                catch (DataException e){
                    ErrorProcessing("Ошибка при изменении темы", e, model);
                    model.setViewName("error");
                    return model;
                }
            }

        }
        model.setViewName("redirect:subject");
      ;
        return model;
    }
}
