package projekti.Post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import projekti.Account.Account;
import projekti.Account.AccountRepository;
import projekti.Account.AccountService;
import projekti.Connection.ConnectionRepository;
import projekti.Connection.ConnectionService;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    ConnectionService connectionSerivce;

    public List<Post> postsFromConnectionsAndSelf() {

        List<Post> postsFromConnections = new ArrayList<>();

        String currentUserUsername = accountService.getCurrentUserUsername();
        Account currentUserAccount = accountRepository.findByUsername(currentUserUsername);

        List<Account> connectedAccounts = connectionSerivce.getListOfConnectedAccounts(currentUserAccount);

        //Loop through all the connected accounts
        for (Account a : connectedAccounts) {
            List<Post> p = postRepository.findByPostAccount(a);
            postsFromConnections.addAll(p);
        }

        //Find connections from self and add to list
        postsFromConnections.addAll(postRepository.findByPostAccount(currentUserAccount));
        return postsFromConnections;
    }

    /**
     * Sorts posts on date, newest first. Shows 25 posts max.
     *
     * @param model
     * @param account
     */
    public void sortAndShowMax25Posts(Model model, @ModelAttribute Account account) {

        String currentUserUsername = accountService.getCurrentUserUsername();
        Account currentUserAccount = accountRepository.findByUsername(currentUserUsername);

        List<Post> posts = postsFromConnectionsAndSelf();

        //Sort posts on date and time. Newest first.
        Comparator<Post> compareByDateTime = (Post p1, Post p2) -> p1.getPostTime().compareTo(p2.getPostTime());
        Collections.sort(posts, compareByDateTime.reversed());

        //Show max 25 newest posts
        if (posts.size() <= 25) {
            model.addAttribute("posts", posts);
        } else {
            List<Post> postsShortened = new ArrayList<>();
            for (int i = 0; i < 25; i++) {
                postsShortened.add(posts.get(i));
            }
            model.addAttribute("posts", postsShortened);
        }
        model.addAttribute("currentUserAccount", currentUserAccount);
        
        
    }

    public void post(String message) {
        String currentUserUsername = accountService.getCurrentUserUsername();
        Account currentUserAccount = accountRepository.findByUsername(currentUserUsername);

        Post p = new Post();

        p.setPostAccount(currentUserAccount);
        p.setPostMessage(message);

        LocalDateTime currentDateTime = LocalDateTime.now();
        p.setPostTime(currentDateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedCurrentDateTime = currentDateTime.format(formatter);
        p.setPostTimeString(formattedCurrentDateTime);

        p.setPostLikesAmount(0);

        postRepository.save(p);
    }

    public void likePost(String postAccountProfileName, String postTimeString) {

        String currentUserUsername = accountService.getCurrentUserUsername();
        Account currentUserAccount = accountRepository.findByUsername(currentUserUsername);

        Account a = accountRepository.findByProfileName(postAccountProfileName);
        Post p = postRepository.findByPostAccountAndPostTimeString(a, postTimeString);
        p.setPostLikesAmount(p.getPostLikesAmount() + 1);
        p.getPostAccountsThatLiked().add(currentUserAccount);
        postRepository.save(p);
    }
}
