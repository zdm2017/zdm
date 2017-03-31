package com.example.answer.bean;

import com.litesuits.orm.db.annotation.Table;

/**
 * Created by zbmobi on 16/7/15.
 */
@Table("FXT")
public class FXTInfo {

    private int ID;
    private byte[] binData;
    private String binString;
    private String 题1;
    private String 题2;
    private String 题3;
    private String 题4;
    private String 题5;
    private String 答案1;
    private String 答案2;
    private String 答案3;
    private String 答案4;
    private String 答案5;
    private String 分值;
    private String 考生选择1;
    private String 考生选择2;
    private String 考生选择3;
    private String 考生选择4;
    private String 考生选择5;
    private String 得分;
    private String 标记;
    private String 解释;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public byte[] getBinData() {
        return binData;
    }

    public void setBinData(byte[] binData) {
        this.binData = binData;
    }

    public String getBinString() {
        return binString;
    }

    public void setBinString(String binString) {
        this.binString = binString;
    }

    public String get题1() {
        return 题1;
    }

    public void set题1(String 题1) {
        this.题1 = 题1;
    }

    public String get题2() {
        return 题2;
    }

    public void set题2(String 题2) {
        this.题2 = 题2;
    }

    public String get题3() {
        return 题3;
    }

    public void set题3(String 题3) {
        this.题3 = 题3;
    }

    public String get题4() {
        return 题4;
    }

    public void set题4(String 题4) {
        this.题4 = 题4;
    }

    public String get题5() {
        return 题5;
    }

    public void set题5(String 题5) {
        this.题5 = 题5;
    }

    public String get答案1() {
        return 答案1;
    }

    public void set答案1(String 答案1) {
        this.答案1 = 答案1;
    }

    public String get答案2() {
        return 答案2;
    }

    public void set答案2(String 答案2) {
        this.答案2 = 答案2;
    }

    public String get答案3() {
        return 答案3;
    }

    public void set答案3(String 答案3) {
        this.答案3 = 答案3;
    }

    public String get答案4() {
        return 答案4;
    }

    public void set答案4(String 答案4) {
        this.答案4 = 答案4;
    }

    public String get答案5() {
        return 答案5;
    }

    public void set答案5(String 答案5) {
        this.答案5 = 答案5;
    }

    public String get分值() {
        return 分值;
    }

    public void set分值(String 分值) {
        this.分值 = 分值;
    }

    public String get考生选择1() {
        return 考生选择1;
    }

    public void set考生选择1(String 考生选择1) {
        this.考生选择1 = 考生选择1;
    }

    public String get考生选择2() {
        return 考生选择2;
    }

    public void set考生选择2(String 考生选择2) {
        this.考生选择2 = 考生选择2;
    }

    public String get考生选择3() {
        return 考生选择3;
    }

    public void set考生选择3(String 考生选择3) {
        this.考生选择3 = 考生选择3;
    }

    public String get考生选择4() {
        return 考生选择4;
    }

    public void set考生选择4(String 考生选择4) {
        this.考生选择4 = 考生选择4;
    }

    public String get考生选择5() {
        return 考生选择5;
    }

    public void set考生选择5(String 考生选择5) {
        this.考生选择5 = 考生选择5;
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
}
