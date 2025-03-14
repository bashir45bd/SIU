package com.eduhub.siu;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;


public class Map extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView address_copy,show_title,show_des,show_notice;
    LinearLayout address, contact2, mail;
    ImageView detialsback,show_pic,notice_pic,u_image,select_image;
    CardView c_fb,c_instragram,c_youtube,up_card,de_card;
    RelativeLayout mappage,detialspage,noticeshow;
    ExtendedFloatingActionButton fav;
    TextInputEditText u_title,u_body;
    Button u_btn;
    String imageBase64;
    SharedPreferences sharedPreferences;

    public static String title="";
    public static String descrpition="";
    public  static Bitmap bitmap= null;

    public static String notice="";
    public  static Bitmap notice_pic1= null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        address = findViewById(R.id.address);
        address_copy = findViewById(R.id.address_copy);
        contact2 = findViewById(R.id.contact2);
        mail = findViewById(R.id.mail);
        c_fb=findViewById(R.id.c_fb);
        c_instragram=findViewById(R.id.c_instragram);
        c_youtube=findViewById(R.id.c_youtube);
        mappage=findViewById(R.id.mappage);
        detialspage=findViewById(R.id.detials);
        noticeshow=findViewById(R.id.noticeshow);
        detialsback=findViewById(R.id.detialsback);
        show_des=findViewById(R.id.show_des);
        show_notice=findViewById(R.id.show_notice);
        show_pic=findViewById(R.id.show_pic);
        notice_pic=findViewById(R.id.notice_pic2);
        show_title=findViewById(R.id.show_title);
        up_card=findViewById(R.id.up_card);
        de_card=findViewById(R.id.de_card);






        Bundle bun=getIntent().getExtras();
        int val =bun.getInt("VAL");
        String id = bun.getString("id");
        String a_title = bun.getString("title");
        String a_body = bun.getString("body");
        String a_image = bun.getString("image");


        if(val==41) {

          mappage.setVisibility(View.VISIBLE);

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map_f);
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }

            address.setOnClickListener(v -> {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Map.this.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("TextView",address_copy.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(Map.this, "Address copied", Toast.LENGTH_LONG).show();
            });

            contact2.setOnClickListener(v -> {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+"+8801754313182"));
                startActivity(intent);
            });

            mail.setOnClickListener(v -> {

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto: info@siu.edu.bd "));
                startActivity(intent);
            });

            c_fb.setOnClickListener(v -> {
                String uri = "https://www.facebook.com/siuofficialbd/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            });

            c_youtube.setOnClickListener(v -> {
                String uri = "https://www.youtube.com/@siuofficialbd";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            });

            c_youtube.setOnClickListener(v -> {
                String uri = "https://siu.edu.bd/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            });




        }

        else if(val==42) {

            detialspage.setVisibility(View.VISIBLE);


            show_des.setText(descrpition);
            show_title.setText(title);

            if(bitmap!=null){
                show_pic.setImageBitmap(bitmap);
            }
            detialsback.setOnClickListener(v -> {
                onBackPressed();
            });
        }

        else if(val==43) {

            noticeshow.setVisibility(View.VISIBLE);
            up_card.setVisibility(View.GONE);
            de_card.setVisibility(View.GONE);


            de_card.setOnClickListener(view -> {

                new AlertDialog.Builder(Map.this)
                        .setTitle("Delete!")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String_RequestFordelete(id);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();


            });

            up_card.setOnClickListener(view -> {

                update_data(id,a_title,a_body,a_image);


            });


            show_notice.setText(notice);

            if(notice_pic1!=null){
                notice_pic.setImageBitmap(notice_pic1);
            }


        }


        sharedPreferences=getSharedPreferences("siu",MODE_PRIVATE);
        String types= sharedPreferences.getString("user_type","");

        if (types.contains("teacher")){

            up_card.setVisibility(View.VISIBLE);
            de_card.setVisibility(View.VISIBLE);
        }



    }//============================================================


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker at a specific location and move the camera
        LatLng location = new LatLng(24.9015, 91.8446); // Replace with your coordinates
        mMap.addMarker(new MarkerOptions().position(location).title("SIU"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));// Zoom level: 1-20
    }


    private void update_data(String id , String title, String body, String image) {

        BottomSheetDialog dialog = new BottomSheetDialog(this);


        View view = getLayoutInflater().inflate(R.layout.add_notice, null);
        dialog.setContentView(view);
        u_image=view.findViewById(R.id.n_image);
        select_image=view.findViewById(R.id.select_image);
        u_title=view.findViewById(R.id.n_title);
        u_body=view.findViewById(R.id.n_body);
        u_btn=view.findViewById(R.id.n_btn);

        u_btn.setText("Update!");
        u_title.setText(title);
        u_body.setText(body);
        Picasso.get()
                .load(""+image)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(u_image);


        select_image.setOnClickListener(view1 -> {

            ImagePicker.with(this)
                    .crop()         // Enables cropping
                    .compress(1024) // Compress image
                    .start();

        });

        u_btn.setOnClickListener(view1 -> {

            if (u_title.length()>0&&u_body.length()>0){

                String f_title= u_title.getText().toString();
                String f_body= u_body.getText().toString();

                String_Request(id,f_title,f_body,imageBase64);

                dialog.dismiss();



            }else {
                new AlertDialog.Builder(Map.this)
                        .setTitle("Please Fill up!")
                        .setMessage("Provide All Data")
                        .setNegativeButton("Thank You!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

            }



        });






        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            Uri fileUri = data.getData();
            Bitmap bitmap = uriToBitmap(fileUri);

            if (bitmap != null) {
                u_image.setImageBitmap(bitmap);
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

    private void String_Request(String id,String title, String body,String image){


        String url = "http://192.168.1.104/SIU/update_notice_event.php";

        // If no new image is selected, send an empty string
        if (image == null || image.isEmpty()) {
            image = "";
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);


        String finalImage = image;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {

                    if (response.contains("Update Successful")){

                        new AlertDialog.Builder(Map.this)
                                .setTitle("Note!")
                                .setMessage("Update Successful")
                                .setNegativeButton("Thank You!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();


                    } else {
                        new AlertDialog.Builder(Map.this)
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

                java.util.Map<String, String> mymap = new HashMap<>();

                mymap.put("id", id);
                mymap.put("title",title);
                mymap.put("body", body);
                mymap.put("image",finalImage);
                mymap.put("key",Encyption.Mykey);

                return mymap;
            }
        };

        requestQueue.add(postRequest);
    }

    private void String_RequestFordelete(String id){


        String url = "http://192.168.1.104/SIU/event_notice_de.php";



        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {

                    if (response.contains("Deleted Successfully")){

                        new AlertDialog.Builder(Map.this)
                                .setTitle("Note!")
                                .setMessage("Delete Successful")
                                .setNegativeButton("Thank You!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();


                    } else {
                        new AlertDialog.Builder(Map.this)
                                .setTitle("Note!")
                                .setMessage("Dose not  Deleted")
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

                java.util.Map<String, String> mymap = new HashMap<>();

                mymap.put("id", id);
                mymap.put("key",Encyption.Mykey);

                return mymap;
            }
        };

        requestQueue.add(postRequest);
    }


}