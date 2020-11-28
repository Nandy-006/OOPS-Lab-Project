package oops.oops_project.FirestoreDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Event
{
    private String name, description;
    Date datetime;
    private boolean wantReminder;

    public Event() {}

    public Event(String name, String description, Date datetime, boolean wantReminder)
    {
        this.name = name;
        this.description = description;
        this.datetime = datetime;
        this.wantReminder = wantReminder;
    }

    public String getName() { return name;}
    public void setName(String name) { this.name = name;}

    public String getDescription() { return description;}
    public void setDescription(String description) { this.description = description;}

    public Date getDatetime() { return datetime;}
    public void setDatetime(Date datetime) { this.datetime = datetime;}

    public boolean isWantReminder() { return wantReminder;}
    public void setWantReminder(boolean wantReminder) { this.wantReminder = wantReminder;}

    public String getFormattedDatetime()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        return dateFormat.format(datetime);
    }

    public String getFormattedDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return dateFormat.format(datetime);
    }

    public String getFormattedTime()
    {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return dateFormat.format(datetime);
    }

    public static String getSimpleFormat(String formattedDate, String formattedTime) throws ParseException
    {
        Date date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(formattedDate);
        Date time = new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(formattedTime);
        return (new SimpleDateFormat("ddMMyyyy", Locale.getDefault())).format(date)
                + (new SimpleDateFormat("HHmm", Locale.getDefault())).format(time);
    }
}
