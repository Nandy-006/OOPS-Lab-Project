package oops.oops_project.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import oops.oops_project.R;

public class DashboardActivity extends AppCompatActivity
{
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    private void sendIntent()
    {
        Intent intent = new Intent(this, NavigatingDashboardActivity.class);
        intent.putExtra(NavigatingDashboardActivity.CHOICE, id);
        startActivity(intent);
    }

    public void choseInventory(View view) { id = R.id.bnav_inventory; sendIntent();}
    public void choseTasks(View view) { id = R.id.bnav_tasks; sendIntent();}
    public void choseNotes(View view) { id = R.id.bnav_notes; sendIntent();}
    public void choseDiary(View view) { id = R.id.bnav_diary; sendIntent();}
}