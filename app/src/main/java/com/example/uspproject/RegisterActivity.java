package com.example.uspproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, confpass,emailEditText, phoneEditText, passwordEditText, favTeamEditText, bestFriendEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        phoneEditText = findViewById(R.id.phoneNumber);
        passwordEditText = findViewById(R.id.pass);
        favTeamEditText = findViewById(R.id.favTeam);
        bestFriendEditText = findViewById(R.id.bestFriend);
        registerButton = findViewById(R.id.button);
        confpass= findViewById(R.id.confpass);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    public void txt_OnClick(View view) {
        Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
        startActivity(intent);
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String favTeam = favTeamEditText.getText().toString().trim();
        String bestFriend = bestFriendEditText.getText().toString().trim();
        String confirmPassword= confpass.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || phone.isEmpty() ||
                password.isEmpty() || favTeam.isEmpty() || bestFriend.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "All fields are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!email.contains("@")) {
            Toast.makeText(RegisterActivity.this, "Invalid email format. Please enter a valid email.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 8) {
            Toast.makeText(RegisterActivity.this, "Password must be at least 8 characters long.", Toast.LENGTH_SHORT).show();
            return;
        } else if (!containsCapitalLetter(password)) {
            Toast.makeText(RegisterActivity.this, "Password must be contain CapitalLetter.", Toast.LENGTH_SHORT).show();
        } else if (!containsSpecialCharacter(password)) {
            Toast.makeText(RegisterActivity.this, "Password must be contain SpecialCharacter(!@$@#$!@#% ..).", Toast.LENGTH_SHORT).show();
        }


        if (!password.equals(confirmPassword)) {
            Toast.makeText(RegisterActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("email", email);
            jsonObject.put("phone", phone);
            jsonObject.put("password", password);
            jsonObject.put("fav_team", favTeam);
            jsonObject.put("best_friend", bestFriend);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://10.0.2.2:5000/create_user";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String result = response.getString("result");
                            Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });

        queue.add(request);
    }
    private boolean containsCapitalLetter(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }


    private boolean containsSpecialCharacter(String password) {
        String specialCharacters = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/";
        for (char c : password.toCharArray()) {
            if (specialCharacters.contains(String.valueOf(c))) {
                return true;
            }
        }
        return false;
    }
}
