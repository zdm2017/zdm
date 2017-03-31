package com.example.answer.fragment;



/**
 * Created by zbmobi on 15/4/7.
 */
public class DbFilesEntity extends BaseEntity{

    private static final long serialVersionUID = 1L;

    private String ID;
    private String Content;
    private String Name;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
