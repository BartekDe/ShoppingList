package com.example.Lista;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.logging.Logger;

public class DetailsActivity extends AppCompatActivity {

    private TextView nameTV;
    private EditText editDesc;
    private Button saveDescBtn;
    private Button deleteBtn;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        nameTV = findViewById(R.id.nameTV);
        editDesc = findViewById(R.id.editDesc);
        saveDescBtn = findViewById(R.id.saveDescBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        dbHelper = new DBHelper(this);

        final String name = getIntent().getExtras().getString("name");
        nameTV.setText(name);

        String currentDescription = dbHelper.getDescription(name);
        editDesc.setText(currentDescription);

        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info("OBECNY OPIS DLA PRZEDMIOTU " + name + " = " + currentDescription);

        saveDescBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newDescription = editDesc.getText().toString();
                dbHelper.saveDescription(name, newDescription);
                Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info("ZAPISYWANIE OPISU ITEMU DLA " + name + ", opis= " + newDescription);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteItem(name);
                Intent intent = new Intent(DetailsActivity.this, AktywnoscLista.class);
                startActivity(intent);
            }
        });
    }
}
