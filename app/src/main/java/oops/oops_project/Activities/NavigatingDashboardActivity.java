package oops.oops_project.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import oops.oops_project.Fragments.*;
import oops.oops_project.R;

public class NavigatingDashboardActivity extends AppCompatActivity
{
    private Fragment fragment = null;
    private String cur_category = "";
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigating_dashboard);

        fab = findViewById(R.id.fab);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        Listener listener = new Listener();
        bottomNav.setOnNavigationItemSelectedListener(listener);
    }

    public class Listener implements BottomNavigationView.OnNavigationItemSelectedListener
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            int id = item.getItemId(), fabImageResource;
            String category;

            if(id == R.id.bnav_inventory) { category = "Inventory"; fragment = new InventoryFragment(); fabImageResource = R.drawable.baseline_add_24;}
            else if(id == R.id.bnav_tasks) { category = "Tasks"; fragment = new TasksFragment(); fabImageResource = R.drawable.baseline_add_task_24;}
            else if(id == R.id.bnav_notes) { category = "Notes"; fragment = new NotesFragment(); fabImageResource = R.drawable.baseline_note_add_white_24;}
            else if(id == R.id.bnav_diary) { category = "Diary"; fragment = new DiaryFragment(); fabImageResource = R.drawable.baseline_create_24;}
            else { category = ""; fragment = null; fabImageResource = 0;}

            if(!category.equals(cur_category))
            {
                fab.setImageResource(fabImageResource);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if(cur_category.equals(""))
                    ft.add(R.id.fragment_container, fragment);
                else
                    ft.replace(R.id.fragment_container, fragment);
                ft.addToBackStack(null);
                ft.commit();

                cur_category = category;
            }

            return true;
        }
    }
}