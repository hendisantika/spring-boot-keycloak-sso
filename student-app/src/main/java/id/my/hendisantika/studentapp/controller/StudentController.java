package id.my.hendisantika.studentapp.controller;

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
 * Time: 11.44
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class StudentController {
    @GetMapping("/contact-us")
    public ModelAndView contactUs() {
        return new ModelAndView("contact-us");
    }

    @GetMapping("/home")
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    @GetMapping("/manage-students")
    @PreAuthorize("hasAuthority('PROFESSOR')")
    // For multiple roles you have options:
    // @PreAuthorize("hasAuthority('PROFESSOR') or hasAuthority('STUDENT')")
    // @PreAuthorize("hasAuthority('PROFESSOR') and hasAuthority('STUDENT')")
    public ModelAndView manageStudents() {
        return new ModelAndView("manage-students");
    }

    @GetMapping("/access-denied")
    public ModelAndView accessDenied() {
        return new ModelAndView("access-denied");
    }
}
