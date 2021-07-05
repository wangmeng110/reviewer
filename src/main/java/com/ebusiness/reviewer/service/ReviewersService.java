package com.ebusiness.reviewer.service;

import com.ebusiness.reviewer.mapper.ReviewersMapper;
import com.ebusiness.reviewer.model.Reviewers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReviewersService {
    @Autowired
    ReviewersMapper reviewersMapper;

    public Reviewers reviewerLogin(String phone, String password) {
        return reviewersMapper.reviewerLogin(phone,password);
    }

    public List<Reviewers> getReviewersList() {
        return reviewersMapper.getReviewersList();
    }

    public int changeInEnable(int id) {
        final Reviewers userInfo = reviewersMapper.getUserInfo(id);
        if (Objects.isNull(userInfo)){
            return 0;
        }
        return reviewersMapper.changeInEnable(id,userInfo.getIsenable());
    }

    public int updateGroup(int groupId, int reviewerId) {
        return reviewersMapper.updateGroup(groupId,reviewerId);
    }

    public Reviewers getUserInfo(Integer id) {
        return reviewersMapper.getUserInfo(id);
    }

    public int changeMassage(Reviewers reviewers) {
        return reviewersMapper.changeMassage(reviewers);
    }

    public int addReviewer(Reviewers reviewers) {
        return reviewersMapper.addReviewer(reviewers);
    }
}
