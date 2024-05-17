package com.example.uspproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SelectedOptionsActivity extends AppCompatActivity {

    ListView listViewOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_options);

        ListView listViewOptions = findViewById(R.id.listViewOptions);

        ArrayList<String> selectedOptions = getIntent().getStringArrayListExtra("SELECTED_OPTIONS");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedOptions);
        listViewOptions.setAdapter(adapter);

        listViewOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = adapter.getItem(position);
                if ("Location".equals(selectedOption)) {
                    Intent intent = new Intent(SelectedOptionsActivity.this, LocationPermission.class);
                    startActivity(intent);
                } else if ("Camera".equals(selectedOption)) {
                    Intent intent = new Intent(SelectedOptionsActivity.this, CameraPermission.class);
                    startActivity(intent);
                } else if ("Microphone".equals(selectedOption)) {
                    Intent intent = new Intent(SelectedOptionsActivity.this, MicrophonePermission.class);
                    startActivity(intent);
                } else if ("Contacts".equals(selectedOption)) {
                    Intent intent = new Intent(SelectedOptionsActivity.this, ContactsPermission.class);
                    startActivity(intent);
                } else if ("Photo Gallery".equals(selectedOption)) {
                    Intent intent = new Intent(SelectedOptionsActivity.this, PhotosPermission.class);
                    startActivity(intent);
                } else if ("Permissions Manager".equals(selectedOption)) {
                    Intent intent = new Intent(SelectedOptionsActivity.this, PermissionsManagerActivity.class);
                    startActivity(intent);
                } else if ("Call Log Activity Tracing".equals(selectedOption)) {
                    Intent intent = new Intent(SelectedOptionsActivity.this, CallLogPermission.class);
                    startActivity(intent);
                }
            }
        });
    }
}