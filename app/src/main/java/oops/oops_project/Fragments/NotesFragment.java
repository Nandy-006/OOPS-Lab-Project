package oops.oops_project.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oops.oops_project.Activities.NavigatingDashboardActivity;
import oops.oops_project.Adapters.NotesAdapter;
import oops.oops_project.R;

public class NotesFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RecyclerView recycler = (RecyclerView) inflater.inflate(R.layout.fragment_notes, container, false);

        NotesAdapter adapter = new NotesAdapter();
        recycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);

        adapter.setListener(new NotesAdapter.Listener() {
            @Override
            public void onClick(int position)
            {
                Log.d("Listener", "Entering on click. Position: " + position);
                ((NavigatingDashboardActivity) getActivity()).showNote(position);
            }
        });

        return recycler;
    }
}