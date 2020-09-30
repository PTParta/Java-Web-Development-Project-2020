
package projekti.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import projekti.Post.Post;

@Controller
public class CommentController {
    
    @Autowired
    CommentService commentService;
    
    @PostMapping("feed/commentPost")
    public String comment(@ModelAttribute Post post, String comment){
        
        System.out.println("");
        System.out.println("TEST");
        System.out.println(post.getPostMessage());
        System.out.println("");
        //commentService.comment();
        
        return "redirect:/feed";
    }
    
}
