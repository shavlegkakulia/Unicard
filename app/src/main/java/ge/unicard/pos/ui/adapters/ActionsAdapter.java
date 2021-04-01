package ge.unicard.pos.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ge.unicard.pos.R;
import ge.unicard.pos.model.ActionItem;
import ge.unicard.pos.ui.widgets.CButton;

/**
 * Created by Akaki on 10/29/18.
 */
public class ActionsAdapter extends RecyclerView.Adapter<ActionsAdapter.LauncherViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final List<ActionItem> mItems;
    public static Integer color;
    public ActionsAdapter(@NonNull Context context,
                          @NonNull List<ActionItem> items) {
        mLayoutInflater = LayoutInflater.from(context);
        mItems = items;
    }

    @NonNull
    @Override
    public ActionsAdapter.LauncherViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                int viewType) {
        return new LauncherViewHolder(mLayoutInflater.inflate(R.layout.action_item_layout,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActionsAdapter.LauncherViewHolder viewHolder,
                                 int position) {
        final ActionItem item = mItems.get(position);

        viewHolder.actionButton.setBackgroundColor(item.getColor());
        viewHolder.textView.setText(item.getLabel());



            viewHolder.imageView.setImageResource(item.getImage());

        //viewHolder.actionButton.setText(item.getLabel());
        viewHolder.actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionsAdapter.color = item.getColor();
                onItemClick(item);
            }
        });
    }

    protected void onItemClick(ActionItem item) {
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static final class LauncherViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.action_btn)
        ConstraintLayout actionButton;

        @BindView(R.id.image_menu)
        ImageView imageView;

        @BindView(R.id.text_menu)
        TextView textView;

        public LauncherViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
