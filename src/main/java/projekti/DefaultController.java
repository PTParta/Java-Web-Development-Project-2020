package projekti;

import javax.annotation.security.RolesAllowed;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("*")
    public String helloWorld(Model model) {
        //model.addAttribute("message", "World!");
        return "/main";
    }

    
    @GetMapping("/login")
    public String login(Model model) {
        
        return "/login";
    }
}
