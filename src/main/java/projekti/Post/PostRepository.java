package projekti.Post;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.Account.Account;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findByPostAccountAndPostMessage(Account postAccount, String postMessage);
    Post findByPostAccountAndPostTimeString(Account postAccount, String postTimeString);
    //Post findByPostAccount(Account account);
    List<Post> findByPostAccount(Account postAccount);
}
