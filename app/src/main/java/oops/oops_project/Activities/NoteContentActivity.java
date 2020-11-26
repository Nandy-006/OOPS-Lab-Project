package oops.oops_project.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import oops.oops_project.R;

public class NoteContentActivity extends AppCompatActivity
{
    public static final String TITLE = "Title";
    public static final String CONTENT = "Content";

    TextView titleText, contentText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_content);

        Intent intent = getIntent();
        String title = (String) intent.getExtras().get(TITLE);
        String content = (String) intent.getExtras().get(CONTENT);

        titleText = findViewById(R.id.note_title);
        contentText = findViewById(R.id.note_content);
        titleText.setText(title);
        contentText.setText(content);
    }
}