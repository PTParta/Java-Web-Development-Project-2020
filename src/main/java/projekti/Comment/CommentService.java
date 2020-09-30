package projekti.Comment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import projekti.Account.Account;
import projekti.Account.AccountRepository;
import projekti.Account.AccountService;
import projekti.Post.Post;

@Service
public class CommentService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;
    
    @Autowired
    CommentRepository commentRepository;

    public void comment(@ModelAttribute Post post, String message) {
        String currentUserUsername = accountService.getCurrentUserUsername();
        Account currentUserAccount = accountRepository.findByUsername(currentUserUsername);

        Comment c = new Comment();

        c.setCommentAccount(currentUserAccount);
        c.setCommentMessage(message);
        
        LocalDateTime currentDateTime = LocalDateTime.now();
        c.setCommentTime(currentDateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedCurrentDateTime = currentDateTime.format(formatter);
        c.setCommentTimeString(formattedCurrentDateTime);
        
        commentRepository.save(c);
    }
}
