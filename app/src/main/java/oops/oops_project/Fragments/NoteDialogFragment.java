package oops.oops_project.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import oops.oops_project.Activities.NoteContentActivity;
import oops.oops_project.R;

public class NoteDialogFragment extends DialogFragment
{
    private EditText titleText, contentText;
    private Button saveButton;

    public static final String NEW_NOTE = "New Note";
    public static final String EDIT_NOTE = "Edit Note";

    public NoteDialogFragment() {}

    public static NoteDialogFragment newInstance(String title, String content)
    {
        NoteDialogFragment frag = new NoteDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("content", content);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_note_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        titleText = view.findViewById(R.id.note_dialog_title);
        contentText = view.findViewById(R.id.note_dialog_content);
        saveButton = view.findViewById(R.id.note_dialog_save);

        if(getArguments() != null)
        {
            titleText.setText(getArguments().getString("title"));
            contentText.setText(getArguments().getString("content"));
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                View parent = (View) v.getParent();
                String title = ((EditText) parent.findViewById(R.id.note_dialog_title)).getText().toString();
                String content = ((EditText) parent.findViewById(R.id.note_dialog_content)).getText().toString();
                ((NoteContentActivity) getActivity()).saveNote(title, content);
                dismiss();
            }
        });
    }

    @Override
    public void onResume()
    {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }
}