package com.maple.userapi.vo;

import lombok.Data;

import java.util.List;


@Data
public class Meta {

    private boolean hideInMenu;
    private boolean showAlways;
    private boolean notCache;
    private List<String> access;
    private String icon;
    private String href;
    private String title;
}
