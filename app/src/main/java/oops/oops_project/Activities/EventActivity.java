package oops.oops_project.Activities;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;

import oops.oops_project.FirestoreDatabase.Event;
import oops.oops_project.R;

import static oops.oops_project.Activities.DashboardActivity.db;

public class EventActivity extends AppCompatActivity
{
    public static final String PATH = "path";
    private String EVENTS_PATH;

    String doc;
    Event cur_event;
    boolean isFABOpen = false;

    FloatingActionButton editEventFab, deleteEventFab, menuFab;
    LinearLayout editEventLayout, deleteEventLayout;
    View fabBGLayout;

    TextView titleText, descText, deadlineText;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        doc = getIntent().getStringExtra(PATH);

        titleText = findViewById(R.id.activity_task_event_name);
        descText = findViewById(R.id.activity_task_description);
        deadlineText = findViewById(R.id.activity_task_deadline);
        checkBox = findViewById(R.id.activity_task_reminder);

        editEventFab = findViewById(R.id.edit_event_fab);
        deleteEventFab = findViewById(R.id.delete_event_fab);
        menuFab = findViewById(R.id.category_fab);

        editEventLayout = findViewById(R.id.edit_event_fabLayout);
        deleteEventLayout = findViewById(R.id.delete_event_fabLayout);
        fabBGLayout = findViewById(R.id.fabBGLayout);

        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFABMenu();
            }
        });

        db().document(doc).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Event event = documentSnapshot.toObject(Event.class);

                        String title = event.getName();
                        String desc = event.getDescription();
                        String deadline = event.getFormattedDatetime();
                        boolean state = event.isWantReminder();

                        titleText.setText(title);
                        descText.setText(desc);
                        deadlineText.setText(deadline);
                        checkBox.setChecked(state);

                        cur_event = event;
                    }
                });
    }

    private void closeFABMenu()
    {
        isFABOpen = false;
        fabBGLayout.setVisibility(View.GONE);
        menuFab.animate().rotation(0);
        editEventLayout.animate().translationY(0);
        deleteEventLayout.animate().translationY(0);
        deleteEventLayout.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    editEventLayout.setVisibility(View.GONE);
                    deleteEventLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private void showFABMenu()
    {
        isFABOpen = true;
        editEventLayout.setVisibility(View.VISIBLE);
        deleteEventLayout.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);
        menuFab.animate().rotationBy(180);
        deleteEventLayout.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        editEventLayout.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
    }

    public void onClickMenuFab(View view)
    {
        if (!isFABOpen) { showFABMenu();}
        else { closeFABMenu();}
    }

    @Override
    public void onBackPressed() {
        if (isFABOpen) {
            closeFABMenu();
        } else {
            super.onBackPressed();
        }
    }

    public void deleteEvent(View view)
    {
        MaterialAlertDialogBuilder deleteEventDialog = new MaterialAlertDialogBuilder((this));
        deleteEventDialog.setTitle("Are you sure you want to delete this category?");
        deleteEventDialog.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        deleteEventDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        deleteEventDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db().document(doc).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Event deleted!", Toast.LENGTH_SHORT).show();
                                closeFABMenu();
                                finish();
                            }
                        });
            }
        });

        deleteEventDialog.show();
    }

    public void editEvent(View view)
    {
        Intent intent = new Intent(this, AddEventActivity.class);
        intent.putExtra("DATE", cur_event.getFormattedDate());
        intent.putExtra("TIME", cur_event.getFormattedTime());
        intent.putExtra("NAME", cur_event.getName());
        intent.putExtra("DESC", cur_event.getDescription());
        intent.putExtra("STATE", cur_event.isWantReminder());
        startActivity(intent);
    }
}