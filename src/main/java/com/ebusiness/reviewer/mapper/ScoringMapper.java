package com.ebusiness.reviewer.mapper;

import com.ebusiness.reviewer.model.Scoring;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScoringMapper {


    int checkScoring(@Param("competitionId") Integer competitionId,@Param("reviewerId") Integer reviewerId);
    int insertScoringRecord(Scoring scoring);

    int check(@Param("reviewerId") Integer reviewerId,@Param("workId") Integer id,@Param("competitionId") Integer competitionId);

    void updateScoringRecord(Scoring scoring);

    void deleteWorksByCompetitionId(Integer CompetitionId);
    List<Scoring> getScoringRecords();

    @Select("select count(*) from scoring where work_id = #{workId}")
    int getScoringNumByWordId(int workId);

    @Select("select Max(score) from scoring where work_id = #{workId}")
    int getMaxScore(int workId);
    @Select("select Min(score) from scoring where work_id = #{workId}")
    int getMinScore(int workId);

}
