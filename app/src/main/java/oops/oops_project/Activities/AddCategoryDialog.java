package oops.oops_project.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import oops.oops_project.R;

public class AddCategoryDialog extends AppCompatDialogFragment {
    private EditText editTextCategoryName;
    private EditText getEditTextCategoryDescription;
    private AddCategoryDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.full_screen_dialog_add_category, null);

        editTextCategoryName = view.findViewById(R.id.add_category_title);
        getEditTextCategoryDescription = view.findViewById(R.id.add_category_description);

        builder.setView(view)
                .setTitle("Add Category")
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AddCategoryDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement AddActivityDialogListener");
        }
    }

    public interface AddCategoryDialogListener {
        void applyTexts(String title, String description);
    }

}
