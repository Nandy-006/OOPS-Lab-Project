package oops.oops_project.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import oops.oops_project.FirestoreDatabase.Event;
import oops.oops_project.R;

public class FirebaseEventsAdapter extends FirestoreRecyclerAdapter<Event, FirebaseEventsAdapter.EventHolder>
{
    private EventListener listener;

    public FirebaseEventsAdapter(@NonNull FirestoreRecyclerOptions<Event> options) { super(options);}

    @Override
    protected void onBindViewHolder(@NonNull EventHolder holder, int position, @NonNull Event model)
    {
        MaterialCardView cardView = holder.cardView;
        MaterialTextView titleText = cardView.findViewById(R.id.event_page_item_layout_name);
        titleText.setText(model.getName());
        MaterialTextView timeText = cardView.findViewById(R.id.event_page_item_layout_time);
        timeText.setText(model.getFormattedDatetime());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position != RecyclerView.NO_POSITION && listener != null)
                    listener.onClick(getSnapshots().getSnapshot(position), position);
            }
        });
    }

    public DocumentReference getDoc(int position) {return getSnapshots().getSnapshot(position).getReference();}

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        MaterialCardView cardView = (MaterialCardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_page_item_layout, parent, false);
        return new EventHolder(cardView);
    }

    class EventHolder extends RecyclerView.ViewHolder
    {
        public MaterialCardView cardView;

        public EventHolder(MaterialCardView view)
        {
            super(view);
            cardView = view;
        }
    }

    public interface EventListener { void onClick(DocumentSnapshot documentSnapshot, int position);}
    public void setListener(EventListener listener) {this.listener = listener;}
}

