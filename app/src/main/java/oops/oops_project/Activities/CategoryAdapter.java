package oops.oops_project.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import oops.oops_project.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewCategoryName;
        public TextView textViewCategoryDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewCategoryName = (TextView) itemView.findViewById(R.id.category_page_item_layout_name);
            textViewCategoryDescription = (TextView) itemView.findViewById(R.id.category_page_item_layout_description);
        }
    }

    private List<Category> mCategories;

    public CategoryAdapter(List<Category> categories) {
        mCategories = categories;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View categoryView = inflater.inflate(R.layout.category_page_item_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(categoryView);
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