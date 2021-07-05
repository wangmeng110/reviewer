package com.ebusiness.reviewer.mapper;

import com.ebusiness.reviewer.model.ComCondition;

/**
 * @author suitianshuang
 * @date 2021/5/30 - 13:07
 */
public interface ComConditionMapper {


    int insertComCondition(ComCondition comCondition);

    ComCondition getComCondition(Integer id);

    int updateCompetitionById(Integer id);
}
