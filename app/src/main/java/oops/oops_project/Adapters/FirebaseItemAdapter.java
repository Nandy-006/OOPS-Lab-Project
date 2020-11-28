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

import de.hdodenhof.circleimageview.CircleImageView;
import oops.oops_project.FirestoreDatabase.Item;
import oops.oops_project.R;

public class FirebaseItemAdapter extends FirestoreRecyclerAdapter<Item, FirebaseItemAdapter.ItemHolder>
{
    private ItemListener listener;

    public FirebaseItemAdapter(@NonNull FirestoreRecyclerOptions<Item> options) { super(options);}

    @Override
    protected void onBindViewHolder(@NonNull ItemHolder holder, int position, @NonNull Item model)
    {
        MaterialCardView cardView = holder.cardView;

        CircleImageView imgView = cardView.findViewById(R.id.item_circle_image_view);
        imgView.setImageResource(model.getImage());
        TextView titleText = cardView.findViewById(R.id.item_name);
        titleText.setText(model.getTitle());
        TextView descText = cardView.findViewById(R.id.item_desc);
        descText.setText(model.getDescriprition());
        TextView quantityText = cardView.findViewById(R.id.item_quantity_val);
        quantityText.setText(String.valueOf(model.getQuantity()));

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
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        MaterialCardView cardView = (MaterialCardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ItemHolder(cardView);
    }

    class ItemHolder extends RecyclerView.ViewHolder
    {
        public MaterialCardView cardView;

        public ItemHolder(MaterialCardView view)
        {
            super(view);
            cardView = view;
        }
    }

    public interface ItemListener { void onClick(DocumentSnapshot documentSnapshot, int position);}
    public void setListener(ItemListener listener) {this.listener = listener;}

}
