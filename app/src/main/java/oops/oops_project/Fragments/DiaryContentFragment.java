package oops.oops_project.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import oops.oops_project.R;

public class DiaryContentFragment extends Fragment
{
    String title, desc;
    EditText titleEditText, descEditText;
    TextView titleViewText, descViewText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_diary_content, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        titleEditText = view.findViewById(R.id.diary_edit_title);
        descEditText = view.findViewById(R.id.diary_edit_desc);
        titleViewText = view.findViewById(R.id.diary_view_title);
        descViewText = view.findViewById(R.id.diary_view_desc);

        titleEditText.setEnabled(true);
        descEditText.setEnabled(true);
        titleViewText.setEnabled(true);
        descViewText.setEnabled(true);

        disableEdit();
    }

    public DiaryContentFragment(String title, String desc)
    {
        this.title = title;
        this.desc = desc;
    }
    public void updateContent()
    {
        title = titleEditText.getText().toString();
        desc = descEditText.getText().toString();
    }

    public String getTitle() { return title;}
    public String getDesc() {return desc;}

    private void setText()
    {
        if(title.equals(""))
        {
            titleEditText.setHint(R.string.diary_title);
            titleViewText.setHint(R.string.diary_title);
        }
        else
        {
            titleEditText.setText(title);
            titleViewText.setText(title);
        }

        if(desc.equals(""))
        {
            descEditText.setHint(R.string.diary_desc);
            descViewText.setHint(R.string.diary_desc);
        }
        else
        {
            descEditText.setText(desc);
            descViewText.setText(desc);
        }
    }

    public void enableEdit()
    {
        setText();
        titleEditText.setVisibility(View.VISIBLE);
        descEditText.setVisibility(View.VISIBLE);
        titleViewText.setVisibility(View.GONE);
        descViewText.setVisibility(View.GONE);
    }

    public void disableEdit()
    {
        setText();
        titleEditText.setVisibility(View.GONE);
        descEditText.setVisibility(View.GONE);
        titleViewText.setVisibility(View.VISIBLE);
        descViewText.setVisibility(View.VISIBLE);
    }
}