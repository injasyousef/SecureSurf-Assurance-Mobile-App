package com.example.uspproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class VerificationActivity extends AppCompatActivity {

    private EditText edtFavoriteSport, edtBestFriend;
    private Button btnVerify;
    private  String favSport ,bestFriend , email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        edtFavoriteSport = findViewById(R.id.favoriteSport);
        edtBestFriend = findViewById(R.id.bestFriend);
        btnVerify = findViewById(R.id.buttonVerify);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        fetchUserDetails(email);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the user details match the expected values
                verifyUserDetails();
            }
        });
    }

    private void fetchUserDetails(String email) {
        String url = "http://10.0.2.2:5000/user/by-email/" + email;

        RequestQueue queue = Volley.newRequestQueue(VerificationActivity.this);

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
                                favSport = jsonArray.getString(5);
                                bestFriend = jsonArray.getString(6);
                            } else {
                                Toast.makeText(VerificationActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VolleyError", error.toString());
                    }
                }
        );
        queue.add(request);
    }

    private void verifyUserDetails() {
        String enteredFavSport = edtFavoriteSport.getText().toString();
        String enteredBestFriend = edtBestFriend.getText().toString();

        if (enteredFavSport.equals(favSport) && enteredBestFriend.equals(bestFriend)) {
            Intent intent = new Intent(VerificationActivity.this, VerificationCodeActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        } else {
            Toast.makeText(VerificationActivity.this, "Verification failed. Please enter again.", Toast.LENGTH_SHORT).show();
        }
    }
}