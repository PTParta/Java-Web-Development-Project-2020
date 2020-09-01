package projekti;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill extends AbstractPersistable<Long>{
    
    @NotEmpty
    private String skillName;
    
    private Integer commendAmount;
    
    @ManyToOne
    private Account account;
    
    @ManyToMany(mappedBy="commendedSkills")
    private List<Account> accountsThatCommended = new ArrayList<>();
    

    
}
