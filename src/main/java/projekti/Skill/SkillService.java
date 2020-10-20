package projekti.Skill;

import projekti.Account.Account;
import projekti.Account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class SkillService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    SkillRepository skillRepository;

    /**
     * Add a skill to the database and link an account to that skill
     *
     * @param account
     * @param skillName
     */
    public void addSkill(Account account, String skillName) {

        Skill s = new Skill();
        s.setSkillName(skillName);
        s.setCommendAmount(0);
        s.setAccount(account);
        skillRepository.save(s);
        account.getSkills().add(s);
        accountRepository.save(account);
    }

    /**
     * Check if the skill has already been added. If it has, give an error
     * message.
     *
     * @param account
     * @param skillName
     * @param redirectAttributes
     * @return boolean
     */
    public boolean skillExists(Account account, String skillName, RedirectAttributes redirectAttributes) {

        if (skillRepository.findByAccountAndSkillName(account, skillName) != null) {
            String error = "Skill " + skillName + " already exists.";
            redirectAttributes.addFlashAttribute("errorMessage", error);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Commend a skill and replace "Commend" button with text commended.
     *
     * @param user
     * @param skill
     */
    public void commend(String user, String skill) {
        Account a = accountRepository.findByProfileName(user);
        Skill s = skillRepository.findByAccountAndSkillName(a, skill);
        s.setCommendAmount(s.getCommendAmount() + 1);
        
        //Get the current user who is browsing the website
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        
        Account accountThatGaveCommendation = accountRepository.findByUsername(currentPrincipalName);
        
        s.getAccountsThatCommended().add(accountThatGaveCommendation);
        skillRepository.save(s);
        
        accountThatGaveCommendation.getCommendedSkills().add(s);
        accountRepository.save(accountThatGaveCommendation);
    }
}
