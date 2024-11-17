package com.eduhub.siu;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Author_all extends AppCompatActivity {

    RelativeLayout dep,admin,admi,author;
    ImageView depback,adminback,admiback,authorback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_all);

        dep=findViewById(R.id.depart);
        admin=findViewById(R.id.admini);
        admi=findViewById(R.id.admi);
        author=findViewById(R.id.author);

        depback=findViewById(R.id.depback);
        adminback=findViewById(R.id.adminiback);
        admiback=findViewById(R.id.admiback);
        authorback=findViewById(R.id.authorback);


        Bundle bun=getIntent().getExtras();
        int val =bun.getInt("VAL");

        if(val==1) {
            dep.setVisibility(View.VISIBLE);

        }
        else if(val==2) {
            admin.setVisibility(View.VISIBLE);

        }
        else if(val==3) {
            admi.setVisibility(View.VISIBLE);

        }
        else if(val==4) {
            author.setVisibility(View.VISIBLE);

        }






        depback.setOnClickListener(v -> {
            onBackPressed();
        });

        adminback.setOnClickListener(v -> {
            onBackPressed();
        });

        admiback.setOnClickListener(v -> {
            onBackPressed();
        });

        authorback.setOnClickListener(v -> {
            onBackPressed();
        });


    }


}