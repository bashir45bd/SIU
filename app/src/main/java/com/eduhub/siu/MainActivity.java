package com.eduhub.siu;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Handler sliderHandler;
    ViewPager2 viewPager;
    MaterialToolbar toolbar;
    NavigationView nav_View;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    BottomNavigationView bottomNavigationView;
    RelativeLayout home, contact, web_site;
    CardView dep, admin, admission, news, event, result, athourity, all_notice;
    TextView address_copy;
    LinearLayout address, contact2, mail;
    LottieAnimationView lottieAnimationView;
    TabLayout tabLayout;
 //   boolean backpress = true;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        nav_View = findViewById(R.id.nav_View);
        drawerLayout = findViewById(R.id.drawer);
        home = findViewById(R.id.home);
        contact = findViewById(R.id.contact);
        web_site = findViewById(R.id.website);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        dep = findViewById(R.id.dep);
        admin = findViewById(R.id.admin);
        admission = findViewById(R.id.admission);
        news = findViewById(R.id.news);
        event = findViewById(R.id.event);
        result = findViewById(R.id.result1);
        athourity = findViewById(R.id.authority);
        all_notice = findViewById(R.id.all_notice);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        address = findViewById(R.id.address);
        address_copy = findViewById(R.id.address_copy);
        contact2 = findViewById(R.id.contact2);
        mail = findViewById(R.id.mail);
        lottieAnimationView = findViewById(R.id.lottieAnimation);

        ConnectivityManager connectivityManager =  (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_f);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }




        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                if (item.getItemId()==R.id.home_tab){

                    home.setVisibility(View.VISIBLE);
                    contact.setVisibility(View.GONE);
                    web_site.setVisibility(View.GONE);


                }
                else if(item.getItemId()==R.id.contact_tab){

                    contact.setVisibility(View.VISIBLE);
                    home.setVisibility(View.GONE);
                    web_site.setVisibility(View.GONE);



                }
                else {

                    web_site.setVisibility(View.VISIBLE);
                    home.setVisibility(View.GONE);
                    contact.setVisibility(View.GONE);

                    if(networkInfo!=null&&networkInfo.isConnected()){
                        // Initialize the WebView
                        webView = findViewById(R.id.webView);
                        WebSettings webSettings = webView.getSettings();
                        webSettings.setJavaScriptEnabled(true); // Enable JavaScript
                        webSettings.setDomStorageEnabled(true); // Enable DOM storage for modern web pages
                        webSettings.setLoadWithOverviewMode(true); // Fit content to screen
                        webSettings.setUseWideViewPort(true); // Enable responsive layout

                        webView.setWebViewClient(new WebViewClient());
                        webView.setWebChromeClient(new WebChromeClient() {
                            @Override
                            public void onProgressChanged(WebView view, int newProgress) {
                                // Hide animation when page loading is complete
                                if (newProgress == 100) {
                                    lottieAnimationView.setVisibility(View.GONE);
                                    webView.setVisibility(View.VISIBLE);
                                }
                            }
                        });

                        // Initially hide WebView and start animation
                        webView.setVisibility(View.GONE);
                        lottieAnimationView.setVisibility(View.VISIBLE);

                        webView.loadUrl("https://siu.edu.bd/");

                        // Handle navigation within WebView

                    }
                    else {
                        no_internet(MainActivity.this);
                    }
                }

                return true;
            }
        });



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


        // Link TabLayout with ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // Optionally set tab content (not necessary for dots)
        }).attach();


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
                else{
                    profile(MainActivity.this);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }

                return false;
            }
        });



        dep.setOnClickListener(v -> {

            Intent nextActivity = new Intent(MainActivity.this, Author_all.class);
            Bundle bundle = new Bundle();
            bundle.putInt("VAL", 1);
            nextActivity.putExtras(bundle);
            startActivity(nextActivity);
        });

        admin.setOnClickListener(v -> {
            Intent nextActivity = new Intent(MainActivity.this, Author_all.class);
            Bundle bundle = new Bundle();
            bundle.putInt("VAL", 2);
            nextActivity.putExtras(bundle);
            startActivity(nextActivity);
        });

        admission.setOnClickListener(v -> {
            Intent nextActivity = new Intent(MainActivity.this, Author_all.class);
            Bundle bundle = new Bundle();
            bundle.putInt("VAL", 3);
            nextActivity.putExtras(bundle);
            startActivity(nextActivity);
        });

        athourity.setOnClickListener(v -> {
            Intent nextActivity = new Intent(MainActivity.this, Author_all.class);
            Bundle bundle = new Bundle();
            bundle.putInt("VAL", 4);
            nextActivity.putExtras(bundle);
            startActivity(nextActivity);
        });

        news.setOnClickListener(v -> {
            Intent nextActivity = new Intent(MainActivity.this, News_all.class);
            Bundle bundle = new Bundle();
            bundle.putInt("VAL", 13);
            nextActivity.putExtras(bundle);
            startActivity(nextActivity);
        });

        event.setOnClickListener(v -> {
            Intent nextActivity = new Intent(MainActivity.this, News_all.class);
            Bundle bundle = new Bundle();
            bundle.putInt("VAL", 11);
            nextActivity.putExtras(bundle);
            startActivity(nextActivity);
        });

        all_notice.setOnClickListener(v -> {
            Intent nextActivity = new Intent(MainActivity.this, News_all.class);
            Bundle bundle = new Bundle();
            bundle.putInt("VAL", 12);
            nextActivity.putExtras(bundle);
            startActivity(nextActivity);
        });



        result.setOnClickListener(v -> {
            Intent nextActivity = new Intent(MainActivity.this, News_all.class);
            Bundle bundle = new Bundle();
            bundle.putInt("VAL", 14);
            nextActivity.putExtras(bundle);
            startActivity(nextActivity);
        });


        address.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(MainActivity.this.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("TextView",address_copy.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(MainActivity.this, "Address copied", Toast.LENGTH_LONG).show();
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



    }
    //==================end========================

    private void profile (Context context) {

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(context).inflate(R.layout.dev_info, null);
        // Set the layout for the BottomSheetDialog
        dialog.setContentView(view);

        view.findViewById(R.id.p_fb).setOnClickListener(v -> {
            String uri = "https://www.facebook.com/bashir45bd";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
            dialog.dismiss();

        });

        view.findViewById(R.id.github).setOnClickListener(v -> {
            String uri = "https://github.com/bashir45soft";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
            dialog.dismiss();

        });

        view.findViewById(R.id.linkdin).setOnClickListener(v -> {
            String uri = "https://www.linkedin.com/in/bashir45bd/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
            dialog.dismiss();

        });

        view.findViewById(R.id.mail2).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto: bashir45.me@gmail.com"));
            startActivity(intent);

        });



        dialog.show();

    }

    private void no_internet (Context context) {
        // Inflate the custom layout
        View view = LayoutInflater.from(context).inflate(R.layout.no_internet, null);

        // Create AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);


        AlertDialog dialog = builder.create();
        view.findViewById(R.id.close).setOnClickListener(v -> {
            dialog.dismiss();


        });


        dialog.show();
    }





    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the back button was pressed and if WebView can navigate back
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack(); // Navigate back
            return true; // Indicate that the event has been handled
        }
        return super.onKeyDown(keyCode, event); // Default back press behavior
    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker at a specific location and move the camera
        LatLng location = new LatLng(24.9015, 91.8446); // Replace with your coordinates
        mMap.addMarker(new MarkerOptions().position(location).title("SIU"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));// Zoom level: 1-20

    }


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
//
//    @Override
//    public void onBackPressed() {
//
//
//        if(backpress){
//            super.onBackPressed();
//        }
//
//        else if (this.backpress=true) {
//
//            Toast.makeText(MainActivity.this, "Press Twice to Exit.", Toast.LENGTH_LONG).show();
//
//            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    backpress=false;
//
//                }
//            },2000);
//
//        }
//
//
//    }


}