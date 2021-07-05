package com.ebusiness.reviewer.pojo.vo;

/**
 * @author husheng
 * @date 2021/5/30 - 18:14
 */
public class GroupVO {

    private Integer id;

    private String worksName;

    private String username;

    private String createTime;

    private String startCheck;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWorksName() {
        return worksName;
    }

    public void setWorksName(String worksName) {
        this.worksName = worksName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStartCheck() {
        return startCheck;
    }

    public void setStartCheck(String startCheck) {
        this.startCheck = startCheck;
    }
}
