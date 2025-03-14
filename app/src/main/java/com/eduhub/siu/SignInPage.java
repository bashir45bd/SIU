package com.eduhub.siu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class SignInPage extends AppCompatActivity {


    TextView move_to_signup;
    Button btnSignIn;
    TextInputEditText s_email, s_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in_page);

        move_to_signup = findViewById(R.id.move_to_signup);
        btnSignIn = findViewById(R.id.btnSignIn);
        s_email = findViewById(R.id.email);
        s_password = findViewById(R.id.password);


        btnSignIn.setOnClickListener(view -> {

            if (s_email.length() > 0 && s_password.length() > 0) {

                String mail = s_email.getText().toString();
                String pass = s_password.getText().toString();


                StringRequest(mail,pass);


            } else {


                new AlertDialog.Builder(SignInPage.this)
                        .setTitle("Please Fill up!")
                        .setMessage("Enter your Information")
                        .setNegativeButton("Thank You!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

            }


        });


        move_to_signup.setOnClickListener(view -> {

            Intent myIntent = new Intent(SignInPage.this, SignupPage.class);
            startActivity(myIntent);
            finish();
        });

    }

    private void ArrayRequest(String mail, String pass) {

        String url = "http://192.168.1.105/SIU/ArrryRequest.php"; // Replace with your API URL
        // Creating JSON Array
        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject user1 = new JSONObject();
            user1.put("email", mail);
            user1.put("pass", pass);
            user1.put("key", "2021");

//            JSONObject user2 = new JSONObject();
//            user2.put("name", "John Doe");
//            user2.put("email", "john@example.com");

            jsonArray.put(user1);
            //jsonArray.put(user2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Creating JSON Array Request (POST)
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST, url, jsonArray,
                response -> {

                    for (int x = 0; x < response.length(); x++) {

                        Toast.makeText(this, "" + response, Toast.LENGTH_SHORT).show();


//                        try {
//                            JSONArray json = response.getJSONArray(x);
//                            JSONObject object= json.getJSONObject(0);
//                            String title = object.getString("title");
//                            String  video_id = object.getString("video_id");
//
//
//                            new AlertDialog.Builder(SignInPage.this)
//                                    .setTitle(title)
//                                    .setMessage(video_id)
//                                    .setNegativeButton("Thank You!", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                        }
//                                    })
//                                    .show();
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }

                },
                error -> {
                    // Handle error
                    Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                });

        // Adding request to Volley queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void StringRequest(String mail, String pass) {


        String url = "http://192.168.1.104/SIU/login.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {

                    if (response.contains("login")) {

                        SharedPreferences sharedPreferences = getSharedPreferences("siu", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", mail);
                        editor.apply();

                        //Code here
                        Intent myIntent = new Intent(SignInPage.this, MainActivity.class);
                        startActivity(myIntent);
                        finish();


                    } else {
                        new AlertDialog.Builder(SignInPage.this)
                                .setTitle("Check Again!")
                                .setMessage("Invalid password or email")
                                .setNegativeButton("Thank You!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }


                },
                error -> {
                    // Handle error
                    Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {

            @Nullable
            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> mymap = new HashMap<>();

                mymap.put("mail", Encyption.EncryptData(mail));
                mymap.put("pass", Encyption.EncryptData(pass));
                mymap.put("key", Encyption.Mykey);

                return mymap;
            }
        };

        requestQueue.add(postRequest);
    }

}