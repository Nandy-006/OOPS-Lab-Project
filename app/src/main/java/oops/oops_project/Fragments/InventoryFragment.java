package oops.oops_project.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import oops.oops_project.Activities.CategoryActivity;
import oops.oops_project.Activities.DashboardActivity;
import oops.oops_project.Activities.NavigatingDashboardActivity;
import oops.oops_project.Adapters.FirebaseCategoryAdapter;
import oops.oops_project.Adapters.FirebaseNotesAdapter;
import oops.oops_project.FirestoreDatabase.Category;
import oops.oops_project.FirestoreDatabase.Data;
import oops.oops_project.R;

import static oops.oops_project.Activities.DashboardActivity.getUID;

public class InventoryFragment extends Fragment
{
    public final static String INVENTORY_PATH = "users" + "/" + getUID() + "/" + "Inventory";
    FirebaseCategoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RecyclerView recycler =  (RecyclerView) inflater.inflate(R.layout.fragment_inventory, container, false);
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
        Query query = DashboardActivity.db().collection(INVENTORY_PATH).orderBy("date", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Category> options = new FirestoreRecyclerOptions.Builder<Category>()
                .setQuery(query, Category.class)
                .build();

        adapter = new FirebaseCategoryAdapter(options);

        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(adapter);

        adapter.setListener(new FirebaseCategoryAdapter.CategoryListener() {
            @Override
            public void onClick(DocumentSnapshot documentSnapshot, int position) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                String path = adapter.getDoc(position).getPath();
                intent.putExtra(CategoryActivity.PATH, path);
                getActivity().startActivity(intent);
            }
        });
    }

    public FirebaseCategoryAdapter getAdapter() { return adapter;}
}