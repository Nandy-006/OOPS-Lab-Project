package oops.oops_project.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Stack;

import oops.oops_project.Adapters.FirebaseCategoryAdapter;
import oops.oops_project.Adapters.FirebaseNotesAdapter;
import oops.oops_project.Dialogs.AddCategoryDialog;
import oops.oops_project.FirestoreDatabase.Category;
import oops.oops_project.FirestoreDatabase.Event;
import oops.oops_project.FirestoreDatabase.Note;
import oops.oops_project.Fragments.DiaryFragment;
import oops.oops_project.Fragments.InventoryFragment;
import oops.oops_project.Fragments.NotesFragment;
import oops.oops_project.Fragments.EventsFragment;
import oops.oops_project.R;

import static oops.oops_project.Activities.DashboardActivity.db;

public class NavigatingDashboardActivity extends AppCompatActivity  implements AddCategoryDialog.AddCategoryDialogListener
{
    private String cur_category = "";
    private boolean diaryEditMode = false;
    private BottomNavigationView bottomNav;
    private FloatingActionButton fab;
    private Listener listener;
    private final Stack<BnavItem> bnavBackStack = new Stack<>();
    public static final String CHOICE = "choice";
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigating_dashboard);
        MaterialToolbar toolbar = findViewById(R.id.toolbar_nav_dashboard);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = findViewById(R.id.fab);
        bottomNav = findViewById(R.id.bottom_nav);

        int id = getIntent().getIntExtra(CHOICE, R.id.bnav_inventory);
        setFragment(id, true);

        listener = new Listener();
        bottomNav.setOnNavigationItemSelectedListener(listener);
    }

    private void setFragment(int id, boolean firstTime)
    {
        String category;
        int fabImageResource;

        if(id == R.id.bnav_inventory) { category = "Inventory"; fragment = new InventoryFragment(); fabImageResource = R.drawable.baseline_add_24;}
        else if(id == R.id.bnav_tasks) { category = "Tasks"; fragment = new EventsFragment(); fabImageResource = R.drawable.baseline_add_task_24;}
        else if(id == R.id.bnav_notes) { category = "Notes"; fragment = new NotesFragment(); fabImageResource = R.drawable.baseline_note_add_white_24;}
        else { category = "Diary"; fragment = new DiaryFragment(); fabImageResource = R.drawable.baseline_create_24;}

        if(!category.equals(cur_category))
        {

            fab.setImageResource(fabImageResource);
            getSupportActionBar().setTitle(category);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(cur_category.equals(""))
                ft.add(R.id.fragment_container, fragment, category);
            else
                ft.replace(R.id.fragment_container, fragment, category);
            if(!firstTime)
                ft.addToBackStack(null);
            else
                bottomNav.setSelectedItemId(id);
            ft.commit();

            bnavBackStack.push(new BnavItem(id, fabImageResource));

            cur_category = category;
        }
    }

    public static FirebaseCategoryAdapter categoryAdapter;
    @Override
    public void applyTexts(String title, String description)
    {
        categoryAdapter = ((InventoryFragment) fragment).getAdapter();
        db().collection(InventoryFragment.INVENTORY_PATH)
                .add(new Category(title, description, new Date()));
    }

    public class Listener implements BottomNavigationView.OnNavigationItemSelectedListener
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            int id = item.getItemId();
            setFragment(id, false);
            return true;
        }
    }

    public void onClickFAB(View view)
    {
        if(cur_category == "Inventory")
            openDialog();
        else if(cur_category == "Tasks")
        {
            Intent intent = new Intent(this, AddEventActivity.class);
            startActivity(intent);
        }
        else if(cur_category.equals("Notes"))
            newNote();
        else if(cur_category.equals("Diary"))
        {
            if(diaryEditMode)
                saveDiary(false);
            else
                editDiary();
        }
    }

    public void saveDiary(boolean discard)
    {
        diaryEditMode = false;
        bnavBackStack.pop();
        fab.setImageResource(bnavBackStack.peek().fabImage);

        DiaryFragment diaryFragment = (DiaryFragment) getSupportFragmentManager().findFragmentByTag("Diary");
        diaryFragment.saveContent(discard);
    }

    public void editDiary()
    {
        diaryEditMode = true;
        fab.setImageResource(R.drawable.baseline_save_24);
        bnavBackStack.push(new BnavItem(R.id.bnav_diary, R.drawable.baseline_save_24));

        DiaryFragment diaryFragment = (DiaryFragment) getSupportFragmentManager().findFragmentByTag("Diary");
        diaryFragment.setEditmode();
    }

    public static FirebaseNotesAdapter notesAdapter;
    public void showNote(DocumentReference reference)
    {
        notesAdapter = ((NotesFragment) fragment).getAdapter();

        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Note savedNote = documentSnapshot.toObject(Note.class);

                Intent intent = new Intent(getApplicationContext(), NoteContentActivity.class);
                intent.putExtra(NoteContentActivity.TITLE, savedNote.getTitle());
                intent.putExtra(NoteContentActivity.CONTENT, savedNote.getContent());
                intent.putExtra(NoteContentActivity.REF, reference.getPath());
                startActivity(intent);
            }
        });
    }

    public void newNote()
    {
        final EditText input = new EditText(this);

        MaterialAlertDialogBuilder newNoteDialog = new MaterialAlertDialogBuilder(this);
        newNoteDialog.setTitle("New Note");
        newNoteDialog.setMessage("Enter title for the note:");
        newNoteDialog.setView(input);
        newNoteDialog.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        newNoteDialog.setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String title = input.getText().toString();
                if(!title.equals(""))
                {
                    db().collection(NotesFragment.NOTES_PATH)
                            .add(new Note(title, "", new Date()))
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    showNote(documentReference);
                                }
                            });
                    Toast.makeText(getApplicationContext(), "Note added!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Empty title!", Toast.LENGTH_SHORT).show();
            }
        });
        newNoteDialog.show();
    }

    @Override
    public void onBackPressed()
    {
        if(diaryEditMode)
        {
            DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    if(which == DialogInterface.BUTTON_NEGATIVE)
                        saveDiary(true);
                    else if(which == DialogInterface.BUTTON_POSITIVE)
                        saveDiary(false);
                }
            };

            MaterialAlertDialogBuilder discardDialog = new MaterialAlertDialogBuilder(this);
            discardDialog.setTitle("Save changes?");
            discardDialog.setNeutralButton("CANCEL", dialogListener);
            discardDialog.setNegativeButton("DISCARD", dialogListener);
            discardDialog.setPositiveButton("SAVE", dialogListener);
            discardDialog.show();
        }
        else
        {
            if (bnavBackStack.size() > 1)
            {
                bottomNav.setOnNavigationItemSelectedListener(null);
                bnavBackStack.pop();
                bottomNav.setSelectedItemId(bnavBackStack.peek().item);
                fab.setImageResource(bnavBackStack.peek().fabImage);
                bottomNav.setOnNavigationItemSelectedListener(listener);
            }
            super.onBackPressed();
        }
    }

    static class BnavItem
    {
        int item, fabImage;

        BnavItem(int item, int fabImage)
        {
            this.fabImage = fabImage;
            this.item = item;
        }
    }

    public void openDialog()
    {
        AddCategoryDialog addCategoryDialog = new AddCategoryDialog();
        addCategoryDialog.show(getSupportFragmentManager(), "add category dialog");
    }

}