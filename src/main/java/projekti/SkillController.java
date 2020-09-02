package projekti;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SkillController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    SkillService skillService;

    @PostMapping("wall/{user}/skill")
    public String addSkill(@PathVariable String user, @RequestParam String skillName, RedirectAttributes redirectAttributes) {

        if (skillName.isEmpty()) {
            return "redirect:/wall/" + user;
        }
        Account a = accountRepository.findByUsername(user);

        if (skillService.skillExists(a, skillName, redirectAttributes)) {
            return "redirect:/wall/" + user;
        }
        skillService.addSkill(a, skillName);
        return "redirect:/wall/" + user;
    }

    @PostMapping("wall/{user}/commend/{skill}")
    public String commend(@PathVariable String user, @PathVariable String skill) {

        skillService.commend(user, skill);
        return "redirect:/wall/" + user;
    }
}
