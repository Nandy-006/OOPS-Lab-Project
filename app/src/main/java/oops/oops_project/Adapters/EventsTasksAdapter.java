package oops.oops_project.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Tasks;
import com.google.android.material.card.MaterialCardView;

import org.w3c.dom.Text;

import java.util.List;

import oops.oops_project.Database.Category;
import oops.oops_project.Database.EventTasks;
import oops.oops_project.R;

public class EventsTasksAdapter extends RecyclerView.Adapter<EventsTasksAdapter.ViewHolder>
{
    private EventsTasksListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewEventName;
        public TextView textViewTimeRemaining;
        public MaterialCardView cardView;

        public ViewHolder(@NonNull MaterialCardView itemView) {
            super(itemView);
            cardView = itemView;
            textViewEventName = (TextView) itemView.findViewById(R.id.event_page_item_layout_name);
            textViewTimeRemaining = (TextView) itemView.findViewById(R.id.event_page_item_layout_time_remaining);
        }
    }

    public interface EventsTasksListener { void onClick(int position);}
    public void setListener(EventsTasksAdapter.EventsTasksListener listener) { this.listener = listener;}

    private List<EventTasks> mTasks;

    public EventsTasksAdapter(List<EventTasks> tasks) {
        mTasks = tasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
