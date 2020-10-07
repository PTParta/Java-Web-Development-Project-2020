
package projekti.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.Post.Post;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    Comment findByPost(Post post);
}
