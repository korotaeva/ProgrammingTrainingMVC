package ru.innopolis.course3.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 * Created by korot on 04.01.2017.
 */
@Controller
public class SubjectController {
    public String getStudentList(Model model){
        model.addAttribute("message","WORLD!");
        return "subject";
    }
}
