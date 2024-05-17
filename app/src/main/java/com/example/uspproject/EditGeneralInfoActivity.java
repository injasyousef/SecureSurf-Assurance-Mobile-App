package com.example.uspproject;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EditGeneralInfoActivity extends AppCompatActivity {

    private EditText usernameEditText, favTeamEditText, bestFriendEditText;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_general_info);

        usernameEditText = findViewById(R.id.username);
        favTeamEditText = findViewById(R.id.favTeam);
        bestFriendEditText = findViewById(R.id.bestFriend);

        email = getIntent().getStringExtra("email");
        fetchUserData();
    }

    private void fetchUserData() {
        String url = "http://10.0.2.2:5000/user/by-email/" + email;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray jsonArray = response;
                            if (response.length() > 0) {
                                String username = jsonArray.getString(1);
                                String favTeam = jsonArray.getString(5);
                                String bestFriend = jsonArray.getString(6);

                                usernameEditText.setText(username);
                                favTeamEditText.setText(favTeam);
                                bestFriendEditText.setText(bestFriend);
                            } else {
                                Toast.makeText(EditGeneralInfoActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(EditGeneralInfoActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditGeneralInfoActivity.this, "Error fetching user data", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });

        queue.add(request);
    }
    public void save(View view) {
        String username = usernameEditText.getText().toString();
        String favTeam = favTeamEditText.getText().toString();
        String bestFriend = bestFriendEditText.getText().toString();

        if (username.isEmpty() || favTeam.isEmpty() || bestFriend.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            updateUser(username, favTeam, bestFriend);
        }
    }

    private void updateUser(String username, String favTeam, String bestFriend) {
        String url = "http://10.0.2.2:5000/update_user";
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("email", email);
            jsonBody.put("fav_team", favTeam);
            jsonBody.put("best_friend", bestFriend);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String result = response.getString("result");
                            Toast.makeText(EditGeneralInfoActivity.this, result, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(EditGeneralInfoActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditGeneralInfoActivity.this, "Error updating user data", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });

        queue.add(request);
    }
}