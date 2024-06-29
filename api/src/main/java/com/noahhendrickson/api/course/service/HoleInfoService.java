package com.noahhendrickson.api.course.service;

import com.noahhendrickson.api.common.exception.HoleInfoNotFoundException;
import com.noahhendrickson.api.course.entity.Course;
import com.noahhendrickson.api.course.entity.Hole;
import com.noahhendrickson.api.course.entity.HoleInfo;
import com.noahhendrickson.api.course.entity.Tee;
import com.noahhendrickson.api.course.repository.HoleInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoleInfoService {

    private final HoleInfoRepository holeInfoRepository;

    @Autowired
    public HoleInfoService(HoleInfoRepository holeInfoRepository) {
        this.holeInfoRepository = holeInfoRepository;
    }

    public List<HoleInfo> getAllHoleInfosByCourse(Course course) {
        return holeInfoRepository.findAllByCourseIdAndDeletedIsFalse(course.getId());
    }

    public HoleInfo getHoleInfoByTeeAndHole(Tee tee, Hole hole) {
        return holeInfoRepository.findByTeeAndHoleAndDeletedIsFalse(tee, hole)
                .orElseThrow(() -> new HoleInfoNotFoundException(tee.getId(), hole.getId()));
    }
}
