package com.ebusiness.reviewer.pojo;

/**
 * @author husheng
 * @date 2021/5/17 - 19:56
 */
public enum ExamType {

    DESIGNATED_DATE(1, "已结束"),
    HOMEWORK_COMPLETED(2, "未开始"),
    EXAM_COMPLETED(3, "未结束");

    private final Integer status;
    private final String name;


    ExamType(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public static ExamType getByStatus(Integer status) {
        for (ExamType conditionType : values()) {
            if (conditionType.getStatus().equals(status)) {
                return conditionType;
            }
        }
        return null;
    }
}
