package projekti.Connection;

import projekti.Account.AccountService;
import projekti.Account.Account;
import projekti.Account.AccountRepository;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConnectionController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private AccountService accountService;

    @GetMapping("connections")
    public String connections(Model model) {

        String currentUser = accountService.getCurrentUserUsername();
        Account currentAccount = accountRepository.findByUsername(currentUser);
        List<Account> accounts = accountRepository.findAll();

        /*Remove the current user from the model so that the user wouldn´t
        see himself in the list of people*/
        accounts.remove(accountRepository.findByUsername(currentUser));

        List<Connection> sentConnectionRequests = connectionRepository.findByRequestSenderAndStatus(currentAccount, "pending");
        List<Connection> receivedConnectionRequests = connectionRepository.findByRequestReceiverAndStatus(currentAccount, "pending");
        List<Connection> connectedAccountsSent = connectionRepository.findByRequestSenderAndStatus(currentAccount, "connected");
        List<Connection> connectedAccountsReceived = connectionRepository.findByRequestReceiverAndStatus(currentAccount, "connected");
        List<Connection> rejectedAccountsSent = connectionRepository.findByRequestSenderAndStatus(currentAccount, "rejected");
        List<Connection> rejectedAccountsReceived = connectionRepository.findByRequestReceiverAndStatus(currentAccount, "rejected");
        List<Connection> connectedAccounts = new ArrayList<>();
        List<Connection> rejectedAccounts = new ArrayList<>();
        connectedAccounts.addAll(connectedAccountsSent);
        connectedAccounts.addAll(connectedAccountsReceived);
        rejectedAccounts.addAll(rejectedAccountsSent);
        rejectedAccounts.addAll(rejectedAccountsReceived);

        /*Remove accounts that have a connection object from "accounts" model
        so there wouldn´t be a duplicate listing of an account.*/
        List<Account> accountsToBeRemoved = new ArrayList<>();

        for (Account a : accounts) {
            if (connectionRepository.findByRequestSenderAndRequestReceiver(a, currentAccount) != null) {
                accountsToBeRemoved.add(a);

            }
            if (connectionRepository.findByRequestSenderAndRequestReceiver(currentAccount, a) != null) {
                accountsToBeRemoved.add(a);
            }
        }
        accounts.removeAll(accountsToBeRemoved);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("accounts", accounts);
        model.addAttribute("sentConnectionRequests", sentConnectionRequests);
        model.addAttribute("receivedConnectionRequests", receivedConnectionRequests);
        model.addAttribute("connections", connectedAccounts);
        model.addAttribute("rejectedConnections", rejectedAccounts);

        return "connections";
    }

    @PostMapping("connections/sendConnectRequest/{connectTo}")
    public String sendConnectRequest(@PathVariable String connectTo) {
        String currentUser = accountService.getCurrentUserUsername();
        Account currentUserAccount = accountRepository.findByUsername(currentUser);
        //Account connectToAccount = accountRepository.findByUsername(connectTo);
        Account connectToAccount = accountRepository.findByProfileName(connectTo);

        if (connectionRepository.findByRequestSenderAndRequestReceiver(currentUserAccount, connectToAccount) == null) {
            Connection connection = new Connection();
            connection.setRequestSender(currentUserAccount);
            connection.setRequestReceiver(connectToAccount);
            connection.setStatus("pending");
            connectionRepository.save(connection);
        }

        return "redirect:/connections";
    }

    @PostMapping("connections/acceptConnectRequest/{requestSender}")
    public String acceptConnectRequest(@PathVariable String requestSender) {
        String currentUser = accountService.getCurrentUserUsername();
        Account currentUserAccount = accountRepository.findByUsername(currentUser);
        //Account connectToAccount = accountRepository.findByUsername(requestSender);
        Account connectToAccount = accountRepository.findByProfileName(requestSender);

        Connection connection = connectionRepository.findByRequestSenderAndRequestReceiver(connectToAccount, currentUserAccount);
        connection.setStatus("connected");
        connectionRepository.save(connection);

        return "redirect:/connections";
    }

    @PostMapping("connections/rejectConnectRequest/{requestSender}")
    public String rejectConnectRequest(@PathVariable String requestSender) {
        String currentUser = accountService.getCurrentUserUsername();
        Account currentUserAccount = accountRepository.findByUsername(currentUser);
        //Account connectToAccount = accountRepository.findByUsername(requestSender);
        Account connectToAccount = accountRepository.findByProfileName(requestSender);

        Connection connection = connectionRepository.findByRequestSenderAndRequestReceiver(connectToAccount, currentUserAccount);
        connection.setStatus("rejected");
        connectionRepository.save(connection);

        return "redirect:/connections";
    }

    @PostMapping("/connections/remove/{connectionToBeRemoved}")
    public String removeConnection(@PathVariable String connectionToBeRemoved) {

        String currentUser = accountService.getCurrentUserUsername();
        Account currentUserAccount = accountRepository.findByUsername(currentUser);
        //Account removeConnectionToAccount = accountRepository.findByUsername(connectionToBeRemoved);
        Account removeConnectionToAccount = accountRepository.findByProfileName(connectionToBeRemoved);

        if (connectionRepository.findByRequestSenderAndRequestReceiver(currentUserAccount, removeConnectionToAccount) != null) {
            connectionRepository.delete(connectionRepository.findByRequestSenderAndRequestReceiver(currentUserAccount, removeConnectionToAccount));
        }
        if (connectionRepository.findByRequestSenderAndRequestReceiver(removeConnectionToAccount, currentUserAccount) != null) {
            connectionRepository.delete(connectionRepository.findByRequestSenderAndRequestReceiver(removeConnectionToAccount, currentUserAccount));
        }

        return "redirect:/connections";
    }

    @PostMapping("/connections/find")
    public String findPeople(@RequestParam String name) {
        List<Account> accountsThatContainName = new ArrayList<>();
        List<Account> allAccounts = accountRepository.findAll();

        for (Account a : allAccounts) {
            if (a.getName().contains(name)) {
                accountsThatContainName.add(a);
            }
        }
        return "redirect:/connections";
    }
}
