package oops.oops_project.FirestoreDatabase;

import java.util.Date;

public class Note
{
    String title, content;
    Date date;

    public Note() {}

    public Note(String title, String content, Date date)
    {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public Date getDate() {  return date;}
    public void setDate(Date date) { this.date = date;}

    public String getTitle() { return title;}
    public void setTitle(String title) { this.title = title;}

    public String getContent() { return content;}
    public void setContent(String content) { this.content = content;}
}
