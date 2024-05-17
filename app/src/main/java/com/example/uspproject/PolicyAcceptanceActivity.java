package com.example.uspproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class PolicyAcceptanceActivity extends AppCompatActivity {
    RadioGroup radioGroupPolicy;
    RadioButton radioButtonAccept;
    Button buttonContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_acceptance);

        radioGroupPolicy = findViewById(R.id.radioGroupPolicy);
        radioButtonAccept = findViewById(R.id.radioButtonAccept);
        buttonContinue = findViewById(R.id.buttonContinue);

        radioGroupPolicy.setOnCheckedChangeListener((group, checkedId) -> {
            buttonContinue.setEnabled(checkedId == R.id.radioButtonAccept);
        });

        buttonContinue.setOnClickListener(v -> {
            if (radioButtonAccept.isChecked()) {
                Intent intent = new Intent(PolicyAcceptanceActivity.this, RegisterActivity.class);
                startActivity(intent);
            } else {
                finish();
            }
        });
    }
}