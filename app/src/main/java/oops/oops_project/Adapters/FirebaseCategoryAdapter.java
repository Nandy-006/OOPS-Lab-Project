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

import oops.oops_project.FirestoreDatabase.Category;
import oops.oops_project.R;

public class FirebaseCategoryAdapter extends FirestoreRecyclerAdapter<Category, FirebaseCategoryAdapter.CategoryHolder>
{
    private CategoryListener listener;

    public FirebaseCategoryAdapter(@NonNull FirestoreRecyclerOptions<Category> options) { super(options);}

    @Override
    protected void onBindViewHolder(@NonNull CategoryHolder holder, int position, @NonNull Category model)
    {
        MaterialCardView cardView = holder.cardView;
        MaterialTextView titleText = cardView.findViewById(R.id.category_card_title);
        titleText.setText(model.getTitle());

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
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        MaterialCardView cardView = (MaterialCardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card_layout, parent, false);
        return new CategoryHolder(cardView);
    }

    class CategoryHolder extends RecyclerView.ViewHolder
    {
        public MaterialCardView cardView;

        public CategoryHolder(MaterialCardView view)
        {
            super(view);
            cardView = view;
        }
    }

    public interface CategoryListener { void onClick(DocumentSnapshot documentSnapshot, int position);}
    public void setListener(CategoryListener listener) { this.listener = listener;}
}
