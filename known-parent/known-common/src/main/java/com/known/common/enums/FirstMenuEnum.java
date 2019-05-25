package com.known.common.enums;

/**
 * 后台管理一级目录
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-25 21:07
 */
public enum FirstMenuEnum {

    SYSTEM("系统管理"),
    CONTENT("内容管理"),
    CONSOLE("控制台");

    private String name;

    private FirstMenuEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
