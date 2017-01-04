package ru.innopolis.course3.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.innopolis.course3.bl.PracticalAssignmentsBL;
import ru.innopolis.course3.bl.SubjectBL;
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

    public static Logger logger = LoggerFactory.getLogger(SubjectController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getSubject(@RequestParam(value = "operation", required = false) String operation,
                                   @RequestParam(value = "pk", required = false) String pk){
        ModelAndView model = new ModelAndView();
        model.setViewName("subject");
        SubjectBL subjectBL = null;
        try {
            subjectBL = new SubjectBL();
        }
        catch (DataException e){
            ErrorProcessing("Ошибка при инициализации темы", e, model);
            model.setViewName("error");
        }


        Subject subject = null;
        try {
            subject = subjectBL.subjectFromPK(pk);
        }
        catch (DataException e){
            ErrorProcessing("Ошибка при чтении по ключу", e, model);
            model.setViewName("error");
        }
        catch (NumberFormatException e) {
            ErrorProcessing("Не корректный формат ключа", e, model);
            model.setViewName("error");
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
                            List<PracticalAssignments> practicals= new PracticalAssignmentsBL().getAllBySubject(subject.getId().toString());
                            model.addObject("Practicals", practicals);
                        }
                    }
                    catch (DataException e){
                        ErrorProcessing("Ошибка при получении списка практичексих заданий", e, model);
                        model.setViewName("error");
                    }
                    break;
                case "delete":
                    try {
                        subjectBL.delete(subject);
                    }
                    catch (DataException e){
                        ErrorProcessing("Ошибка при удалении темы", e, model);
                        model.setViewName("error");
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
        }
        return model;
    }

    private void ErrorProcessing(String errorStr, Exception e, ModelAndView model){
        logger.error(errorStr, e);
        model.addObject("error", errorStr);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView getSubjectSave(HttpServletRequest req,  @RequestParam(value = "operation", required = false) String operation,
                                       @RequestParam(value = "pk", required = false) String pk,
                                       @RequestParam(value = "subjectid", required = false) String subjectid) {
        ModelAndView model = new ModelAndView();


        if(operation != null && operation.equals("save")){
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            String id = req.getParameter("id");
            Subject subject;

            if (id == null || id.isEmpty()) {
                subject = new Subject(name, description);
                try {
                    (new SubjectBL()).create(subject);
                }
                catch (DataException e){
                    ErrorProcessing("Ошибка при создании темы", e, model);
                    model.setViewName("error");
                }

            } else {
                subject = new Subject(name, description, Integer.parseInt(id));
                try {
                    (new SubjectBL()).update(subject);
                }
                catch (DataException e){
                    ErrorProcessing("Ошибка при изменении темы", e, model);
                    model.setViewName("error");
                }
            }

        }
        model.setViewName("redirect:subject");
      ;
        return model;
    }
}
