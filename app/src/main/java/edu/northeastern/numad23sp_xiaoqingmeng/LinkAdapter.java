package edu.northeastern.numad23sp_xiaoqingmeng;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.LinkViewHolder> {
    private List<Link> links;

    public LinkAdapter(LinkCollectorActivity linkCollectorActivity, List<Link> links) {
        this.links = links;
    }

    @Override
    public LinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_link, parent, false);
        return new LinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LinkViewHolder holder, int position) {
        Link link = links.get(position);
        holder.nameTextView.setText(link.getName());
        holder.urlTextView.setText(link.getUrl());
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    public void addLink(Link link) {
        links.add(link);
        notifyDataSetChanged();
    }

    public void editLink(int position, Link link) {
        links.set(position, link);
        notifyDataSetChanged();
    }

    public void removeLink(int position) {
        links.remove(position);
        notifyDataSetChanged();
    }

    static class LinkViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView urlTextView;

        public LinkViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_name);
            urlTextView = itemView.findViewById(R.id.item_url);
        }
    }
}

