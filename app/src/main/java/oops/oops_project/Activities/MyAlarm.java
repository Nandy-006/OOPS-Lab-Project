package oops.oops_project.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import java.util.Timer;
import java.util.TimerTask;

import oops.oops_project.R;

/**
 * Created by Belal on 8/29/2017.
 */

//class extending the Broadcast Receiver
/*public class MyAlarm extends BroadcastReceiver
{


    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    String phoneNo;
    String message;


    //the method will be fired when the alarm is triggerred

    @Override
    public void onReceive(Context context, Intent intent) {

        //you can check the log that it is fired
        //Here we are actually not doing anything
        //but you can do any task here that you want to be done at a specific time everyday
        Log.d("MyAlarmBelal", "Alarm just fired");
        Toast.makeText(context, "Alarm  fired", Toast.LENGTH_LONG).show();
    }
}*/
public class MyAlarm extends BroadcastReceiver {
    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";
    MediaPlayer mp;

    @Override
    public void onReceive(Context context, Intent intent) {
        //mp=MediaPlayer.create(context, R.raw.alarm);
        //mp.start();
        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify task")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Task Reminder")
                .setContentText("You have one upcoming task")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());


    }
}


       /* NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.home_screen_icon);
        mBuilder.setContentTitle("Notification Alert, Click Me!");
        mBuilder.setContentText("Hi, This is Android Notification Detail!");

        Intent resultIntent = new Intent(this, ResultActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(ResultActivity.class);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);*/

// notificationID allows you to update the notification later on.
        /*mNotificationManager.notify(123, mBuilder.build());

// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);*/



    /*private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");
            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Channel 2",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription("This is Channel 2");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }*/

/*
        final Timer mytimer = new Timer(true);
        final TimerTask mytask = new TimerTask() {
            public void run() {
                SmsManager sms = SmsManager.getDefault();

                ActivityCompat.requestPermissions(My,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);

                sms.sendTextMessage("+917760618943", "", "Hi Droide!",
                        null, null);
            }
        };
        mytimer.schedule(mytask, 30000L, 90000L);
        phoneNo = "7760618943";
        message = "hello world";*/

        /*if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(com.example.alarmmanager.MyAlarm,
                    Manifest.permission.SEND_SMS))
            {
                //Toast.makeText(getApplicationContext(),  "inside blank block", Toast.LENGTH_LONG).show();
            }
            else
            {
                //Toast.makeText(getApplicationContext(),  "inside else block", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(MyAlarm,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else
        {
            //Toast.makeText(getApplicationContext(),  "inside big else block", Toast.LENGTH_SHORT).show();
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(context, "SMS sent.",
                    Toast.LENGTH_LONG).show();
        }
    }



        public void onRequestPermissionsResult (int requestCode, String permissions[], int[] grantResults)
        {
            switch (requestCode) {
                case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNo, null, message, null, null);
                        Toast.makeText(context, "SMS sent.",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context,
                                "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }

        }
        ;
*/


