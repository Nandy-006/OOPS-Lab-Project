package oops.oops_project.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import oops.oops_project.R;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Temporary code
        Intent intent = new Intent(this, NavigatingDashboardActivity.class);
        startActivity(intent);
    }
}