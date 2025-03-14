package com.eduhub.siu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import java.util.HashMap;
import java.util.Map;


public class SignupPage extends AppCompatActivity {

    TextView move_to_signin,types;
    Switch tv_switch;
    LinearLayout student, teacher;
    AutoCompleteTextView student_dep,t_dep;
    TextInputEditText y_name,y_session,y_roll,y_reg,y_phone,y_mail,y_password;
    TextInputEditText t_name,t_pos,t_phone,t_mail,t_password,t_code;

    String[] options = {"CSE", "ECE", "LAW", "ENG","BBA"};
    Button btnsignup,t_btnsignup;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup_page);

        move_to_signin=findViewById(R.id.move_to_signin);
        types= findViewById(R.id.types);
        tv_switch= findViewById(R.id.tv_switch);
        student = findViewById(R.id.student);
        teacher = findViewById(R.id.teacher);
        student_dep =findViewById(R.id.student_dep);
        t_dep =findViewById(R.id.t_dep);
        y_name = findViewById(R.id.s_name);
        y_roll = findViewById(R.id.s_roll);
        y_reg = findViewById(R.id.s_reg);
        y_phone = findViewById(R.id.s_phone);
        y_session = findViewById(R.id.s_session);
        y_mail = findViewById(R.id.s_mail);
        y_password = findViewById(R.id.s_password);
        btnsignup = findViewById(R.id.s_btnSignUp);
        t_btnsignup = findViewById(R.id.t_btnsignup);
        t_name = findViewById(R.id.t_name);
        t_pos = findViewById(R.id.t_pos);
        t_code = findViewById(R.id.t_code);
        t_phone = findViewById(R.id.t_phone);
        t_mail = findViewById(R.id.t_email);
        t_password = findViewById(R.id.t_pass);



        t_btnsignup.setOnClickListener(view -> {


            if (t_name.length()>0 && t_dep.length()>0 && t_pos.length()>0 && t_code.length()>0 && t_phone.length()==9 && t_mail.length()>0 && t_password.length()>0) {

                String b_name =t_name.getText().toString();
                String b_pos =t_pos.getText().toString();
                String b_phone = t_phone.getText().toString();
                String b_dep =t_dep.getText().toString();
                String b_mail =t_mail.getText().toString();
                String b_password = t_password.getText().toString();
                String b_code = t_code.getText().toString();

                if (b_code.contains("SIU2024")){

                    String_RequestForteacher(b_name,b_pos,b_phone,b_dep,b_mail,b_password,"teacher");

                }else {
                    new AlertDialog.Builder(SignupPage.this)
                            .setTitle("Wrong Code!")
                            .setMessage("Enter valid code")
                            .setNegativeButton("Thank You!", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();

                }




            } else {


                new AlertDialog.Builder(SignupPage.this)
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




        btnsignup.setOnClickListener(view -> {


            if (y_name.length()>0 && y_session.length()>0 && y_roll.length()>0 && y_reg.length()>0 && y_phone.length()==9 && y_mail.length()>0 && y_password.length()>0 && student_dep.length()>0) {

                String f_name =y_name.getText().toString();
                String f_roll = y_roll.getText().toString();
                String f_reg =y_reg.getText().toString();
                String f_phone = y_phone.getText().toString();
                String f_dep =student_dep.getText().toString();
                String f_mail =y_mail.getText().toString();
                String f_password = y_password.getText().toString();
                String f_session = y_session.getText().toString();


                String_Request(f_name,f_session,f_roll,f_reg,f_phone,f_dep,f_mail,f_password,"student");


            } else {


                new AlertDialog.Builder(SignupPage.this)
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




        ArrayAdapter<String> adapter = new ArrayAdapter<>(SignupPage.this, android.R.layout.simple_dropdown_item_1line, options);
        student_dep.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(SignupPage.this, android.R.layout.simple_dropdown_item_1line, options);
        t_dep.setAdapter(adapter1);


        tv_switch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                student.setVisibility(View.GONE);
                teacher.setVisibility(View.VISIBLE);
                types.setText("For Faculty Member");

            } else {
                student.setVisibility(View.VISIBLE);
                teacher.setVisibility(View.GONE);
                types.setText("For Students");

            }
        });


     move_to_signin.setOnClickListener(view -> {

         //Code here
         Intent myIntent = new Intent(SignupPage.this, SignInPage.class);
         startActivity(myIntent);
         finish();
     });

    }//=========on create end==========================


    private void String_Request(String name,String session, String roll,String reg, String phone, String dep,String mail, String pass, String user_type){


         String url = "http://192.168.1.104/SIU/SignUp.php";

         RequestQueue requestQueue = Volley.newRequestQueue(this);

         StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                 response -> {

                   if (response.contains("Account Create Successful")){

                       SharedPreferences sharedPreferences= getSharedPreferences("siu",MODE_PRIVATE);
                       SharedPreferences.Editor editor=sharedPreferences.edit();
                       editor.putString("email",mail);
                       editor.apply();

                       //Code here
                       Intent myIntent = new Intent(SignupPage.this, MainActivity.class);
                       startActivity(myIntent);
                       finish();



                   } else {
                       new AlertDialog.Builder(SignupPage.this)
                               .setTitle("Already account created!")
                               .setMessage("Please use another phone or email.")
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
                 })
         {

             @Nullable
             @Override
             protected Map<String, String> getParams() throws AuthFailureError {

                 Map<String, String> mymap = new HashMap<>();

                 mymap.put("name", name);
                 mymap.put("session",session);
                 mymap.put("roll", roll);
                 mymap.put("reg",reg);
                 mymap.put("phone", phone);
                 mymap.put("mail",Encyption.EncryptData(mail));
                 mymap.put("pass",Encyption.EncryptData(pass));
                 mymap.put("dep", dep);
                 mymap.put("user_type", user_type);
                 mymap.put("key",Encyption.Mykey);

                 return mymap;
             }
         };

         requestQueue.add(postRequest);
     }

    private void String_RequestForteacher(String name,String pos, String phone, String dep,String mail, String pass, String user_type){


        String url = "http://192.168.1.104/SIU/teacher_signup.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {

                    if (response.contains("Account Create Successful")){

                        SharedPreferences sharedPreferences= getSharedPreferences("siu",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("email",mail);
                        editor.apply();

                        //Code here
                        Intent myIntent = new Intent(SignupPage.this, MainActivity.class);
                        startActivity(myIntent);
                        finish();



                    } else {
                        new AlertDialog.Builder(SignupPage.this)
                                .setTitle("Already account created!")
                                .setMessage("Please use another phone or email.")
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
                })
        {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> mymap = new HashMap<>();

                mymap.put("name", name);
                mymap.put("pos",pos);
                mymap.put("phone", phone);
                mymap.put("mail",Encyption.EncryptData(mail));
                mymap.put("pass",Encyption.EncryptData(pass));
                mymap.put("dep", dep);
                mymap.put("user_type", user_type);
                mymap.put("key",Encyption.Mykey);

                return mymap;
            }
        };

        requestQueue.add(postRequest);
    }

//    private void ArrayRequest() {
//
//        String url = "http://192.168.1.104/SIU/SignUp.php"; // Replace with your API URL
//
//
//        // Creating JSON Array Request (POST)
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                Request.Method.GET, url, null,
//                response -> {
//
//                    for (int x = 0; x < response.length(); x++) {
//
//                        try {
//
//                            JSONObject object= response.getJSONObject(x);
//                            String name = object.getString("name");
//                            //String  video_id = object.getString("video_id");
//                            types.setText(name);
//
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//
//
//                },
//                error -> {
//                    // Handle error
//                    Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                });
//
//        // Adding request to Volley queue
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(jsonArrayRequest);
//    }



}