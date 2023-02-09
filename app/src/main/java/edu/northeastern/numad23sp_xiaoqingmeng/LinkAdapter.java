package edu.northeastern.numad23sp_xiaoqingmeng;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LinkAdapter extends RecyclerView.Adapter<LinkViewHolder> {
    private ArrayList<Link> list;
    private ItemClickListener listener;

    public LinkAdapter(ArrayList<Link> list) {
        this.list = list;
    }

    // WHY is it GREYED OUT?
    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public LinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_link, parent, false);
        return new LinkViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(LinkViewHolder holder, int position) {
        Link link = list.get(position);
        holder.nameTextView.setText(link.getName());
        holder.urlTextView.setText(link.getUrl());
        // Bind the link data to the viewholder
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position); //TODO: link?
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

/*    public void addLink(Link link) {
        list.add(link);
        notifyDataSetChanged();
    }

    public void editLink(int position, Link link) {
        list.set(position, link);
        notifyDataSetChanged();
    }

    public void removeLink(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }*/
}

