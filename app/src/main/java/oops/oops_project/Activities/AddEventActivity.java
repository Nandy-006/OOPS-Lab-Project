package oops.oops_project.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import oops.oops_project.FirestoreDatabase.Event;
import oops.oops_project.Fragments.EventsFragment;
import oops.oops_project.R;

import static oops.oops_project.Activities.DashboardActivity.db;

public class AddEventActivity extends AppCompatActivity {

    int chosenDate, chosenYear, chosenMonth, chosenHour, chosenMinute;
    TextView dateText, timeText;
    EditText nameText, descText;
    CheckBox checkBox;
    Button DateButton, TimeButton;

    String dateTimeString;


    long timeinms;
    boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        dateText = findViewById(R.id.event_date_text_view);
        timeText = findViewById(R.id.event_time_text_view);
        nameText = findViewById(R.id.event_name);
        descText = findViewById(R.id.event_description);
        checkBox = findViewById(R.id.event_reminder);
        DateButton = findViewById(R.id.event_set_date);
        TimeButton = findViewById(R.id.event_set_time);

        checkBox.setClickable(true);

        try {

            String date = getIntent().getStringExtra("DATE");
            String time = getIntent().getStringExtra("TIME");
            String name = getIntent().getStringExtra("NAME");
            String desc = getIntent().getStringExtra("DESC");
            boolean state = getIntent().getBooleanExtra("STATE", false);

            dateTimeString = Event.getSimpleFormat(date, time);

            checkBox.setClickable(false);

            dateText.setText(date);
            timeText.setText(time);
            nameText.setText(name);
            descText.setText(desc);
            checkBox.setChecked(state);

            isEdit = true;
        }
        catch (Exception e) {}
    }

    public void save(View view) {

        if (dateText.getText().equals(getString(R.string.set_date)) || timeText.getText().equals(getString(R.string.set_time)))
            Toast.makeText(getApplicationContext(), "Set both date and time", Toast.LENGTH_SHORT).show();
        else if (nameText.getText().equals(""))
            Toast.makeText(getApplicationContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
        else {
            Date date;
            boolean wantReminder;
            String name, desc;
            if(!isEdit)
            {

                        dateTimeString = String.format(Locale.getDefault(), "%02d", chosenDate) +
                        String.format(Locale.getDefault(), "%02d", chosenMonth + 1) +
                        chosenYear + String.format(Locale.getDefault(), "%02d", chosenHour) +
                        String.format(Locale.getDefault(), "%02d", chosenMinute);
            }
            try {
                date = new SimpleDateFormat("ddMMyyyyHHmm", Locale.getDefault()).parse(dateTimeString);
                name = nameText.getText().toString();
                desc = descText.getText().toString();
                wantReminder = checkBox.isChecked();

                /*timeinms = date.getTime()- (new Date()).getTime();

                Intent intent = new Intent(this, MyAlarm.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        this.getApplicationContext(), 234324243, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                        + timeinms , pendingIntent);
                Toast.makeText(this, "Alarm set in " + timeinms + " ms",Toast.LENGTH_LONG).show();*/
                if(wantReminder)
                {
                    Toast.makeText(this, "Reminder", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(AddEventActivity.this, MyAlarm.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(AddEventActivity.this, 0, intent, 0);

                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                    long timeAtButtonClick = System.currentTimeMillis();

                    long tenSecondsInMillis = 1300*10;
                    alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick+tenSecondsInMillis, pendingIntent);
                }


                /*Calendar calendar = Calendar.getInstance();

                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                            chosenHour, chosenMinute, 0);

                timeinms = calendar.getTimeInMillis();
                Toast.makeText(this, Long.toString(timeinms), Toast.LENGTH_SHORT).show();
                setAlarm(timeinms);*/


                Event event = new Event(name, desc, date, wantReminder);

                db().collection(EventsFragment.EVENTS_PATH).add(event)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                if(isEdit)
                                    Toast.makeText(getApplicationContext(), "Event edited", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getApplicationContext(), "Event added", Toast.LENGTH_SHORT).show();
                            }
                        });
                finish();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Couldn't get date", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*@RequiresApi(api = Build.VERSION_CODES.O)
    */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void CreateNotificationChannel()
    {
        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.)
        {*/
            CharSequence name = "Taskete";
            String description = "reminder for task";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("noitfytask", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        /*}*/
    }

    public void delete(View view)
    {
        finish();
    }

    public void openDateDialog(View view)
    {
        int mYear, mMonth, mDay;

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                chosenDate = dayOfMonth;
                chosenMonth = month;
                chosenYear = year;

                String dateString = String.format(Locale.getDefault(), "%02d", chosenDate) + "-" +
                        String.format(Locale.getDefault(),"%02d", chosenMonth+1) + "-" +
                        chosenYear;
                dateText.setText(dateString);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void openTimeDialog(View view)
    {
        int mHour, mMinute;

        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                chosenHour = hourOfDay;
                chosenMinute = minute;

                String timeString = String.format(Locale.getDefault(), "%02d", chosenHour) + ":" +
                        String.format(Locale.getDefault(),"%02d", chosenMinute);
                timeText.setText(timeString);
            }
        }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void setAlarm(long time) {
        //getting the alarm manager
        /*AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(this, MyAlarm.class);

        //creating a pending intent using the intent
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);

        //setting the repeating alarm that will be fired every day
        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        Toast.makeText(this, "Alarm is set in " + Long.toString(time) + "ms", Toast.LENGTH_LONG).show();*/


    }

}