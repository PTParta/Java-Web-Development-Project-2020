package projekti.Account;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.Comment.Comment;
import projekti.Post.Post;
import projekti.Skill.Skill;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"username", "profileName"})})
public class Account extends AbstractPersistable<Long> {

    @Size(min = 3, max = 20)
    private String username;

    @Size(min = 3, max = 20)
    private String profileName;

    @Size(min = 3, max = 20)
    private String name;

    @Size(min = 6, max = 60)
    private String password;

    @OneToMany
    private List<Skill> skills = new ArrayList<>();

    //@Lob used for local testing. Doesn´t work in Heroku
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] profilePicture;

    @ManyToMany
    private List<Skill> commendedSkills = new ArrayList<>();

    @OneToMany
    private List<Post> posts;

    @ManyToMany(mappedBy="postAccountsThatLiked")
    private List<Post> likedPosts = new ArrayList<>();
    
    @OneToMany
    private List<Comment> comments;

}
