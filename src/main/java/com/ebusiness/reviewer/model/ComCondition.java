package com.ebusiness.reviewer.model;

import java.util.Date;

/**
 * @author husheng
 * @date 2021/5/30 - 12:59
 */
public class ComCondition {

    private Integer id;

    private Integer designPer;

    private Integer posterPer;

    private Integer themeStylePer;

    private Integer attactionPer;

    private Integer analysisPer;

    private Integer firstPrizePer;

    private Integer competitionId;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDesignPer() {
        return designPer;
    }

    public void setDesignPer(Integer designPer) {
        this.designPer = designPer;
    }

    public Integer getPosterPer() {
        return posterPer;
    }

    public void setPosterPer(Integer posterPer) {
        this.posterPer = posterPer;
    }

    public Integer getThemeStylePer() {
        return themeStylePer;
    }

    public void setThemeStylePer(Integer themeStylePer) {
        this.themeStylePer = themeStylePer;
    }

    public Integer getAttactionPer() {
        return attactionPer;
    }

    public void setAttactionPer(Integer attactionPer) {
        this.attactionPer = attactionPer;
    }

    public Integer getAnalysisPer() {
        return analysisPer;
    }

    public void setAnalysisPer(Integer analysisPer) {
        this.analysisPer = analysisPer;
    }

    public Integer getFirstPrizePer() {
        return firstPrizePer;
    }

    public void setFirstPrizePer(Integer firstPrizePer) {
        this.firstPrizePer = firstPrizePer;
    }

    public Integer getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Integer competitionId) {
        this.competitionId = competitionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
