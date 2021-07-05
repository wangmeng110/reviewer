package com.ebusiness.reviewer.enums;

/**
 * @author husheng
 * @date 2021/5/30 - 16:49
 */
public enum GradeType {
    ONE(1, "一等奖"),
    TWO(2, "二等奖"),
    THREE(3, "三等奖"),
    FOUR(4, "未获奖");

    private final Integer status;
    private final String name;

    GradeType(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
