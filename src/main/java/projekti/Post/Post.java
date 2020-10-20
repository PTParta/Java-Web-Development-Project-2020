package projekti.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import projekti.Account.Account;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.Comment.Comment;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post extends AbstractPersistable<Long> {

    private LocalDateTime postTime;

    private String postTimeString;

    @NotEmpty
    private String postMessage;

    private int postLikesAmount;

    @ManyToOne
    private Account postAccount;

    @ManyToMany
    private List<Account> postAccountsThatLiked = new ArrayList<>();

    @OneToMany
    private List<Comment> postComments = new ArrayList<>();
}
