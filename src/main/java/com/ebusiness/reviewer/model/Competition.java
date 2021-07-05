package com.ebusiness.reviewer.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Competition {
    private int id;
    private String cname;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date endTime;
    private Boolean isenable;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date checkTime;
    private String process;
    private String detail;

    private ComCondition comCondition;

    /**
     * 是否上传过附件
     */
    private Boolean haveAnnex;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Boolean getIsenable() {
        return isenable;
    }

    public void setIsenable(Boolean isenable) {
        this.isenable = isenable;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public ComCondition getComCondition() {
        return comCondition;
    }

    public void setComCondition(ComCondition comCondition) {
        this.comCondition = comCondition;
    }

    public Boolean getHaveAnnex() {
        return haveAnnex;
    }

    public void setHaveAnnex(Boolean haveAnnex) {
        this.haveAnnex = haveAnnex;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "id=" + id +
                ", cname='" + cname + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isenable=" + isenable +
                ", createTime=" + createTime +
                ", checkTime=" + checkTime +
                ", process='" + process + '\'' +
                ", detail='" + detail + '\'' +
                ", comCondition=" + comCondition +
                ", haveAnnex=" + haveAnnex +
                '}';
    }
}
