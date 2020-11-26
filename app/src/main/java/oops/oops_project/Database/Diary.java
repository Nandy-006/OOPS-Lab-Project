package oops.oops_project.Database;

import java.util.HashMap;

public class Diary
{
    public static HashMap<String, Content> diary = new HashMap<>();

    public static void putEntry(String date, String title, String desc)
    {
        diary.put(date, new Content(title, desc));
    }

    public static String getTitle(String date)
    {
        try {
            return diary.get(date).title;
        }
        catch (NullPointerException e) {
            return "";
        }
    }

    public static String getDesc(String date)
    {
        try {
            return diary.get(date).desc;
        }
        catch (NullPointerException e) {
            return "";
        }
    }

    private static class Content
    {
        String title, desc;

        Content(String title, String desc)
        {
            this.title = title;
            this.desc = desc;
        }
    }
}
