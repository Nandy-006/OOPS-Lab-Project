package oops.oops_project.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import oops.oops_project.Database.Notes;
import oops.oops_project.R;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder>
{
    private Listener listener;

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public MaterialCardView cardView;

        public ViewHolder(MaterialCardView view)
        {
            super(view);
            cardView = view;
        }
    }

    public interface Listener { void onClick(int position);}
    public void setListener(Listener listener) { this.listener = listener;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       MaterialCardView cardView = (MaterialCardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card, parent, false);
       return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        MaterialCardView cardView = holder.cardView;
        TextView cardTitle = cardView.findViewById(R.id.note_card_text);
        cardTitle.setText(Notes.getTitle(position));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() { return Notes.notes.size();}
}
