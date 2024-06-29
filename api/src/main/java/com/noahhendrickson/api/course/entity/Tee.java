package com.noahhendrickson.api.course.entity;

import com.noahhendrickson.api.common.entity.DeletableEntity;
import com.noahhendrickson.api.round.entity.Round;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tees")
public class Tee extends DeletableEntity {

    @OneToMany(mappedBy = "tee")
    private final List<HoleInfo> holeInfos = new ArrayList<>();
    @OneToMany(mappedBy = "tee")
    private final List<Round> rounds = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    private Course course;
    @Column(name = "name", length = 15, nullable = false)
    private String name;
    @Column(name = "course_rating", nullable = false)
    private double courseRating;
    @Column(name = "slope_rating", nullable = false)
    private int slopeRating;

    protected Tee() {
    }

    public Tee(Course course, String name, double courseRating, int slopeRating) {
        this.course = course;
        this.name = name;
        this.courseRating = courseRating;
        this.slopeRating = slopeRating;
    }

    @Override
    public List<? extends DeletableEntity> getRelatedEntitiesToDelete() {
        List<DeletableEntity> deletableEntities = new ArrayList<>();

        deletableEntities.addAll(holeInfos);
        deletableEntities.addAll(rounds);

        return deletableEntities;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCourseRating() {
        return courseRating;
    }

    public void setCourseRating(double courseRating) {
        this.courseRating = courseRating;
    }

    public int getSlopeRating() {
        return slopeRating;
    }

    public void setSlopeRating(int slopeRating) {
        this.slopeRating = slopeRating;
    }

    public List<HoleInfo> getHoleInfos() {
        return holeInfos;
    }

    public List<Round> getRounds() {
        return rounds;
    }
}
