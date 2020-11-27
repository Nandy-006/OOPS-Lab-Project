package oops.oops_project.FirestoreDatabase;

public class DiaryEntry
{
    String title, desc;

    public DiaryEntry(String title, String desc)
    {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() { return title;}
    public void setTitle(String title) { this.title = title;}

    public String getDesc() { return desc;}
    public void setDesc(String desc) { this.desc = desc;}
}
