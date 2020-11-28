package oops.oops_project.FirestoreDatabase;

import java.util.Date;

public class Category
{
    private String title, desc;
    private Date date;

    public Category() {}

    public Category(String title, String desc, Date date)
    {
        this.title = title;
        this.desc = desc;
        this.date = date;
    }

    public Date getDate() { return date;}
    public void setDate(Date date) { this.date = date;}

    public String getTitle() { return title;}
    public void setTitle(String title) { this.title = title;}

    public String getDesc() { return desc;}
    public void setDesc(String desc) { this.desc = desc;}

}
