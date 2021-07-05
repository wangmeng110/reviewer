package com.ebusiness.reviewer.enums;

/**
 * @author husheng
 * @date 2021/5/30 - 16:37
 */
public enum CompetitionType {
    BAO_MING_ZHONG(1, "报名中"),
    WEI_KAI_SHI(2, "未开始"),
    JIN_JING_ZHONG(3, "进行中"),
    YI_JIE_SHU(4, "已结束");

    private final Integer status;
    private final String name;

    CompetitionType(Integer status, String name) {
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
