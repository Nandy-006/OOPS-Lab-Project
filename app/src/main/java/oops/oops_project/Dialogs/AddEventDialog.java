/*
package oops.oops_project.Dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
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
import oops.oops_project.Fragments.DiaryContentFragment;
import oops.oops_project.R;

public class AddEventDialog extends AppCompatDialogFragment {
    private EditText editTextEventName;
    private TextView textViewSetDate;
    private Button buttonSetEventDate;
    private Button buttonSetEventTime;
    private EditText editTextEventDescription;
    private CheckBox checkBoxEventReminder;
    private AddEventDialogListener listener;

    Date currentDate;
    boolean firstFragment = true;

    private int year;
    private int month;
    private int day;

    private final String DIARY_PATH = "users" + "/" + Data.DID + "/" + "Diary";
    private final String KEY_TITLE = "title", KEY_DESC = "desc";

    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.full_screen_dialog_add_event, null);

        currentDate = new Date();

        editTextEventName = view.findViewById(R.id.event_name);
        textViewSetDate = view.findViewById(R.id.event_date_text_view);
        editTextEventDescription = view.findViewById(R.id.event_description);
        buttonSetEventDate = view.findViewById(R.id.event_set_date);
        buttonSetEventTime = view.findViewById(R.id.event_set_time);
        checkBoxEventReminder = view.findViewById(R.id.event_reminder);

        setDate(currentDate);
        firstFragment = false;

        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        //textViewSetDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" "));

        buttonSetEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseDate();
            }
        });

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
                        String title = editTextEventName.getText().toString();
                        Date event_date = new Date();
                        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a");
                        String date = event_date.toString();
                        listener.applyTexts(title, ft.format(date));
                    }
                });

        return builder.create();
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
        textViewSetDate.setText(ssb);

        switchFragment(date);
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AddEventDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement AddActivityDialogListener");
        }
    }

    public interface AddEventDialogListener {
        void applyTexts(String title, String date);
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
}
*/
