package projekti.Post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.Account.Account;
import projekti.Account.AccountRepository;
import projekti.Account.AccountService;
import projekti.Skill.Skill;
import projekti.Skill.SkillRepository;

@Controller
public class PostController {
    
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    AccountService accountService;
    
    @Autowired
    PostRepository postRepository;
    
    @Autowired
    PostService postService;
    
    @GetMapping("feed")
    public String feed(Model model, @ModelAttribute Account account) {
        
        postService.sortAndShowMax25Posts(model, account);
        
        return "feed";
    }
    
    @PostMapping("feed/post")
    public String post(@RequestParam String message) {
        
        postService.post(message);
        
        return "redirect:/feed";
    }
    
    @PostMapping("feed/like/{postAccountProfileName}/{postTimeString}")
    public String likePost(@PathVariable String postAccountProfileName, @PathVariable String postTimeString) {
        
        postService.likePost(postAccountProfileName, postTimeString);
        
        return "redirect:/feed";
    }
}
