package com.eduhub.siu;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import java.util.HashMap;
import java.util.Map;


public class SignInPage extends AppCompatActivity {


    TextView move_to_signup,forgetpass;
    Button btnSignIn;
    TextInputEditText s_email, s_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in_page);

        move_to_signup = findViewById(R.id.move_to_signup);
        forgetpass = findViewById(R.id.forgetpass);
        btnSignIn = findViewById(R.id.btnSignIn);
        s_email = findViewById(R.id.email);
        s_password = findViewById(R.id.password);




        btnSignIn.setOnClickListener(view -> {

            if (s_email.length() > 0 && s_password.length() > 0) {

                String mail = s_email.getText().toString();
                String pass = s_password.getText().toString();


                StringRequest(mail,pass);


            }
            else {


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

            Intent nextActivity = new Intent(SignInPage.this,SignupPage.class);
            Bundle bundle = new Bundle();
            bundle.putInt("VAL", 112);
            nextActivity.putExtras(bundle);
            startActivity(nextActivity);

        });

        forgetpass.setOnClickListener(view -> {

            String mail = s_email.getText().toString();
            setForgetpass(mail);

        });

    }


    private void StringRequest(String mail, String pass) {
        String url = "http://192.168.1.106/SIU/login.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Create a ProgressDialog
        ProgressDialog progressDialog = new ProgressDialog(SignInPage.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    progressDialog.dismiss();

                    if (response.contains("login")) {

                        SharedPreferences sharedPreferences = getSharedPreferences("siu", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", mail);
                        editor.apply();


                        Intent myIntent = new Intent(SignInPage.this, MainActivity.class);
                        startActivity(myIntent);
                        finish();

                    } else {
                        progressDialog.dismiss(); // Hide loading if login failed

                        new AlertDialog.Builder(SignInPage.this)
                                .setTitle("Server response")
                                .setMessage(""+response)
                                .setNegativeButton("Thank You!", (dialog, which) -> dialog.dismiss())
                                .show();
                    }

                },
                error -> {
                    progressDialog.dismiss(); // Hide loading on error
                    Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> mymap = new HashMap<>();
                mymap.put("mail", Encyption.EncryptData(mail));
                mymap.put("pass", Encyption.EncryptData(pass));
                mymap.put("key", Encyption.Mykey);
                return mymap;
            }
        };

        requestQueue.add(postRequest);
    }

    private void setForgetpass(String mail) {

        String url = "http://192.168.1.106/SIU/forgetpass.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Create a ProgressDialog
        ProgressDialog progressDialog = new ProgressDialog(SignInPage.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    progressDialog.dismiss();

                    if (response.contains("OTP sent")) {

                        Toast.makeText(SignInPage.this, "OTP sent!", Toast.LENGTH_SHORT).show();

                        Intent nextActivity = new Intent(SignInPage.this,SignupPage.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("VAL", 111);
                        bundle.putString("mail",mail);
                        nextActivity.putExtras(bundle);
                        startActivity(nextActivity);


                    } else {
                        new AlertDialog.Builder(SignInPage.this)
                                .setTitle("Server response")
                                .setMessage(""+response)
                                .setNegativeButton("Thank You!", (dialog, which) -> dialog.dismiss())
                                .show();
                    }


                },
                error -> {
                    progressDialog.dismiss(); // Hide loading on error
                    Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> mymap = new HashMap<>();
                mymap.put("mail", Encyption.EncryptData(mail));
                return mymap;
            }
        };

        requestQueue.add(postRequest);
    }


}