package com.ebusiness.reviewer.service;

import com.ebusiness.reviewer.enums.CompetitionType;
import com.ebusiness.reviewer.enums.GradeType;
import com.ebusiness.reviewer.mapper.*;
import com.ebusiness.reviewer.model.*;
import com.ebusiness.reviewer.pojo.vo.CompetitionResultVO;
import com.ebusiness.reviewer.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CompetitionService {
    @Autowired
    CompetitionMapper competitionMapper;
    @Autowired
    ScoringMapper scoringMapper;

    @Autowired
    ImageMapper imageMapper;

    @Autowired
    WorksMapper worksMapper;

    @Autowired
    private ComConditionMapper comConditionMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Transactional(rollbackFor = Exception.class)
    public RespBean createCompetition(Competition competition) {
        competition.setCreateTime(new Date());
        Long time = new Date().getTime() + 60 * 1000 * 60 * 24;
        competition.setCheckTime(new Date(time));
        competition.setIsenable(true);
        competitionMapper.createCompetition(competition);
        ComCondition comCondition = competition.getComCondition();

        comCondition.setCompetitionId(competition.getId());

        comCondition.setCreateTime(new Date());
        comConditionMapper.insertComCondition(comCondition);
        return RespBean.ok("创建比赛成功");
    }

    public List<Competition> getCompetitionList() {
        return competitionMapper.getCompetitionList();
    }


    public List<Scoring> getScoringRecords() {

        return scoringMapper.getScoringRecords();
    }

    @Transactional
    public int closeCompetitionById(Integer competitionId) {
        //删除比赛相关的打分记录
        scoringMapper.deleteWorksByCompetitionId(competitionId);

        //删除比赛表记录
        competitionMapper.closeCompetitionById(competitionId);

        //删除比赛图片
        imageMapper.deleteImageByComId();

        //删除比赛的作品
        worksMapper.deleteWorksByComId();
        /**
         * 删除公告表
         */
        return 1;
    }

    public int checkScoring(Integer reviewerId, Integer workId, Integer competitionId) {
        return competitionMapper.checkScoring(reviewerId, workId, competitionId);
    }

    public RespBean getComCondition(Integer id) {

        ComCondition comCondition = comConditionMapper.getComCondition(id);
        if (Objects.nonNull(comCondition)) {
            return RespBean.ok(comCondition);
        }
        return RespBean.error("获取失败");
    }

    /**
     * 获取比赛结果，一等奖按照参赛人数百分比指定
     *
     * @param id
     * @return
     */
    public RespBean getCompetitionResultList(Integer id) {
        // 如果该比赛没有结束返回暂无数据
        Competition competition = competitionMapper.getCompetitionById(id);
        if (Objects.isNull(competition)) {
            return RespBean.error("暂无数据");
        }
        // 检查比赛结束时间和当前时间
        if (new Date().getTime() > competition.getEndTime().getTime()) {
            // 修改比赛状态为已经结束
            competitionMapper.endCompetitionById(competition.getId());
        }
        if (!CompetitionType.YI_JIE_SHU.getName().equals(competition.getProcess())) {
            return RespBean.error("比赛暂未结束,无法查看比赛结果");
        }
        // 比赛已经结束，查找该比赛参赛人数
        List<Works> worksList = worksMapper.getAllWorksByCompetitionId(id);
        if (worksList.isEmpty()) {
            return RespBean.error("无人参加本次比赛");
        }
        // 查看该比赛一等奖百分比
        ComCondition comCondition = comConditionMapper.getComCondition(id);
        // 查看参加该比赛的总作品数
        List<Works> works = worksMapper.getAllWorksByCompetitionId(id);
        List<CompetitionResultVO> list = new ArrayList<>(10);
        if (Objects.nonNull(comCondition)) {
            Integer firstPrizePer = comCondition.getFirstPrizePer();
            double firstPrizeNum = (firstPrizePer*0.01) * works.size();
            // 查看作品的list
            list = new ArrayList<>();
            for (int i = 0; i < worksList.size(); i++) {
                CompetitionResultVO competitionResultVO = new CompetitionResultVO();
                competitionResultVO.setWorksName(worksList.get(i).getWorksName());
                competitionResultVO.setAverageScore(worksList.get(i).getAverageScore());
                if (worksList.get(i).getAverageScore() >= 90) {
                    competitionResultVO.setGradeName(GradeType.ONE.getName());
                } else if (worksList.get(i).getAverageScore() < 90 && worksList.get(i).getAverageScore() >= 80) {
                    competitionResultVO.setGradeName(GradeType.TWO.getName());
                } else if (worksList.get(i).getAverageScore() < 80 && worksList.get(i).getAverageScore() >= 70) {
                    competitionResultVO.setGradeName(GradeType.THREE.getName());
                } else {
                    competitionResultVO.setGradeName(GradeType.FOUR.getName());
                }
                if (i >= firstPrizeNum && worksList.get(i).getAverageScore() >= 90) {
                    competitionResultVO.setGradeName(GradeType.TWO.getName());
                }
                Users userInfo = usersMapper.getUserInfo(worksList.get(i).getUserId());
                competitionResultVO.setUsername(userInfo.getUsername());
                competitionResultVO.setPhone(userInfo.getPhone());
                // 获取
                list.add(competitionResultVO);
            }
        }
        return RespBean.ok("获取成功", list);
    }
}
