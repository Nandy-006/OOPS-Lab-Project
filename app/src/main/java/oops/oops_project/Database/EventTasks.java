package oops.oops_project.Database;

import java.util.ArrayList;

public class EventTasks {
    private String mName;
    private String mTime;

    public void EventTasks(String name, String time) {
        mName = name;
        mTime = time;
    }

    public String getName() { return mName; }

    public String getTime() { return mTime; }

    private static int lastEventId = 0;

}
