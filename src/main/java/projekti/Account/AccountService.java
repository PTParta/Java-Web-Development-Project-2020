package projekti.Account;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import projekti.Skill.Skill;

@Service
public class AccountService {

    /**
     * Get the current user´s username who is browsing the website
     *
     * @return
     */
    public String getCurrentUserUsername() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.out.println("");
        System.out.println(currentPrincipalName);
        System.out.println("");
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
}
