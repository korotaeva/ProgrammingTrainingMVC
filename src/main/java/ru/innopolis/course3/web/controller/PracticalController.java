package ru.innopolis.course3.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.innopolis.course3.bl.PracticalAssignmentsBL;
import ru.innopolis.course3.bl.SubjectBL;
import ru.innopolis.course3.dao.DataException;
import ru.innopolis.course3.pojo.PracticalAssignments;
import ru.innopolis.course3.pojo.Subject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by korot on 04.01.2017.
 */
@RequestMapping(value = { "/practical**" })
@Controller
public class PracticalController {
    public static Logger logger = LoggerFactory.getLogger(SubjectController.class);



    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getSubject(@RequestParam(value = "operation", required = false) String operation,
                                   @RequestParam(value = "pk", required = false) String pk,
                                   @RequestParam(value = "subjectid", required = false) String subjectid){
        ModelAndView model = new ModelAndView();
        model.setViewName("practical");
        PracticalAssignmentsBL practicalBL = null;
        try {
            practicalBL = new PracticalAssignmentsBL();
        } catch (DataException e) {
            ErrorProcessing("Ошибка при инициализации", e, model);
            model.setViewName("error");
            return model;
        }

        PracticalAssignments practical = null;
        try {
            practical = practicalBL.practicalFromPK(pk);
        }
        catch (DataException e){
            ErrorProcessing("Ошибка при чтении практического задания по ключу", e, model);
            model.setViewName("error");
            return model;
        }
        catch (NumberFormatException e) {
            ErrorProcessing("Некорректный формат ключа", e, model);
            model.setViewName("error");
            return model;
        }
        model.setViewName("editsubject");

        Integer idSubject = practicalBL.getIdSubject(subjectid);
        if(idSubject != null)
            model.addObject("subjectid", idSubject);

        if (operation != null) {
            switch (operation){
                case "create":
                    model.setViewName("editpractical");
                    break;
                case "edit":
                    model.setViewName("editpractical");
                    model.addObject("practical", practical);
                    break;
                case "delete":
                    try {
                        practicalBL.delete(practical);
                    }
                    catch (DataException e){
                        ErrorProcessing("Ошибка при удалении практического задания", e, model);
                        model.setViewName("error");
                        return model;
                    }
                    model.setViewName("redirect:/subject?edit="+ idSubject);
                    break;
            }
        }
        try {
            List<PracticalAssignments> practicals= practicalBL.getAll();
            model.addObject("Practicals", practicals);
        }
        catch (DataException e){
            ErrorProcessing("Ошибка при получении списка практических заданий", e, model);
            model.setViewName("error");
            return model;
        }

        return model;
    }

    private void ErrorProcessing(String errorStr, Exception e, ModelAndView model){
        logger.error(errorStr, e);
        model.addObject("error", errorStr);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView getSubjectSave(HttpServletRequest req, @RequestParam(value = "operation", required = false) String operation,
                                       @RequestParam(value = "pk", required = false) String pk,
                                       @RequestParam(value = "subjectid", required = false) String subjectid) {
        ModelAndView model = new ModelAndView();
        PracticalAssignmentsBL practicalBL = null;
        try {
            practicalBL = new PracticalAssignmentsBL();
        } catch (DataException e) {
            ErrorProcessing("Ошибка при инициализации практического задания", e, model);
            model.setViewName("error");
            return model;
        }

        Integer idSubject = practicalBL.getIdSubject(subjectid);
        if(idSubject != null)
            model.addObject("subjectid", idSubject);

        if(operation != null && operation.equals("save")) {
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            String id = req.getParameter("id");
            Subject subject = null;
            try {
                subject = (new SubjectBL()).getByPK(idSubject);
            }
            catch (DataException e){
                ErrorProcessing("Не найдена тема с данным ключом", e, model);
                model.setViewName("error");
                return model;
            }

            PracticalAssignments practical;
            if (id == null || id.isEmpty()) {
                practical = new PracticalAssignments(name, description, subject);
                try {
                    practicalBL.create(practical);
                }
                catch (DataException e){
                    ErrorProcessing("Ошибка при создании практического задания", e, model);
                    model.setViewName("error");
                    return model;
                }

            } else {
                practical = new PracticalAssignments(name, description, subject, Integer.parseInt(id));
                try {
                    practicalBL.update(practical);
                }
                catch (DataException e){
                    ErrorProcessing("Ошибка при обновлении практического задания", e, model);
                    model.setViewName("error");
                    return model;
                }
            }
        }

        model.setViewName("redirect:subject");
        return model;
    }
}
