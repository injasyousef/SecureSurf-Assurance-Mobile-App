package com.example.uspproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AccessibilityOptionsActivity extends AppCompatActivity {

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility_options);

        CheckBox checkBoxCamera = findViewById(R.id.checkBoxCamera);
        CheckBox checkBoxLocation = findViewById(R.id.checkBoxLocation);
        CheckBox checkBoxPhotoGallery = findViewById(R.id.checkBoxPhotoGallery);
        CheckBox checkBoxActivityTracing = findViewById(R.id.checkBoxActivityTracing);
        CheckBox checkBoxMicrophone = findViewById(R.id.checkBoxMicrophone);
        CheckBox checkBoxContacts = findViewById(R.id.checkBoxContacts);
        CheckBox checkBoxPermissionsManager = findViewById(R.id.checkBoxPermissionsManager);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        Button buttonProtect = findViewById(R.id.buttonProtect);
        buttonProtect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> selectedOptions = new ArrayList<>();

                if (checkBoxCamera.isChecked())
                    selectedOptions.add(checkBoxCamera.getText().toString());
                if (checkBoxLocation.isChecked())
                    selectedOptions.add(checkBoxLocation.getText().toString());
                if (checkBoxPhotoGallery.isChecked())
                    selectedOptions.add(checkBoxPhotoGallery.getText().toString());
                if (checkBoxActivityTracing.isChecked())
                    selectedOptions.add(checkBoxActivityTracing.getText().toString());
                if (checkBoxMicrophone.isChecked())
                    selectedOptions.add(checkBoxMicrophone.getText().toString());
                if (checkBoxContacts.isChecked())
                    selectedOptions.add(checkBoxContacts.getText().toString());
                if (checkBoxPermissionsManager.isChecked())
                    selectedOptions.add(checkBoxPermissionsManager.getText().toString());

                Intent intent = new Intent(AccessibilityOptionsActivity.this, SelectedOptionsActivity.class);
                intent.putStringArrayListExtra("SELECTED_OPTIONS", selectedOptions);
                startActivity(intent);
            }
        });

        checkBoxCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCheckBoxClick((CheckBox) view);
            }
        });

        checkBoxLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCheckBoxClick((CheckBox) view);
            }
        });

        checkBoxPhotoGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCheckBoxClick((CheckBox) view);
            }
        });

        checkBoxActivityTracing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCheckBoxClick((CheckBox) view);
            }
        });

        checkBoxMicrophone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCheckBoxClick((CheckBox) view);
            }
        });

        checkBoxContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCheckBoxClick((CheckBox) view);
            }
        });

        checkBoxPermissionsManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCheckBoxClick((CheckBox) view);
            }
        });
    }

    private void handleCheckBoxClick(CheckBox checkBox) {
        if (checkBox.isChecked()) {
            Toast.makeText(this, checkBox.getText() + " is selected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, checkBox.getText() + " is unselected", Toast.LENGTH_SHORT).show();
        }
    }

    public void Setting(View view) {
        Intent intent = new Intent(AccessibilityOptionsActivity.this, MultiSittings.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }
}