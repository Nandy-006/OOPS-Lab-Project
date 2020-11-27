package oops.oops_project.AuthActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.support.v4.app.*;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.*;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import oops.oops_project.R;

public class Registration extends AppCompatActivity
{
    String[] country = { "India", "USA", "China", "Japan", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        Spinner spinner = (Spinner) findViewById(R.id.profession);




        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.profession_array, R.layout.profession_spinner);
        spinner.setAdapter(adapter);

        Button submitButton = (Button) findViewById(R.id.SubmitDetailsButton);

        Intent fromintent = getIntent();
        String EmailEntered  = fromintent.getStringExtra("Email");

        TextInputEditText name = (TextInputEditText) findViewById(R.id.NameOfUser);
        TextInputEditText phone = (TextInputEditText) findViewById(R.id.PhoneNumber);
        TextInputEditText email = (TextInputEditText) findViewById(R.id.EmailOfUser);

        email.setText(EmailEntered);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String Name = name.getText().toString();
                String Phone = phone.getText().toString();
                String Email = email.getText().toString();

                if(!(Pattern.matches("[a-zA-Z\\s]+", Name)))
                {
                    Toast.makeText(Registration.this, "Invalid Name", Toast.LENGTH_SHORT).show();
                }
                else if(!(Pattern.matches("[\\d]{10}", Phone)))
                {
                    Toast.makeText(Registration.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                }

                else if(!(Pattern.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$", Email)))
                {
                    Toast.makeText(Registration.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Registration.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Registration.this, PhoneAuthActivity.class);
                    intent.putExtra("phno", Phone);
                    startActivity(intent);
                }

            }

        });

    }






}
