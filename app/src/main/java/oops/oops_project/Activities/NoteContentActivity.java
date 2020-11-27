package oops.oops_project.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import oops.oops_project.Database.Notes;
import oops.oops_project.Fragments.NoteDialogFragment;
import oops.oops_project.R;

public class NoteContentActivity extends AppCompatActivity
{
    public static final String TITLE = "Title";
    public static final String CONTENT = "Content";
    public static final String POS = "Position";

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

    public void editNote(View view)
    {
        NoteDialogFragment dialogFragment = NoteDialogFragment.newInstance(titleText.getText().toString(), contentText.getText().toString());
        dialogFragment.show(getSupportFragmentManager(), NoteDialogFragment.EDIT_NOTE);
    }

    public void deleteNote(View view)
    {
        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if(which == DialogInterface.BUTTON_NEUTRAL)
                    dialog.dismiss();
                else if(which == DialogInterface.BUTTON_POSITIVE)
                {
                    int position = (int) getIntent().getExtras().getInt(POS);
                    Notes.notes.remove(position);
                    finish();
                }
            }
        };

        MaterialAlertDialogBuilder discardDialog = new MaterialAlertDialogBuilder(this);
        discardDialog.setTitle("Are you sure you want to delete this note?");
        discardDialog.setNeutralButton("CANCEL", dialogListener);
        discardDialog.setPositiveButton("DELETE", dialogListener);
        discardDialog.show();
    }

    public void saveNote(String title, String content)
    {
        titleText.setText(title);
        contentText.setText(content);

        int position = getIntent().getExtras().getInt(POS);
        Notes.notes.set(position, new Notes.Note(title, content));

    }
}