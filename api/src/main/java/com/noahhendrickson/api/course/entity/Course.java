package com.noahhendrickson.api.course.entity;

import com.noahhendrickson.api.common.entity.DeletableEntity;
import com.noahhendrickson.api.round.entity.Round;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course extends DeletableEntity {

    @OneToMany(mappedBy = "course")
    private final List<Tee> tees = new ArrayList<>();
    @OneToMany(mappedBy = "course")
    private final List<Hole> holes = new ArrayList<>();
    @OneToMany(mappedBy = "course")
    private final List<Round> rounds = new ArrayList<>();
    @Column(name = "name", length = 75, nullable = false)
    private String name;
    @Column(name = "location", length = 75, nullable = false)
    private String location;

    protected Course() {
    }

    public Course(String name, String location) {
        this.name = name;
        this.location = location;
    }

    @Override
    public List<? extends DeletableEntity> getRelatedEntitiesToDelete() {
        List<DeletableEntity> deletableEntities = new ArrayList<>();

        deletableEntities.addAll(tees);
        deletableEntities.addAll(holes);
        deletableEntities.addAll(rounds);

        return deletableEntities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Tee> getTees() {
        return tees;
    }

    public List<Hole> getHoles() {
        return holes;
    }

    public List<Round> getRounds() {
        return rounds;
    }
}
