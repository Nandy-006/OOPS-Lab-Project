package oops.oops_project.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import oops.oops_project.FirestoreDatabase.Note;
import oops.oops_project.R;

public class FirebaseNotesAdapter extends FirestoreRecyclerAdapter<Note, FirebaseNotesAdapter.NoteHolder>
{
    private NoteListener listener;

    public FirebaseNotesAdapter(@NonNull FirestoreRecyclerOptions<Note> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Note model)
    {
        MaterialCardView cardView = holder.cardView;
        TextView cardTitle = cardView.findViewById(R.id.note_card_text);
        cardTitle.setText(model.getTitle());

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
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        MaterialCardView cardView = (MaterialCardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card, parent, false);
        return new NoteHolder(cardView);
    }

    class NoteHolder extends RecyclerView.ViewHolder
    {
        public MaterialCardView cardView;

        public NoteHolder(MaterialCardView view)
        {
            super(view);
            cardView = view;
        }
    }

    public interface NoteListener { void onClick(DocumentSnapshot documentSnapshot, int position);}
    public void setListener(FirebaseNotesAdapter.NoteListener listener) { this.listener = listener;}
}
