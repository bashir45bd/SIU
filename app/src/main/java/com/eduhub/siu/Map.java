package com.eduhub.siu;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView address_copy;
    LinearLayout address, contact2, mail;
    ImageView mapback;
    CardView c_fb,c_instragram,c_youtube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        address = findViewById(R.id.address);
        address_copy = findViewById(R.id.address_copy);
        contact2 = findViewById(R.id.contact2);
        mail = findViewById(R.id.mail);
        mapback=findViewById(R.id.mapback);
        c_fb=findViewById(R.id.c_fb);
        c_instragram=findViewById(R.id.c_instragram);
        c_youtube=findViewById(R.id.c_youtube);


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



        mapback.setOnClickListener(v -> {
            onBackPressed();

        });


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