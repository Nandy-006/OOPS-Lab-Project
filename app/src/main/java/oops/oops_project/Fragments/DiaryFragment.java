package oops.oops_project.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import oops.oops_project.Activities.DashboardActivity;
import oops.oops_project.FirestoreDatabase.Data;
import oops.oops_project.FirestoreDatabase.DiaryEntry;
import oops.oops_project.R;

public class DiaryFragment extends Fragment implements View.OnClickListener
{
    TextView dateTextView;
    ImageButton leftButton, rightButton, calendarButton;
    FrameLayout fragment_container;
    Date currentDate;
    boolean firstFragment = true;

    private final String DIARY_PATH = "users" + "/" + Data.DID + "/" + "Diary";
    private final String KEY_TITLE = "title", KEY_DESC = "desc";

    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_diary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        currentDate = new Date();

        dateTextView = view.findViewById(R.id.diary_date);
        leftButton = view.findViewById(R.id.diary_left_button);
        rightButton = view.findViewById(R.id.diary_right_button);
        calendarButton = view.findViewById(R.id.diary_calendar_button);
        fragment_container = view.findViewById(R.id.diary_fragment_container);

        setDate(currentDate);
        firstFragment = false;

        leftButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);
        calendarButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        int viewId = v.getId();
        if(viewId == R.id.diary_left_button)
            previousDay();
        else if(viewId == R.id.diary_right_button)
            nextDay();
        else if(viewId == R.id.diary_calendar_button)
            choseDate();
    }

    private void setDate(Date currentDate)
    {
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(currentDate);
        String day = new SimpleDateFormat("EEEE", Locale.getDefault()).format(currentDate);

        SpannableStringBuilder ssb = new SpannableStringBuilder(date);
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new AbsoluteSizeSpan(20, true), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append("\n");
        ssb.append(day);
        ssb.setSpan(new AbsoluteSizeSpan(12, true), ssb.length()-day.length(), ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        dateTextView.setText(ssb);

        switchFragment(date);
    }

    private void nextDay()
    {
        currentDate = new Date(currentDate.getTime() + MILLIS_IN_A_DAY);
        setDate(currentDate);
    }

    private void previousDay()
    {
        currentDate = new Date(currentDate.getTime() - MILLIS_IN_A_DAY);
        setDate(currentDate);
    }

    private void choseDate()
    {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        String dateString = String.format(Locale.getDefault(), "%02d", dayOfMonth) +
                                String.format(Locale.getDefault(),"%02d", month+1) + year;
                        try {
                            currentDate = new SimpleDateFormat("ddMMyyyy", Locale.getDefault()).parse(dateString);
                            setDate(currentDate);
                        }
                        catch(ParseException e){}
                    }
                }, year, month, day);
        datePicker.show();
    }

    private void switchFragment(String date)
    {

        ProgressDialog dialog=new ProgressDialog(getContext());
        dialog.setMessage("Connecting with database...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();

        DashboardActivity.db().document((DIARY_PATH + "/" + date)).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        String title = "", desc = "";

                        if(documentSnapshot.exists())
                        {
                            title = documentSnapshot.getString(KEY_TITLE);
                            desc = documentSnapshot.getString(KEY_DESC);
                        }

                        DiaryContentFragment childFragment = new DiaryContentFragment(title, desc);
                        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                        if(firstFragment)
                            ft.add(R.id.diary_fragment_container, childFragment, "Diary Content");
                        else
                            ft.replace(R.id.diary_fragment_container, childFragment, "Diary Content");
                        ft.commit();

                        dialog.hide();
                    }
                });
    }

    public void setEditmode()
    {
        DiaryContentFragment diaryContentFragment = (DiaryContentFragment) getChildFragmentManager().findFragmentByTag("Diary Content");
        diaryContentFragment.enableEdit();
    }

    public void saveContent(boolean discard)
    {
        DiaryContentFragment diaryContentFragment = (DiaryContentFragment) getChildFragmentManager().findFragmentByTag("Diary Content");
        if(!discard)
            diaryContentFragment.updateContent();
        diaryContentFragment.disableEdit();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(currentDate);

        DashboardActivity.db().document((DIARY_PATH + "/" +date))
                .set(new DiaryEntry(diaryContentFragment.getTitle(), diaryContentFragment.getDesc()))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        switchFragment(date);
                        Toast.makeText(getContext(), "Saved!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Doesn't work", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}