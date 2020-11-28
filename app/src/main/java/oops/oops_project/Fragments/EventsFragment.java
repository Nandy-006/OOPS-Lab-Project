package oops.oops_project.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

import oops.oops_project.Activities.EventActivity;
import oops.oops_project.Adapters.FirebaseEventsAdapter;
import oops.oops_project.FirestoreDatabase.Data;
import oops.oops_project.FirestoreDatabase.Event;
import oops.oops_project.R;

import static oops.oops_project.Activities.DashboardActivity.db;

public class EventsFragment extends Fragment
{
    public final static String EVENTS_PATH = "users" + "/" + Data.DID + "/" + "Events";
    FirebaseEventsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        updateDatabase();
        RecyclerView recycler = (RecyclerView) inflater.inflate(R.layout.fragment_events, container, false);
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
        Query query = db().collection(EVENTS_PATH).orderBy("datetime", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Event> options = new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(query, Event.class)
                .build();

        adapter = new FirebaseEventsAdapter(options);

        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(adapter);

        adapter.setListener(new FirebaseEventsAdapter.EventListener() {
            @Override
            public void onClick(DocumentSnapshot documentSnapshot, int position)
            {
                Intent intent = new Intent(getContext(), EventActivity.class);
                String path = adapter.getDoc(position).getPath();
                intent.putExtra(EventActivity.PATH, path);
                getActivity().startActivity(intent);
            }
        });
    }

    private void updateDatabase()
    {
        Date cur_date = new Date();
        db().collection(EventActivity.PATH).whereLessThan("datetime", cur_date).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                            Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                            documentSnapshot.getReference().delete();
                        }
                    }
                });
    }

}