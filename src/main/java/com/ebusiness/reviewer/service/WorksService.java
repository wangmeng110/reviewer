package com.ebusiness.reviewer.service;

import com.ebusiness.reviewer.mapper.*;
import com.ebusiness.reviewer.model.*;
import com.ebusiness.reviewer.utils.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class WorksService {
    @Autowired
    WorksMapper worksMapper;

    @Autowired
    ImageMapper imageMapper;

    @Autowired
    ScoringMapper scoringMapper;

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    private ComConditionMapper comConditionMapper;

    public int updateGroup(int groupId, int worksId) {
        return worksMapper.updateGroup(groupId, worksId);
    }

    public RespPageBean getWorksListByPage(Integer page, Integer size, String keyWords) {

        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        //获得已经评审过的作品列表
        List<Works> data = worksMapper.getWorksListByPage(page, size, keyWords);

        /**
         * 去掉最高分和最低分
         */
        //变量初始化
        int totalScore;
        int maxScore;
        int minScore;
        int averageScore;
        int level;
        for (int i = 0; i < data.size(); i++) {

            //取得每个作品的workId
            int workId = data.get(i).getId();
            //根据workId找到打分表中，查看该作品的打分次数，如果打分次数大于3 就去掉最高分和最低分
            int count = scoringMapper.getScoringNumByWordId(workId);
            if (count < 3) {
                //跳出本次循环 ;
                continue;
            }
            if (count >= 3) {
                //找到最高分和最低分
                maxScore = scoringMapper.getMaxScore(workId);
                minScore = scoringMapper.getMinScore(workId);
                totalScore = data.get(i).getTotalScore() - (maxScore + minScore);
                //重新计算平均得分
                //总分除以 打分次数-2
                averageScore = totalScore / (count - 2);
                //重新计算等级 根据当前的平均得分计算等级

                if (averageScore > 90) {
                    level = 5;
                } else if (averageScore >= 80 && averageScore < 90) {
                    level = 4;
                } else if (averageScore >= 70 && averageScore < 80) {
                    level = 4;
                } else if (averageScore >= 60 && averageScore < 70) {
                    level = 3;
                } else {
                    level = 2;
                }
                //修改返回值
                data.get(i).setTotalScore(totalScore);
                data.get(i).setAverageScore(averageScore);
                data.get(i).setLevel(level);
            }
        }
        Integer total = worksMapper.getWorksTotal(keyWords);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }


    public Works check(int userId, int competitionId) {
        return worksMapper.getWorkByUserIdAndComId(userId, competitionId);
    }

    /**
     * 自动分配小组
     *
     * @param works
     * @return
     */
    public int insertWorks(Works works) {
//        查询出当前小组数量，生成随机数，随机分配小组
        int result = groupMapper.findGroupNum();
        //生成随机数据
        Random random = new Random();

        int j = random.nextInt(result - 1) + 1;

        //查询小组id集合
        List<Group> list = groupMapper.findGroupId();

        List<Integer> list1 = new ArrayList<>();

        //对已有的小组进行循环
        for (int i = 0; i < list.size(); i++) {
            list.get(i).getId();
            //吧小组id存到新的集合中
            list1.add(list.get(i).getId());
        }
        //传入随机数，获得小组id
        int groupId = list1.get(j);
        System.out.println("随机分配的小组id是：" + groupId);
        works.setGroupId(groupId);
        return worksMapper.insertWorks(works);
    }

    public int getWorksId(Works works) {
        final Works worksById = worksMapper.getWorksById(works);
        return worksById.getId();
    }


    public int uploadImage(Image image) {
        return imageMapper.uploadImage(image);
    }

    public int changeStartCheck(int id) {
        return worksMapper.changeStartCheck(id);
    }

    public RespPageBean getCheckWorksByPage(String keyWords, Integer groupId) {
        List<Users> data = worksMapper.getCheckWorksByPage(keyWords,groupId);
        Integer total = worksMapper.getCheckWorksTotal(keyWords,groupId);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    public Works getWorksDetail(int id) {
        return worksMapper.getWorksDetail(id);
    }


    public int checkScoring(Integer competitionId, Integer reviewerId) {
        return scoringMapper.checkScoring(competitionId, reviewerId);
    }

    /**
     * 计算平均成绩 等等
     * <p>
     * 1. 如果第一次打分就插入打分
     * 2. 如果不是第一次打分就根据之前的打分成绩进行修改
     *
     * @param id
     * @param totalScore
     * @param reviewerId
     * @param competitionId
     * @return
     */
    @Transactional
    public int scoring(Integer id, Integer totalScore, Integer reviewerId, Integer competitionId) {
        Works works = new Works();
        works.setTotalScore(totalScore);
//        这里的id是workId
        works.setId(id);

        Scoring scoring = new Scoring();
        scoring.setScore(totalScore);
        scoring.setReviewerId(reviewerId);
        scoring.setWorkId(id);
        scoring.setCompetitionId(competitionId);
//        对一个作品已经打过一次分数
        if (scoringMapper.check(reviewerId, id, competitionId) == 1) {
//            就修改
            scoringMapper.updateScoringRecord(scoring);
        } else {
//            往打分记录表中插入一条数据
            scoringMapper.insertScoringRecord(scoring);
        }
//        但是无论如何打分都会进行
        return worksMapper.scoring(works);
    }

    /**
     * 结束比赛统计成绩
     *
     * @param competitionId
     * @return
     */
    @Transactional
    public int endCompetitionById(Integer competitionId) {

        comConditionMapper.updateCompetitionById(competitionId);
        worksMapper.endCompetitionById(competitionId);
        worksMapper.computeScore(competitionId);
        int result = worksMapper.computeLevel(competitionId);


        return result;
    }

    public List<Works> getMyWorks(Integer userId) {

        /**
         * 监测是否参加过比赛 过滤没有参加过比赛的人
         */
        int trueOrFalse = worksMapper.checkJoinCompetition(userId);
        if (trueOrFalse == 0) {
            return null;
        }

        List<Works> myWorks = worksMapper.getMyWorks(userId);

        for (int i = 0; i < myWorks.size(); i++) {
            int workId = myWorks.get(i).getId();
            //根据workId找到打分表中，查看该作品的打分次数，如果打分次数大于3 就去掉最高分和最低分
            int count = scoringMapper.getScoringNumByWordId(workId);
            int averageScore = 0;
            int level = 0;
            int totalScore = 0;
            if (count >= 3) {
                int maxScore = 0;
                int minScore = 0;
                //找到最高分和最低分
                maxScore = scoringMapper.getMaxScore(workId);
                minScore = scoringMapper.getMinScore(workId);
                totalScore = myWorks.get(0).getTotalScore() - (maxScore + minScore);
                //重新计算平均得分
                //总分除以 打分次数-2
                averageScore = totalScore / (count - 2);
                //重新计算等级 根据当前的平均得分计算等级

                if (averageScore > 90) {
                    level = 5;
                } else if (averageScore > 80 && averageScore < 90) {
                    level = 4;
                } else if (averageScore > 70 && averageScore < 80) {
                    level = 3;
                } else if (averageScore > 60 && averageScore < 70) {
                    level = 2;
                } else {
                    level = 0;
                }
                //修改返回值
                myWorks.get(i).setTotalScore(totalScore);
                myWorks.get(i).setAverageScore(averageScore);
                myWorks.get(i).setLevel(level);
            }else {
                averageScore = myWorks.get(i).getAverageScore();
                totalScore = myWorks.get(i).getTotalScore();
                if (averageScore > 90) {
                    level = 5;
                } else if (averageScore > 80 && averageScore < 90) {
                    level = 4;
                } else if (averageScore > 70 && averageScore < 80) {
                    level = 3;
                } else if (averageScore > 60 && averageScore < 70) {
                    level = 2;
                } else {
                    level = 0;
                }
                myWorks.get(i).setTotalScore(totalScore);
                myWorks.get(i).setAverageScore(averageScore);
                myWorks.get(i).setLevel(level);
            }


        }

        return myWorks;
    }

    public RespPageBean getScoreListByPage(String keyWords) {
        List<Users> data = worksMapper.getScoreListByPage(keyWords);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        return bean;
    }
}
