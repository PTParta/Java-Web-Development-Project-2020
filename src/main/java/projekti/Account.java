package projekti;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    //@Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] profilePicture;

    @ManyToMany
    private List<Skill> commendedSkills;

    /*@OneToMany
    private List<Connection> connections = new ArrayList<>();*/
 /*
    private List<Account> receivedConnectionRequests = new ArrayList<>();
    
    private List<Account> sentConnectionRequests = new ArrayList<>();
    
    private List<Account> connected = new ArrayList<>();
     */
}
