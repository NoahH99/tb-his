package com.noahhendrickson.api.course.entity;

import com.noahhendrickson.api.common.entity.DeletableEntity;
import com.noahhendrickson.api.round.entity.Score;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "holes")
public class Hole extends DeletableEntity {

    @OneToMany(mappedBy = "hole")
    private final List<HoleInfo> holeInfos = new ArrayList<>();
    @OneToMany(mappedBy = "hole")
    private final List<Score> scores = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    private Course course;
    @Column(name = "hole_number", nullable = false)
    private int holeNumber;

    protected Hole() {
    }

    public Hole(Course course, int holeNumber) {
        this.course = course;
        this.holeNumber = holeNumber;
    }

    @Override
    public List<? extends DeletableEntity> getRelatedEntitiesToDelete() {
        List<DeletableEntity> deletableEntities = new ArrayList<>();

        deletableEntities.addAll(holeInfos);
        deletableEntities.addAll(scores);

        return deletableEntities;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getHoleNumber() {
        return holeNumber;
    }

    public void setHoleNumber(int holeNumber) {
        this.holeNumber = holeNumber;
    }

    public List<HoleInfo> getHoleInfos() {
        return holeInfos;
    }

    public List<Score> getScores() {
        return scores;
    }
}
