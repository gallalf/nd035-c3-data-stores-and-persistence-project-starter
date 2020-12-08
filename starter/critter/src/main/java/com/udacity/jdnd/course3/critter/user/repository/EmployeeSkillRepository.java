package com.udacity.jdnd.course3.critter.user.repository;

import com.udacity.jdnd.course3.critter.user.model.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, Long> {

    @Query("select s from com.udacity.jdnd.course3.critter.user.model.EmployeeSkill s where s.skill in :skills")
    List<EmployeeSkill> findSkillsByName(List<String> skills);
}
