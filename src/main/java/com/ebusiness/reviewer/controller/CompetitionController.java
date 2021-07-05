package com.ebusiness.reviewer.controller;

import com.ebusiness.reviewer.model.Competition;
import com.ebusiness.reviewer.model.Scoring;
import com.ebusiness.reviewer.service.CompetitionService;
import com.ebusiness.reviewer.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/competition")
public class CompetitionController {
    @Autowired
    CompetitionService competitionService;

    @PostMapping("/create")
    public RespBean createCompetition(@RequestBody Competition competition){
        return competitionService.createCompetition(competition);

    }
    @GetMapping("/")
    public RespBean getCompetitionList(){
        List<Competition> list = competitionService.getCompetitionList();
        if(list != null){
            return RespBean.ok("",list);
        }
        return RespBean.error("获取列表失败");
    }
//    获取打分记录等信息
    @GetMapping("/scoringRecords/")
    public RespBean getScoringRecords(){

      List<Scoring> list=  competitionService.getScoringRecords();
      if(list != null){
          return RespBean.ok(list);
      }
      return RespBean.error("获取评审记录失败");
    }
    @PutMapping("/close/")
    public RespBean closeCompetitionById(Integer competitionId){
        int result = competitionService.closeCompetitionById(competitionId);

        if(result == 1){
            return RespBean.ok("终止成功");
        }
        return RespBean.error("关闭失败");
    }
    /**
     * 检验是否打过分数
     */
    @GetMapping("/checkScoring/")
    public RespBean checkScoring(Integer reviewerId, Integer workId, Integer competitionId){
        System.out.println(reviewerId + workId + competitionId);
        int count = competitionService.checkScoring(reviewerId,workId,competitionId);
        return RespBean.ok(count);
    }

    @GetMapping("/getComCondition/")
    public RespBean getComCondition(Integer id){
        return competitionService.getComCondition(id);
    }

    @GetMapping("/getCompetitionResultList")
    public RespBean getCompetitionResultList(Integer id){
        return competitionService.getCompetitionResultList(id);
    }
}
