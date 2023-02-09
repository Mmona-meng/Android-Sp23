package edu.northeastern.numad23sp_xiaoqingmeng;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class LinkViewHolder extends RecyclerView.ViewHolder {
    public TextView nameTextView;
    public TextView urlTextView;

    public LinkViewHolder(View itemView, final ItemClickListener listener) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.item_name);
        urlTextView = itemView.findViewById(R.id.item_url);

        itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onItemClick(position); //TODO link?
                            }
                        }
            }
        });
    }
}
