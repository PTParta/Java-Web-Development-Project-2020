package projekti;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Skill findBySkillName(String skillName);
    //Skill findByAccount(Account account);
    Skill findByAccountAndSkillName(Account account, String skillName);
}
