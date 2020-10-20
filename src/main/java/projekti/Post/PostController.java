package projekti.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.Comment.CommentService;

@Controller
public class PostController {

    @Autowired
    PostService postService;
    
    @Autowired
    CommentService commentService;

    @GetMapping("feed")
    public String feed(Model model) {

        postService.sortAndShowMax25Posts(model);
        commentService.addCommentServiceToModel(model);
        
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
