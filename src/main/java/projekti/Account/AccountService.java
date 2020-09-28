package projekti.Account;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import projekti.Skill.Skill;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    /**
     * Get the current user´s username who is browsing the website
     *
     * @return
     */
    public String getCurrentUserUsername() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return currentPrincipalName;
    }

    /**
     * Check if the current user (who has logged in) is the same as the person
     * whose wall they are browsing. This info is used by Thymeleaf to determine
     * what is shown on the HTML page to the user.
     *
     * @param Account
     * @return boolean
     */
    public boolean userSameAsBrowsed(Account a) {

        String currentUser = getCurrentUserUsername();

        /*
        //Get the current user who is browsing the website
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
         */
        //Check if the user is the same as the person whose wall he is looking at
        boolean userSameAsBrowsed;

        if (currentUser.equals(a.getUsername())) {
            userSameAsBrowsed = true;
        } else {
            userSameAsBrowsed = false;
        }

        return userSameAsBrowsed;
    }

    /**
     * Sort a list of skills based on amount of commendations in descending
     * order. Add the sorted list to the model.
     *
     * @param skills
     * @param model
     */
    public void sortAndAddSkillsToModel(List<Skill> skills, Model model) {

        //Sort skills on commend amount in descending order
        Comparator<Skill> compareByCommendAmount = (Skill s1, Skill s2) -> s1.getCommendAmount().compareTo(s2.getCommendAmount());
        Collections.sort(skills, compareByCommendAmount.reversed());

        //Top 3 skills are highlighted only if there are more than three skills
        if (skills.size() < 4) {
            boolean moreThanThreeSkills = false;
            model.addAttribute("moreThanThreeSkills", moreThanThreeSkills);
            model.addAttribute("skills", skills);
        } else {
            boolean moreThanThreeSkills = true;
            List<Skill> skillsTopThree = skills.subList(0, 3);
            List<Skill> skillsNotTopThree = skills.subList(3, skills.size());
            model.addAttribute("moreThanThreeSkills", moreThanThreeSkills);
            model.addAttribute("skillsTopThree", skillsTopThree);
            model.addAttribute("skillsNotTopThree", skillsNotTopThree);
        }
    }

    /**
     * During sign up checks if the User name and Profile name are available.
     * Returns true if they are. Return false if they aren´t and redirects to
     * the sign up page with error messages.
     *
     * @param account
     * @return boolean
     */
    /*public boolean checkAccount(Account account, RedirectAttributes redirectAttributes) {
        if (accountRepository.findByUsername(account.getUsername()) != null
                && accountRepository.findByProfileName(account.getProfileName()) != null) {
            String error1 = "User name " + account.getUsername() + " is already taken.";
            redirectAttributes.addFlashAttribute("errorMessageUsername", error1);

            String error2 = "Profile name " + account.getProfileName() + " is already taken.";
            redirectAttributes.addFlashAttribute("errorMessageProfileName", error2);
        }

        if (accountRepository.findByUsername(account.getUsername()) != null) {
            String error = "User name " + account.getUsername() + " is already taken.";
            redirectAttributes.addFlashAttribute("errorMessageUsername", error);

            return "redirect:/signup";
        }

        if (accountRepository.findByProfileName(account.getProfileName()) != null) {
            String error = "Profile name " + account.getProfileName() + " is already taken.";
            redirectAttributes.addFlashAttribute("errorMessageProfileName", error);

            return "redirect:/signup";
        }
    }*/
}
