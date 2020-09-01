package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private AccountRepository accountRepository;

    
}
