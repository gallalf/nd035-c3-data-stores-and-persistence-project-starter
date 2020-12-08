package com.udacity.jdnd.course3.critter.user.repository;

import com.udacity.jdnd.course3.critter.user.model.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayOfWeekRepository extends JpaRepository<DayOfWeek, Long> {

    @Query("select d from com.udacity.jdnd.course3.critter.user.model.DayOfWeek d where d.dayOfWeek in :dayOfWeek")
    List<DayOfWeek> findDaysOfWeekByName(List<String> dayOfWeek);
}
