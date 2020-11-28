package oops.oops_project.AuthActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import oops.oops_project.Activities.MainActivity2;
import oops.oops_project.R;

public class StartUpActivity extends AppCompatActivity
{
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    String phoneNo;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        Button loginButton = (Button) findViewById(R.id.LoginButton);
        Button signupButton = (Button) findViewById(R.id.SignUpButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity2.class);
                intent.putExtra("LoginClicked", true);
                view.getContext().startActivity(intent);
            }

        });

        signupButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                /*final Timer mytimer = new Timer(true);

                final TimerTask mytask = new TimerTask() {
                    public void run() {
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage("+917760618943", "", "Hi Droide!",
                                null, null);
                    } */
                    Intent intent = new Intent(view.getContext(), MainActivity2.class);
                    intent.putExtra("LoginClicked", false);
                    view.getContext().startActivity(intent);
                }
                //mytimer.schedule(mytask, 30000L, 90000L);
                //sendSMSMessage();


        });
    }
    /*
    protected void sendSMSMessage()
    {
        phoneNo = "7760618943";
        message = "hello world";

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS))
            {
                //Toast.makeText(getApplicationContext(),  "inside blank block", Toast.LENGTH_LONG).show();
            }
            else {
                //Toast.makeText(getApplicationContext(),  "inside else block", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else
        {
            //Toast.makeText(getApplicationContext(),  "inside big else block", Toast.LENGTH_SHORT).show();
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    } */

}
