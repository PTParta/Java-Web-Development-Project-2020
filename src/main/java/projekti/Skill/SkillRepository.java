package projekti.Skill;

import projekti.Account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Skill findBySkillName(String skillName);
    Skill findByAccountAndSkillName(Account account, String skillName);
}
