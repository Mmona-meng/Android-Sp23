package edu.northeastern.numad23sp_xiaoqingmeng;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class LinkCollectorActivity extends AppCompatActivity {

    private ArrayList<Link> linkList = new ArrayList<>();

    private RecyclerView linkRecyclerView;
    private LinkAdapter adapter;
    private FloatingActionButton addButton;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        linkRecyclerView = findViewById(R.id.recycler_view);
        adapter = new LinkAdapter(this, linkList);
        linkRecyclerView.setAdapter(adapter);
        linkRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addButton = findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLinkEntryDialog();
            }
        });

        //constraintLayout = findViewById(R.id.constraint_layout);
    }

    private void showLinkEntryDialog() {
        final EditText nameEditText = new EditText(this);
        nameEditText.setHint("Name");
        final EditText urlEditText = new EditText(this);
        urlEditText.setHint("URL");
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(nameEditText);
        linearLayout.addView(urlEditText);

        new AlertDialog.Builder(this)
                .setTitle("Add Link")
                .setView(linearLayout)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nameEditText.getText().toString();
                        String url = urlEditText.getText().toString();
                        addLink(name, url);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void addLink(String name, String url) {
        Link link = new Link(name, url);
        linkList.add(link);
        adapter.notifyDataSetChanged();

        Snackbar snackbar = Snackbar.make(constraintLayout, "Link added successfully!", Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkList.remove(link);
                adapter.notifyDataSetChanged();
                Snackbar undoSnackbar = Snackbar.make(constraintLayout, "Link removed!", Snackbar.LENGTH_SHORT);
                undoSnackbar.show();
            }
        });
        snackbar.show();
    }

}

