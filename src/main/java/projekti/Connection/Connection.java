package projekti.Connection;

import projekti.Account.Account;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Connection extends AbstractPersistable<Long> {

    @ManyToOne
    private Account requestSender;
    
    @ManyToOne
    private Account requestReceiver;

    //Status can be "pending", "connected", "rejected", or "removed"=connection deleted.
    private String status;

    /*public List<Account> getAllAccounts(){
        
    }*/
}
