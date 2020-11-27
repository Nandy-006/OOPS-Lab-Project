package oops.oops_project.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import oops.oops_project.R;

public class AddEventDialog extends AppCompatDialogFragment {
    private EditText editTextEventName;
    private Button buttonSetEventDate;
    private EditText editTextEventDescription;
    private CheckBox checkBoxEventReminder;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.full_screen_dialog_add_event, null);

        editTextEventDescription = view.findViewById(R.id.event_description);
        buttonSetEventDate = view.findViewById(R.id.event_set_date);
        checkBoxEventReminder = view.findViewById(R.id.event_reminder);

        builder.setView(view)
                .setTitle("Add Event")
                .setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = editTextCategoryName.getText().toString();
                        String description = getEditTextCategoryDescription.getText().toString();
                        listener.applyTexts(title, description);
                    }
                });

        return builder.create();
    }
}
