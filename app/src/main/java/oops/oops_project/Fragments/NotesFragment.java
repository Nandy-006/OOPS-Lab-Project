package oops.oops_project.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import oops.oops_project.Activities.DashboardActivity;
import oops.oops_project.Activities.NavigatingDashboardActivity;
import oops.oops_project.Adapters.FirebaseNotesAdapter;
import oops.oops_project.FirestoreDatabase.Data;
import oops.oops_project.FirestoreDatabase.Note;
import oops.oops_project.R;

import static oops.oops_project.Activities.DashboardActivity.db;

public class NotesFragment extends Fragment
{
    public final static String NOTES_PATH = "users" + "/" + Data.DID + "/" + "Notes";
    FirebaseNotesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RecyclerView recycler = (RecyclerView) inflater.inflate(R.layout.fragment_notes, container, false);
        setUpRecyclerView(recycler);
        return recycler;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void setUpRecyclerView(RecyclerView recycler)
    {
        Query query = db().collection(NOTES_PATH).orderBy("date", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class)
                .build();

        adapter = new FirebaseNotesAdapter(options);

        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(adapter);

        adapter.setListener(new FirebaseNotesAdapter.NoteListener() {
            @Override
            public void onClick(DocumentSnapshot documentSnapshot, int position) {
                ((NavigatingDashboardActivity) getActivity()).showNote(adapter.getDoc(position));
            }
        });
    }

    public FirebaseNotesAdapter getAdapter(){ return adapter;}
}