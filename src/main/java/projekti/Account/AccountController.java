package projekti.Account;

import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projekti.Skill.Skill;
import projekti.Skill.SkillRepository;

@Controller
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountService accountService;

    @GetMapping("*")
    public String afterLogin(Model model) {

        String currentUserUsername = accountService.getCurrentUserUsername();
        Account currentUserAccount = accountRepository.findByUsername(currentUserUsername);
        model.addAttribute("currentUserAccount", currentUserAccount);

        //getUser(model, currentUserAccount.getProfileName());
        return "redirect:wall/" + currentUserAccount.getProfileName();
    }

    @GetMapping("login")
    public String login(Model model) {

        return "login";
    }

    @GetMapping("signup")
    public String signup(@ModelAttribute Account account) {

        return "signup";
    }

    @PostMapping("signup")
    public String add(@Valid @ModelAttribute Account account, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        if (accountRepository.findByUsername(account.getUsername()) != null
                && accountRepository.findByProfileName(account.getProfileName()) != null) {
            String error1 = "User name " + account.getUsername() + " is already taken.";
            redirectAttributes.addFlashAttribute("errorMessageUsername", error1);

            String error2 = "Profile name " + account.getProfileName() + " is already taken.";
            redirectAttributes.addFlashAttribute("errorMessageProfileName", error2);
        }

        if (accountRepository.findByUsername(account.getUsername()) != null) {
            String error = "User name " + account.getUsername() + " is already taken.";
            redirectAttributes.addFlashAttribute("errorMessageUsername", error);

            return "redirect:/signup";
        }

        if (accountRepository.findByProfileName(account.getProfileName()) != null) {
            String error = "Profile name " + account.getProfileName() + " is already taken.";
            redirectAttributes.addFlashAttribute("errorMessageProfileName", error);

            return "redirect:/signup";
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);

        return "login";
    }

    @GetMapping("main")
    public String main(Model model) {

        return "main";
    }

    @GetMapping("wall")
    public String wall(Model model, @ModelAttribute Account account) {

        String currentUserUsername = accountService.getCurrentUserUsername();
        Account currentUserAccount = accountRepository.findByUsername(currentUserUsername);
        return "redirect:" + currentUserAccount.getProfileName();
    }

    @GetMapping("wall/{user}")
    public String getUser(Model model, @PathVariable String user) {

        //Account a = accountRepository.findByUsername(user);
        Account a = accountRepository.findByProfileName(user);
        List<Skill> skills = a.getSkills();
        accountService.sortAndAddSkillsToModel(skills, model);
        boolean userSameAsBrowsed = accountService.userSameAsBrowsed(a);
        model.addAttribute("account", accountRepository.findByProfileName(user));
        model.addAttribute("userSameAsBrowsed", userSameAsBrowsed);

        //--------
        String currentUserUsername = accountService.getCurrentUserUsername();
        Account currentUserAccount = accountRepository.findByUsername(currentUserUsername);

        //List<Skill> commendedSkills = a.getCommendedSkills();
        model.addAttribute("currentUserAccount", currentUserAccount);
        //model.addAttribute("commendedSkills", commendedSkills);
        //--------

        return "wall";
    }

    @GetMapping(path = "wall/{user}/picture", produces = "image/png")
    @ResponseBody
    public byte[] getPicture(@PathVariable String user) {
        Account a = accountRepository.findByProfileName(user);

        return a.getProfilePicture();

    }

    @PostMapping("wall/{user}/remove_picture")
    public String removePicture(@PathVariable String user) {
        Account a = accountRepository.findByProfileName(user);
        a.setProfilePicture(null);

        accountRepository.save(a);

        return "redirect:/wall/" + user;
    }

    @PostMapping("wall/{user}/add_picture")
    public String addPicture(@RequestParam MultipartFile file, @PathVariable String user) throws IOException {
        Account a = accountRepository.findByProfileName(user);

        if (file.getBytes().length > 0) {
            a.setProfilePicture(file.getBytes());
            accountRepository.save(a);
        }
        return "redirect:/wall/" + user;
    }

    @GetMapping("wall/{user}/add_picture")
    public String Picture(@PathVariable String user, Model model) {

        model.addAttribute("account", this.accountRepository.findByProfileName(user));

        return "add_picture";
    }

}
