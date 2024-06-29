package com.noahhendrickson.api.round.entity;

import com.noahhendrickson.api.common.entity.BaseEntity;
import com.noahhendrickson.api.course.entity.Course;
import com.noahhendrickson.api.course.entity.Tee;
import com.noahhendrickson.api.user.entity.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "cumulative_score")
public class CumulativeScore extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tee_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    private Tee tee;

    @Column(name = "date", nullable = false, updatable = false, insertable = false)
    private LocalDate date;

    @Column(name = "front9_score")
    private int front9Score;

    @Column(name = "front9_score_adjusted")
    private int front9ScoreAdjusted;

    @Column(name = "back9_score")
    private int back9Score;

    @Column(name = "back9_score_adjusted")
    private int back9ScoreAdjusted;

    @Column(name = "total_score")
    private int totalScore;

    @Column(name = "total_score_adjusted")
    private int totalScoreAdjusted;

    protected CumulativeScore() {
    }

    public User getUser() {
        return user;
    }

    public Course getCourse() {
        return course;
    }

    public Tee getTee() {
        return tee;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getFront9Score() {
        return front9Score;
    }

    public int getFront9ScoreAdjusted() {
        return front9ScoreAdjusted;
    }

    public int getBack9Score() {
        return back9Score;
    }

    public int getBack9ScoreAdjusted() {
        return back9ScoreAdjusted;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getTotalScoreAdjusted() {
        return totalScoreAdjusted;
    }
}
