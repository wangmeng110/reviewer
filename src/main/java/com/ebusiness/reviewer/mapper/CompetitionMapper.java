package com.ebusiness.reviewer.mapper;

import com.ebusiness.reviewer.model.Competition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompetitionMapper {

    int createCompetition(Competition competition);

    List<Competition> getCompetitionList();

    List<Competition> getMyJoinCompetition(int id);

    int closeCompetitionById(Integer competitionId);

    int checkScoring(@Param("reviewerId") Integer reviewerId, @Param("workId") Integer workId, @Param("competitionId") Integer competitionId);

    Competition getCompetitionById(Integer id);

    int endCompetitionById(int id);
}
