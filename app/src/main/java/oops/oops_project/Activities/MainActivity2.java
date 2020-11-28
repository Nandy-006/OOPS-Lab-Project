package oops.oops_project.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import oops.oops_project.AuthActivities.EmailPasswordActivity;
import oops.oops_project.AuthActivities.GoogleSignInActivity;
import oops.oops_project.AuthActivities.ManageUserActivity;
import oops.oops_project.R;

public class MainActivity2 extends AppCompatActivity {
    private static boolean loginClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button email = (Button) findViewById(R.id.GoToEmail);
        Button google = (Button) findViewById(R.id.GoToGoogle);
        Button editaccount = (Button) findViewById(R.id.EditAccount);

        loginClicked = getIntent().getBooleanExtra("LoginClicked", true);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EmailPasswordActivity.class);
                intent.putExtra("LoginClicked", loginClicked);
                view.getContext().startActivity(intent);
            }

        });

        google.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), GoogleSignInActivity.class);
                intent.putExtra("LoginClicked", loginClicked);
                view.getContext().startActivity(intent);
            }

        });

        editaccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ManageUserActivity.class);
                intent.putExtra("LoginClicked", false);
                view.getContext().startActivity(intent);
            }
        });
    }

}
