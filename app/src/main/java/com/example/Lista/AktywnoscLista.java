package com.example.Lista;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AktywnoscLista extends AppCompatActivity {

    DBHelper dbHelper;
    ArrayAdapter<String> mAdapter;
    ListView listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(this);

        listItems = (ListView) findViewById(R.id.list_items);

        loadItemsList();
    }


    private void loadItemsList() {
        ArrayList<String> itemsList = dbHelper.getItemsList();
        if(mAdapter == null) {
            mAdapter = new ArrayAdapter<String>(this, R.layout.row, R.id.item_name, itemsList);
            listItems.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(itemsList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_item:
                final EditText itemEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Dodaj nowy przedmiot do listy:")
                        .setMessage("Co musisz kupić?")
                        .setView(itemEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String item = String.valueOf(itemEditText.getText());
                                dbHelper.insertNewItem(item);
                                loadItemsList();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteItem(View view) {
        View parent = (View) view.getParent();
        TextView itemTextView = (TextView)parent.findViewById(R.id.item_name);
        String item = String.valueOf(itemTextView.getText());
        dbHelper.deleteItem(item);
        loadItemsList();
    }

    public void showDetails(View view) {
        // get the name of the thing
        View parent = (View) view.getParent();
        TextView itemTextView = parent.findViewById(R.id.item_name);
        String item = String.valueOf(itemTextView.getText());

        // create intent with name of the thing and start new activity based on it
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("name", item);

        startActivity(intent);
    }
}
