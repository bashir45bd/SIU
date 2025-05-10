package com.eduhub.siu;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    ImageView p_image,u_image,back;

    String imageBase64;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bun=getIntent().getExtras();
        String name =bun.getString("name");
        String bio =bun.getString("bio");
        String session =bun.getString("session");
        String roll =bun.getString("roll");
        String reg =bun.getString("reg");
        String fblink =bun.getString("fblink");
        String email =bun.getString("mail");
        String phone =bun.getString("phone");
        String dep =bun.getString("dep");
        String image =bun.getString("image");
        String id =bun.getString("id");

        Button u_btn=findViewById(R.id.u_btn);
        TextInputEditText f_name=findViewById(R.id.u_name);
        TextInputEditText f_bio=findViewById(R.id.u_bio);
        TextInputEditText f_session=findViewById(R.id.u_session);
        TextInputEditText f_roll=findViewById(R.id.u_roll);
        TextInputEditText f_reg=findViewById(R.id.u_reg);
        TextInputEditText f_phone=findViewById(R.id.u_phone);
        TextInputEditText f_mail=findViewById(R.id.u_mail);
        TextInputEditText f_fblink=findViewById(R.id.u_facebook);
        TextInputEditText f_dep=findViewById(R.id.u_dep);
        p_image=findViewById(R.id.p_image);
        u_image=findViewById(R.id.u_image);
        back=findViewById(R.id.profileback);





        f_name.setText(name);
        f_phone.setText(phone);
        f_bio.setText(bio);
        f_session.setText(session);
        f_roll.setText(roll);
        f_reg.setText(reg);
        f_mail.setText(email);
        f_dep.setText(dep);
        f_fblink.setText(fblink);

        Picasso.get()
                .load(""+image)
                .placeholder(R.drawable.itempic)
                .error(R.drawable.itempic)
                .into(p_image);



        u_image.setOnClickListener(view -> {


            ImagePicker.with(this)
                    .crop()         // Enables cropping
                    .compress(1024) // Compress image
                    .start();

        });


        u_btn.setOnClickListener(view1 -> {


            if (f_name.length()>0&&f_dep.length()>0&&f_bio.length()>0&&f_mail.length()>0&&f_phone.length()>0&&f_reg.length()>0&&f_roll.length()>0&&f_session.length()>0&&f_fblink.length()>0){

                String s_name= f_name.getText().toString();
                String s_bio= f_bio.getText().toString();
                String s_mail= f_mail.getText().toString();
                String s_phone= f_phone.getText().toString();
                String s_roll= f_roll.getText().toString();
                String s_reg=  f_reg.getText().toString();
                String s_session= f_session.getText().toString();
                String s_fb = f_fblink.getText().toString();
                String s_dep = f_dep.getText().toString();

                String_Request(s_name,s_session,s_roll,s_reg,s_phone,s_dep,s_mail,s_fb,s_bio,imageBase64,id);


            }
            else {
                new AlertDialog.Builder(Profile.this)
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

        back.setOnClickListener(view -> {
            onBackPressed();
        });

    }

    private void String_Request(String name,String session, String roll,String reg, String phone, String dep,String mail, String fblink,String bio, String image,String id){


        String url = "http://192.168.1.104/SIU/update_info.php";

        // If no new image is selected, send an empty string
        if (image == null || image.isEmpty()) {
            image = "";
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);


        String finalImage = image;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {

                    if (response.contains("Update Successful")){

                        SharedPreferences sharedPreferences= getSharedPreferences("siu",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("email",mail);
                        editor.apply();

                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent); // Send success result to MainActivity
                        finish(); // Close Profile Activity


                    } else {
                        new AlertDialog.Builder(Profile.this)
                                .setTitle("Note!")
                                .setMessage("Update not Successful")
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
            protected java.util.Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> mymap = new HashMap<>();

                mymap.put("name", name);
                mymap.put("session",session);
                mymap.put("roll", roll);
                mymap.put("reg",reg);
                mymap.put("phone", phone);
                mymap.put("mail",Encyption.EncryptData(mail));
                mymap.put("bio",bio);
                mymap.put("fblink",fblink);
                mymap.put("dep", dep);
                mymap.put("image", finalImage);
                mymap.put("id",id);
                mymap.put("key",Encyption.Mykey);

                return mymap;
            }
        };

        requestQueue.add(postRequest);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            Uri fileUri = data.getData();
            Bitmap bitmap = uriToBitmap(fileUri);

            if (bitmap != null) {
                p_image.setImageBitmap(bitmap);
                imageBase64 = bitmapToBase64(bitmap);
            }

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap uriToBitmap(Uri uri) {
        try {
            return MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


}