package edu.northeastern.numad23sp_xiaoqingmeng;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class LinkCollectorActivity extends AppCompatActivity {

    private ArrayList<Link> linkItemList;
    private AlertDialog inputAlertDialog;

    private EditText linkName;
    private EditText linkUrl;
    private RecyclerView linkRecyclerView;
    private LinkAdapter linkCollectorViewAdapter;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        linkItemList = new ArrayList<>();

        init(savedInstanceState);

        FloatingActionButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(view -> addLink());

        createInputAlertDialog();
        createRecyclerView();
        linkCollectorViewAdapter.setOnLinkClickListener(position -> linkItemList.get(position).onItemClick(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getLayoutPosition();
                linkItemList.remove(position);
                linkCollectorViewAdapter.notifyDataSetChanged();
            }
        });
        itemTouchHelper.attachToRecyclerView(linkRecyclerView);
    }

    //Handling Orientation Changes on Android
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        int size = linkItemList == null? 0 : linkItemList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        //Need to generate unique key for each item
        for(int i=0; i<size; i++){
            outState.putString(KEY_OF_INSTANCE + i+ "0", linkItemList.get(i).getName());
            outState.putString(KEY_OF_INSTANCE + i+ "1", linkItemList.get(i).getUrl());
        }
        super.onSaveInstanceState(outState);
    }

    private void init(Bundle savedInstanceState) {
        initialItemData(savedInstanceState);
        createRecyclerView();
    }

    private void initialItemData(Bundle savedInstanceState){

        if(savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)){
            if(linkItemList == null || linkItemList.size() == 0){
                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);

                for(int i=0; i<size; i++){
                    String name = savedInstanceState.getString(KEY_OF_INSTANCE+i+"0");
                    String url = savedInstanceState.getString(KEY_OF_INSTANCE+i+"1");

                    Link link = new Link(name, url);
                    linkItemList.add(link);
                }
            }
        }
    }

    public void createRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        linkRecyclerView = findViewById(R.id.recycler_view);
        linkRecyclerView.setHasFixedSize(true);
        linkCollectorViewAdapter = new LinkAdapter(linkItemList);

        linkRecyclerView.setAdapter(linkCollectorViewAdapter);
        linkRecyclerView.setLayoutManager(layoutManager);
    }

    public void createInputAlertDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.activity_link_input, null);

        linkName = view.findViewById(R.id.link_name_input);
        linkUrl = view.findViewById(R.id.link_url_input);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(getString(R.string.Add),
                        (dialog, id) -> {
                            Link linkItem = new Link(linkName.getText().toString(), linkUrl.getText().toString());
                            if (linkItem.isValid()) {
                                linkItemList.add(0, linkItem);
                                linkCollectorViewAdapter.notifyDataSetChanged();
                                Snackbar.make(linkRecyclerView, getString(R.string.link_add_success), Snackbar.LENGTH_LONG).show();
                            } else {
                                Snackbar.make(linkRecyclerView, getString(R.string.link_invalid), Snackbar.LENGTH_LONG).show();
                            }
                        })
                .setNegativeButton(getString(R.string.Cancel),
                        (dialog, id) -> dialog.cancel());

        inputAlertDialog = alertDialogBuilder.create();
    }

    // add the user input into the list and show a snack-bar
    private void addLink() {
        linkName.getText().clear();
        linkUrl.setText(getString(R.string.Http));
        linkName.requestFocus();
        inputAlertDialog.show();
    }
}

