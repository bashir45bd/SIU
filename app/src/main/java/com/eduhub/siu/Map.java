package com.eduhub.siu;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;


public class Map extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView address_copy,show_title,show_des,show_notice;
    LinearLayout address, contact2, mail;
    ImageView detialsback,show_pic,notice_pic;
    CardView c_fb,c_instragram,c_youtube;
    RelativeLayout mappage,detialspage,noticeshow;
    ExtendedFloatingActionButton fav;

    public static String title="";
    public static String descrpition="";
    public  static Bitmap bitmap= null;

    public static String notice="";
    public  static Bitmap notice_pic1= null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
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



        Bundle bun=getIntent().getExtras();
        int val =bun.getInt("VAL");


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

            show_notice.setText(notice);

            if(notice_pic1!=null){
                notice_pic.setImageBitmap(notice_pic1);
            }


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


}