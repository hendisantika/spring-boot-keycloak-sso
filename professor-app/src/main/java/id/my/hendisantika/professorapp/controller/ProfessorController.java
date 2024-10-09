package id.my.hendisantika.professorapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-keycloak-sso
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 09/10/24
 * Time: 11.37
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ProfessorController {
    @GetMapping("/contact-us")
    public ModelAndView contactUs() {
        ModelAndView modelAndView = new ModelAndView("contact-us");
        return modelAndView;
    }

    @GetMapping("/home")
    @PreAuthorize("hasAuthority('PROFESSOR')")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home");
        return modelAndView;
    }

    @GetMapping("/manage-students")
    @PreAuthorize("hasAuthority('PROFESSOR')")
    public ModelAndView manageStudents() {
        ModelAndView modelAndView = new ModelAndView("manage-students");
        return modelAndView;
    }

    @GetMapping("/access-denied")
    public ModelAndView accessDenied() {
        ModelAndView modelAndView = new ModelAndView("access-denied");
        return modelAndView;
    }
}
