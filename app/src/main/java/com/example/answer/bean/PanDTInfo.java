package com.example.answer.bean;

import com.litesuits.orm.db.annotation.Table;

/**
 * Created by zbmobi on 16/7/12.
 */
@Table("判断题")
public class PanDTInfo {

    private int ID;
    private String 题目;
    private String 答案;
    private String 分值;
    private String 考生选择;
    private String 得分;
    private String 标记;
    private String 解释;
    private String 记事本;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String get题目() {
        return 题目;
    }

    public void set题目(String 题目) {
        this.题目 = 题目;
    }

    public String get答案() {
        return 答案;
    }

    public void set答案(String 答案) {
        this.答案 = 答案;
    }

    public String get分值() {
        return 分值;
    }

    public void set分值(String 分值) {
        this.分值 = 分值;
    }

    public String get考生选择() {
        return 考生选择;
    }

    public void set考生选择(String 考生选择) {
        this.考生选择 = 考生选择;
    }

    public String get得分() {
        return 得分;
    }

    public void set得分(String 得分) {
        this.得分 = 得分;
    }

    public String get标记() {
        return 标记;
    }

    public void set标记(String 标记) {
        this.标记 = 标记;
    }

    public String get解释() {
        return 解释;
    }

    public void set解释(String 解释) {
        this.解释 = 解释;
    }

    public String get记事本() {
        return 记事本;
    }

    public void set记事本(String 记事本) {
        this.记事本 = 记事本;
    }
}
