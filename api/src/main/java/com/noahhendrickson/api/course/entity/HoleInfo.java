package com.noahhendrickson.api.course.entity;

import com.noahhendrickson.api.common.entity.DeletableEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "holes_info")
public class HoleInfo extends DeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tee_id", referencedColumnName = "id", nullable = false)
    private Tee tee;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hole_id", referencedColumnName = "id", nullable = false)
    private Hole hole;

    @Column(name = "yardage", nullable = false)
    private int yardage;

    @Column(name = "par", nullable = false)
    private int par;

    @Column(name = "handicap", nullable = false)
    private int handicap;

    protected HoleInfo() {
    }

    public HoleInfo(Tee tee, Hole hole, int yardage, int par, int handicap) {
        this.tee = tee;
        this.hole = hole;
        this.yardage = yardage;
        this.par = par;
        this.handicap = handicap;
    }

    public Tee getTee() {
        return tee;
    }

    public void setTee(Tee tee) {
        this.tee = tee;
    }

    public Hole getHole() {
        return hole;
    }

    public void setHole(Hole hole) {
        this.hole = hole;
    }

    public int getYardage() {
        return yardage;
    }

    public void setYardage(int yardage) {
        this.yardage = yardage;
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public int getHandicap() {
        return handicap;
    }

    public void setHandicap(int handicap) {
        this.handicap = handicap;
    }
}
