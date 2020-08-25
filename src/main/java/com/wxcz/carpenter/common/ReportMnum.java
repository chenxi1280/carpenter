package com.wxcz.carpenter.common;

/**
 * @author by cxd
 * @Classname StaticString
 * @Description TODO
 * @Date 2020/8/21 13:28
 */
public enum ReportMnum {

    //侵权视频状态
    tort(1,"侵权"),violations(2,"违规"),other(3,"其他");
    int value;
    String name;


    ReportMnum(int i, String name) {
        this.value = i;
        this.name = name;
    }
    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static ReportMnum getByValue(int value) {
        for(ReportMnum ReportMnum : ReportMnum.values()) {
            if(ReportMnum.value == value) {
                return ReportMnum;
            }
        }
//        System.out.println(StaticString.tort.get);
        throw new IllegalArgumentException("No element matches " + value);
    }


}
