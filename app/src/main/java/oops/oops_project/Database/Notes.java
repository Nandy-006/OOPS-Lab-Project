package oops.oops_project.Database;

import java.util.ArrayList;

public class Notes
{
    public static ArrayList<Note> notes = new ArrayList<>();

    static {
        notes.add(new Note("Hey", "there"));
        notes.add(new Note("Hello", "there"));
        notes.add(new Note("Hey", "there"));
        notes.add(new Note("Hello", "there"));
    }

    public static void putEntry(String title, String content) { notes.add(new Note(title, content));}
    public static String getTitle(int idx) { return notes.get(idx).title;}
    public static String getContent(int idx) { return notes.get(idx).content;}

    public static class Note
    {
        String title, content;

        public Note(String title, String content)
        {
            this.title = title;
            this.content = content;
        }
    }
}
