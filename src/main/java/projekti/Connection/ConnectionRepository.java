package projekti.Connection;

import projekti.Account.Account;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    Connection findByRequestSenderAndRequestReceiver(Account sender, Account receiver);
    List<Connection> findByRequestSender(Account sender);
    List<Connection> findByRequestReceiver(Account receiver);
    List<Connection> findByStatus(String status);
    
    //3108 ilta
    List<Connection> findByRequestSenderAndStatus(Account sender, String status);
    List<Connection> findByRequestReceiverAndStatus(Account receiver, String status);
    
}
