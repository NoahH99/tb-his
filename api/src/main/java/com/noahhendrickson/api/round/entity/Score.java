package com.noahhendrickson.api.round.entity;

import com.noahhendrickson.api.common.entity.DeletableEntity;
import com.noahhendrickson.api.course.entity.Hole;
import jakarta.persistence.*;

@Entity
@Table(name = "scores")
public class Score extends DeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "round_id", referencedColumnName = "id", nullable = false)
    private Round round;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hole_id", referencedColumnName = "id", nullable = false)
    private Hole hole;

    @Column(name = "score", nullable = false)
    private int score;

    @Column(name = "adjusted_score", nullable = false)
    private int adjustedScore;

    protected Score() {
    }

    public Score(Round round, Hole hole, int score, int adjustedScore) {
        this.round = round;
        this.hole = hole;
        this.score = score;
        this.adjustedScore = adjustedScore;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public Hole getHole() {
        return hole;
    }

    public void setHole(Hole hole) {
        this.hole = hole;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAdjustedScore() {
        return adjustedScore;
    }

    public void setAdjustedScore(int adjustedScore) {
        this.adjustedScore = adjustedScore;
    }
}
