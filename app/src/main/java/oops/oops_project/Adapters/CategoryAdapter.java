/*
package oops.oops_project.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

import oops.oops_project.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private CategoryListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public MaterialCardView cardView;

        public ViewHolder(MaterialCardView view)
        {
            super(view);
            cardView = view;
        }
    }

    public interface CategoryListener { void onClick(int position);}
    public void setListener(CategoryListener listener) { this.listener = listener;}

    private List<Category> mCategories;

    public CategoryAdapter(List<Category> categories) {
        mCategories = categories;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        MaterialCardView cardView = (MaterialCardView) inflater.inflate(R.layout.category_card_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(cardView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Category category = mCategories.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.textViewCategoryName;
        textView.setText(category.getName());
        TextView textView1 = holder.textViewCategoryDescription;
        textView1.setText(category.getDescription());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mCategories.size();
    }

}
*/