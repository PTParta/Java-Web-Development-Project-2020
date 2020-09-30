package projekti.Connection;

import java.util.ArrayList;
import java.util.List;
import projekti.Account.AccountService;
import projekti.Account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.Account.Account;

@Service
public class ConnectionService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    ConnectionRepository connectionRepository;

    /**
     * Return true if the two account are connected, i.e. status = "connected".
     * If not connected, return false.
     *
     * @param a1
     * @param a2
     * @return
     */
    public boolean accountsConnected(Account a1, Account a2) {

        List<Connection> connections1 = connectionRepository
                .findByRequestReceiverAndStatus(a1, "connected");

        for (Connection c : connections1) {
            if (c.getRequestSender().equals(a2)) {
                return true;
            }
        }
        List<Connection> connections2 = connectionRepository
                .findByRequestSenderAndStatus(a1, "connected");

        for (Connection c : connections2) {
            if (c.getRequestReceiver().equals(a2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return a list of accounts that are connected (status = "connected") to
     * the current user
     *
     * @param a
     * @return
     */
    public List<Account> getListOfConnectedAccounts(Account a) {
        List<Connection> connections1 = connectionRepository
                .findByRequestReceiverAndStatus(a, "connected");
        List<Connection> connections2 = connectionRepository
                .findByRequestSenderAndStatus(a, "connected");
        connections1.addAll(connections2);
        List<Account> connectedAccounts = new ArrayList<>();

        for (Connection c : connections1) {
            if (c.getRequestReceiver().equals(a)) {
                connectedAccounts.add(c.getRequestSender());
            } else {
                connectedAccounts.add(c.getRequestReceiver());
            }
        }
        return connectedAccounts;
    }
}
