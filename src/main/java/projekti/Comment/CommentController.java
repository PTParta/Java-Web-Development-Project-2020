package projekti.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("feed/commentPost/{postAccountProfileName}/{postTimeString}")
    public String comment(@PathVariable String postAccountProfileName,
            @PathVariable String postTimeString, String comment) {

        if (comment.isEmpty()) {
            return "redirect:/feed";
        }
        commentService.comment(postAccountProfileName, postTimeString, comment);

        return "redirect:/feed";
    }

}
