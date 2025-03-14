package com.eduhub.siu;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Author_all extends AppCompatActivity {

    RelativeLayout dep,admin,admi,author;
    ImageView depback,adminback,admiback,authorback;
    CardView cse,ece,law,bba,english;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_all);

        dep=findViewById(R.id.depart);
        admin=findViewById(R.id.admini);
        admi=findViewById(R.id.admi);
        author=findViewById(R.id.author);
        cse=findViewById(R.id.cse);
        ece=findViewById(R.id.ece);
        law=findViewById(R.id.law);
        bba=findViewById(R.id.bba);
        english=findViewById(R.id.english);

        depback=findViewById(R.id.depback);
        adminback=findViewById(R.id.adminiback);
        admiback=findViewById(R.id.admiback);
        authorback=findViewById(R.id.authorback);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout2 = findViewById(R.id.tabLayout2);
        ViewPager2 viewPager2 = findViewById(R.id.viewPager2);

        Bundle bun=getIntent().getExtras();
        int val =bun.getInt("VAL");







        if(val==1) {
            dep.setVisibility(View.VISIBLE);

            cse.setOnClickListener(v -> {
                Toast.makeText(Author_all.this,"Up Coming",Toast.LENGTH_LONG).show();
            });

            ece.setOnClickListener(v -> {
                Toast.makeText(Author_all.this,"Up Coming",Toast.LENGTH_LONG).show();
            });
            law.setOnClickListener(v -> {
                Toast.makeText(Author_all.this,"Up Coming",Toast.LENGTH_LONG).show();
            });
            english.setOnClickListener(v -> {
                Toast.makeText(Author_all.this,"Up Coming",Toast.LENGTH_LONG).show();
            });
            bba.setOnClickListener(v -> {
                Toast.makeText(Author_all.this,"Up Coming",Toast.LENGTH_LONG).show();
            });



        }
        else if(val==2) {
            admin.setVisibility(View.VISIBLE);
            ViewPagerAdapter2 adapter2 = new ViewPagerAdapter2(this);
            viewPager2.setAdapter(adapter2);

            new TabLayoutMediator(tabLayout2, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab2, int position2) {
                    if (position2 == 0) {
                        tab2.setText("Treasurer");
                    } else if (position2 == 1) {
                        tab2.setText("Registrar");
                    } else if (position2 == 2) {
                        tab2.setText("Exam Controller");
                    }
                    else if (position2 == 3) {
                        tab2.setText("Librarian");
                    }
                    else {
                        tab2.setText("Default Tab");
                    }
                }
            }).attach();

        }
        else if(val==3) {
            admi.setVisibility(View.VISIBLE);

        }
        else if(val==4) {
            author.setVisibility(View.VISIBLE);

            ViewPagerAdapter adapter = new ViewPagerAdapter(this);
            viewPager.setAdapter(adapter);

            new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    if (position == 0) {
                        tab.setText("Trust (SIU)");
                    } else if (position == 1) {
                        tab.setText("Syndicate");
                    } else if (position == 2) {
                        tab.setText("Academic Council");
                    }
                    else if (position == 3) {
                        tab.setText("Disciplinary Panel");
                    }
                    else {
                        tab.setText("Default Tab");
                    }
                }
            }).attach();

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