package com.example.answer.fragment;

import java.io.Serializable;

/**
 * Created by zbmobi on 15/11/17.
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private int themeColor;

    public int getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(int themeColor) {
        this.themeColor = themeColor;
    }
}
