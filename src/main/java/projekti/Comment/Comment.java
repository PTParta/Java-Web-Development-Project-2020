package projekti.Comment;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.Account.Account;
import projekti.Post.Post;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends AbstractPersistable<Long> {

    private LocalDateTime commentTime;

    private String commentTimeString;

    @NotEmpty
    private String commentMessage;

    @ManyToOne
    private Account commentAccount;
    
    @ManyToOne
    private Post post;
}
