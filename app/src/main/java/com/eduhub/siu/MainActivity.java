package com.eduhub.siu;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Handler sliderHandler;
    ViewPager2 viewPager;
    MaterialToolbar toolbar;
    NavigationView nav_View;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    BottomNavigationView bottomNavigationView;
    RelativeLayout home,contact,web_site;
    CardView dep,admin,admission,news,event,result,cardview7,all_notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        nav_View = findViewById(R.id.nav_View);
        drawerLayout =findViewById(R.id.drawer);
        home = findViewById(R.id.home);
        contact=findViewById(R.id.contact);
        web_site=findViewById(R.id.website);
        bottomNavigationView=findViewById(R.id.bottom_nav);
        dep=findViewById(R.id.dep);
        admin=findViewById(R.id.admin);
        admission=findViewById(R.id.admission);
        news=findViewById(R.id.news);
        event=findViewById(R.id.event);
        result=findViewById(R.id.result1);
        cardview7=findViewById(R.id.cardview7);
        all_notice=findViewById(R.id.all_notice);
        viewPager = findViewById(R.id.viewPager);







// Create slider items
        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem("https://siu.edu.bd/storage/slider/2024-04-16-661edd32bc219.jpg", "Title 1"));
        sliderItems.add(new SliderItem("https://siu.edu.bd/storage/slider/2024-02-18-65d259e5d35cf.jpg", "Title 2"));
        sliderItems.add(new SliderItem("https://siu.edu.bd/storage/slider/2024-02-18-65d259c9bfed3.jpg", "Title 3"));
        sliderItems.add(new SliderItem("https://siu.edu.bd/storage/slider/2024-04-16-661edd0f94e40.jpg", "Title 4"));
        sliderItems.add(new SliderItem("https://siu.edu.bd/storage/slider/2024-02-18-65d25a8583fea.jpg", "Title 5"));

        SliderAdapter adapter = new SliderAdapter(this, sliderItems);
        viewPager.setAdapter(adapter);



        // Auto-scroll
        sliderHandler = new Handler(Looper.getMainLooper());
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000); // Auto-scroll every 3 seconds
            }
        });




        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout,toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);


        nav_View.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                if (item.getItemId()==R.id.shareapp){



                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if (item.getItemId()==R.id.rateapp){

                    Context context=MainActivity.this;
                    final String apppn = context.getPackageName();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id="+apppn));
                    startActivity(intent);

                    try {

                    } catch (ActivityNotFoundException e){

                        Intent intent1 = new Intent(Intent.ACTION_VIEW);
                        intent1.setData(Uri.parse("https://play.google.com/store/apps/details?id="+apppn));
                        startActivity(intent1);

                    }

                    drawerLayout.closeDrawer(GravityCompat.START);
                }



                else if (item.getItemId()==R.id.policy){

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://sites.google.com/view/extulprivacy-policy"));
                    startActivity(intent);



                    drawerLayout.closeDrawer(GravityCompat.START);
                }




                else if (item.getItemId()==R.id.more){

                    String devmane = "Orion Craft";
                    try {

                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub: "+devmane)));

                    } catch (ActivityNotFoundException e){

                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id= "+devmane)));

                    }

                    drawerLayout.closeDrawer(GravityCompat.START);
                }

                return false;
            }
        });


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                if (item.getItemId()==R.id.home_tab){
                    home.setVisibility(View.VISIBLE);
                    contact.setVisibility(View.GONE);
                    web_site.setVisibility(View.GONE);


                }
                else if(item.getItemId()==R.id.contact_tab){

                    home.setVisibility(View.GONE);
                    contact.setVisibility(View.VISIBLE);
                    web_site.setVisibility(View.GONE);
                }
                else {
                    home.setVisibility(View.GONE);
                    contact.setVisibility(View.GONE);
                    web_site.setVisibility(View.VISIBLE);
                }

                return true;
            }
        });

        dep.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this,"DEP",Toast.LENGTH_LONG).show();
        });

        admin.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this,"Admin",Toast.LENGTH_LONG).show();
        });

        admission.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this,"Admission",Toast.LENGTH_LONG).show();
        });

        news.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this,"news",Toast.LENGTH_LONG).show();
        });

        event.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this,"event",Toast.LENGTH_LONG).show();
        });

        all_notice.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this,"notice",Toast.LENGTH_LONG).show();
        });

        cardview7.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this,"7",Toast.LENGTH_LONG).show();
        });

        result.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this,"Coming soon",Toast.LENGTH_LONG).show();
        });


    }//==================end========================

    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = viewPager.getCurrentItem();
            int nextItem = (currentItem + 1) % viewPager.getAdapter().getItemCount();
            viewPager.setCurrentItem(nextItem, true);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sliderHandler.removeCallbacks(sliderRunnable);
    }


    public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ViewHolder> {

        private final List<SliderItem> sliderItems;
        private final Context context;

        public SliderAdapter(Context context, List<SliderItem> sliderItems) {
            this.context = context;
            this.sliderItems = sliderItems;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.slider_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            SliderItem item = sliderItems.get(position);
            holder.titleTextView.setText(item.getTitle());
            Glide.with(context).load(item.getImageUrl()).into(holder.imageView);

            holder.imageView.setOnClickListener(v -> {
                Toast.makeText(MainActivity.this,"Coming soon",Toast.LENGTH_LONG).show();
            });
        }

        @Override
        public int getItemCount() {
            return sliderItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView titleTextView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                titleTextView = itemView.findViewById(R.id.titleTextView);
            }
        }
    }
}