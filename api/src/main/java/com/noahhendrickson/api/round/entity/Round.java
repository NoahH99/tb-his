package com.noahhendrickson.api.round.entity;

import com.noahhendrickson.api.common.entity.DeletableEntity;
import com.noahhendrickson.api.course.entity.Course;
import com.noahhendrickson.api.course.entity.Tee;
import com.noahhendrickson.api.user.entity.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rounds")
public class Round extends DeletableEntity {

    @OneToMany(mappedBy = "round")
    private final List<Score> scores = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    private Course course;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tee_id", referencedColumnName = "id", nullable = false)
    private Tee tee;
    @Column(name = "date", nullable = false)
    private LocalDate date;

    protected Round() {
    }

    public Round(User user, Course course, Tee tee, LocalDate date) {
        this.user = user;
        this.course = course;
        this.tee = tee;
        this.date = date;
    }

    @Override
    public List<? extends DeletableEntity> getRelatedEntitiesToDelete() {
        return scores;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Tee getTee() {
        return tee;
    }

    public void setTee(Tee tee) {
        this.tee = tee;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Score> getScores() {
        return scores;
    }
}
