package edu.northeastern.numad23sp_xiaoqingmeng;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LinkAdapter extends RecyclerView.Adapter<LinkViewHolder> {
    private ArrayList<Link> linkItemlist;
    private LinkClickListener linkClickListener;

    public LinkAdapter(ArrayList<Link> linkItemlist) {
        this.linkItemlist = linkItemlist;
    }

    public void setOnLinkClickListener(LinkClickListener linkClickListener) {
        this.linkClickListener = linkClickListener;
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_link, parent, false);
        return new LinkViewHolder(view, linkClickListener);
    }

    @Override
    public void onBindViewHolder(LinkViewHolder holder, int position) {
        Link currentLink = linkItemlist.get(position);
        holder.nameTextView.setText(currentLink.getName());
        holder.urlTextView.setText(currentLink.getUrl());
    }

    @Override
    public int getItemCount() {
        return linkItemlist.size();
    }

    public interface LinkClickListener {
        void onLinkClick(int position); // Context
    }
}

