package com.example.uspproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MultiSittings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_sittings);

        findViewById(R.id.cardViewEditGeneralInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MultiSittings.this, EditGeneralInfoActivity.class);
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);


            }
        });

        findViewById(R.id.cardViewChangePassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MultiSittings.this, ChangePasswordActivity.class);
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);            }
        });

        findViewById(R.id.cardViewModifyAppFeatures).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MultiSittings.this, ChangePasswordActivity.class);
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);             }
        });
    }
}