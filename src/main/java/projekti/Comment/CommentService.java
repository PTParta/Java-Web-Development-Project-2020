package projekti.Comment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import projekti.Account.Account;
import projekti.Account.AccountRepository;
import projekti.Account.AccountService;
import projekti.Post.Post;
import projekti.Post.PostRepository;

@Service
public class CommentService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    public void comment(String postAccountProfileName, String postTimeString, String comment) {
        String currentUserUsername = accountService.getCurrentUserUsername();
        Account currentUserAccount = accountRepository.findByUsername(currentUserUsername);
        Account a = accountRepository.findByProfileName(postAccountProfileName);
        Post p = postRepository.findByPostAccountAndPostTimeString(a, postTimeString);

        Comment c = new Comment();
        c.setCommentAccount(currentUserAccount);
        c.setCommentMessage(comment);
        c.setPost(p);
        LocalDateTime currentDateTime = LocalDateTime.now();
        c.setCommentTime(currentDateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedCurrentDateTime = currentDateTime.format(formatter);
        c.setCommentTimeString(formattedCurrentDateTime);

        p.getPostComments().add(c);

        commentRepository.save(c);
        postRepository.save(p);
    }

    /**
     * Gives a comparator on commentTime for Thymeleaf to use
     *
     * @param model
     */
    /*public void sortAndShowMax10Comments(Post post) {
        //model.addAttribute("byDate", Comparator.comparing(Comment::getCommentTime));
        //Sort posts on date and time. Newest first.
        Comparator<Comment> compareByDateTime = (Comment c1, Comment c2) -> c1.getCommentTime().compareTo(c2.getCommentTime());
        //model.addAttribute("byDate", compareByDateTime);
    }*/
    public void addCommentServiceToModel(Model model) {
        CommentService commentService = new CommentService();
        model.addAttribute("commentService", commentService);
    }

    /*public boolean sortAndShow10Comments(@ModelAttribute Post post, Model model){
        
        List<Comment> comments= post.getPostComments();
        model.addAttribute("comments", comments);
        
        return true;
    }*/
    public List<Comment> sortAndGet10Comments(List<Post> posts) {

        Comparator<Comment> compareByDateTimeComments = (Comment c1, Comment c2) -> c1.getCommentTime().compareTo(c2.getCommentTime());
        List<Comment> comments = new ArrayList<>();
        
        for (Post p : posts) {
            List<Comment> commentsHelper = p.getPostComments();
            Collections.sort(commentsHelper, compareByDateTimeComments.reversed());
            if (commentsHelper.size() <= 10) {
                comments.addAll(commentsHelper);
            } else {
                List<Comment> commentsHelper2 = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    commentsHelper2.add(commentsHelper.get(i));
                }
                comments.addAll(commentsHelper2);
            }
        }

        return comments;
    }

}
