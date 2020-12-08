package com.udacity.jdnd.course3.critter.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity(name = "day_of_week")
public class DayOfWeek {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "day_of_week")
    private String dayOfWeek;

    public DayOfWeek() {
    }

    public DayOfWeek(Long id, String dayOfWeek) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayOfWeek dayOfWeek = (DayOfWeek) o;
        return Objects.equals(id, dayOfWeek.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
