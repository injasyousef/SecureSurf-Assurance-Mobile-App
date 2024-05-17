package com.example.uspproject;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class LogInActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private CheckBox rememberCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.pass);
        rememberCheckBox = findViewById(R.id.checkBox);

        loadCredentials();
    }

    public void signIn(View view) {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if (rememberCheckBox.isChecked()) {
            saveCredentials(email, password);
        } else {
            clearCredentials();
        }

        loginUser(email, password);
    }

    private void saveCredentials(String email, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    private void loadCredentials() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email", "");
        String savedPassword = sharedPreferences.getString("password", "");

        if (!savedEmail.isEmpty() && !savedPassword.isEmpty()) {
            edtEmail.setText(savedEmail);
            edtPassword.setText(savedPassword);
            rememberCheckBox.setChecked(true);
        }
    }

    private void clearCredentials() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void txt_OnClick(View view) {
        Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void loginUser(String email, String password) {
        String url = "http://10.0.2.2:5000/user/by-email/" + email;

        RequestQueue queue = Volley.newRequestQueue(LogInActivity.this);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray jsonArray = response;

                            if (jsonArray.length() > 0) {
                                String serverPassword = jsonArray.getString(4);

                                if (password.equals(serverPassword)) {
                                    Intent intent = new Intent(LogInActivity.this, VerificationActivity.class);
                                    intent.putExtra("email", email);
                                    startActivity(intent);
                                } else {
                                    showToast("Invalid password");
                                }
                            } else {
                                showToast("User not found");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showToast("Error occurred");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VolleyError", error.toString());
                        showToast("Error occurred");
                    }
                }
        );
        queue.add(request);
    }

    private void showToast(String message) {
        Toast.makeText(LogInActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}