package oops.oops_project.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Stack;

import oops.oops_project.Fragments.*;
import oops.oops_project.R;

public class NavigatingDashboardActivity extends AppCompatActivity
{
    private String cur_category = "";
    private BottomNavigationView bottomNav;
    private FloatingActionButton fab;
    private Listener listener;
    private final Stack<BnavItem> bnavBackStack = new Stack<>();
    public static final String CHOICE = "choice";

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
        Fragment fragment;

        if(id == R.id.bnav_inventory) { category = "Inventory"; fragment = new InventoryFragment(); fabImageResource = R.drawable.baseline_add_24;}
        else if(id == R.id.bnav_tasks) { category = "Tasks"; fragment = new TasksFragment(); fabImageResource = R.drawable.baseline_add_task_24;}
        else if(id == R.id.bnav_notes) { category = "Notes"; fragment = new NotesFragment(); fabImageResource = R.drawable.baseline_note_add_white_24;}
        else { category = "Diary"; fragment = new DiaryFragment(); fabImageResource = R.drawable.baseline_create_24;}

        if(!category.equals(cur_category))
        {

            fab.setImageResource(fabImageResource);
            getSupportActionBar().setTitle(category);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(cur_category.equals(""))
                ft.add(R.id.fragment_container, fragment);
            else
                ft.replace(R.id.fragment_container, fragment);
            if(!firstTime)
                ft.addToBackStack(null);
            else
                bottomNav.setSelectedItemId(id);
            ft.commit();

            bnavBackStack.push(new BnavItem(id, fabImageResource));

            cur_category = category;
        }
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

    @Override
    public void onBackPressed()
    {
        if(bnavBackStack.size() > 1)
        {
            bottomNav.setOnNavigationItemSelectedListener(null);
            bnavBackStack.pop();
            bottomNav.setSelectedItemId(bnavBackStack.peek().item);
            fab.setImageResource(bnavBackStack.peek().fabImage);
            bottomNav.setOnNavigationItemSelectedListener(listener);
        }
        super.onBackPressed();
    }

    private static class BnavItem
    {
        int item, fabImage;

        BnavItem(int item, int fabImage)
        {
            this.fabImage = fabImage;
            this.item = item;
        }
    }
}