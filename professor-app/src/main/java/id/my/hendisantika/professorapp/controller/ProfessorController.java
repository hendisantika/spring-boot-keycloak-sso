package id.my.hendisantika.professorapp.controller;

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
}
