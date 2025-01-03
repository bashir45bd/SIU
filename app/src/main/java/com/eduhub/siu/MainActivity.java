package com.eduhub.siu;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;




public class MainActivity extends AppCompatActivity{

    WebView webView;
    MaterialToolbar toolbar;
    NavigationView nav_View;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    BottomNavigationView bottomNavigationView;
    RelativeLayout home, web_site;
    CardView dep, admin, admission, epayment, event, result, athourity, all_notice;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        nav_View = findViewById(R.id.nav_View);
        drawerLayout = findViewById(R.id.drawer);
        home = findViewById(R.id.home);
        web_site = findViewById(R.id.website);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        dep = findViewById(R.id.dep);
        admin = findViewById(R.id.admin);
        admission = findViewById(R.id.admission);
        epayment = findViewById(R.id.epayment);
        event = findViewById(R.id.event);
        result = findViewById(R.id.result1);
        athourity = findViewById(R.id.authority);
        all_notice = findViewById(R.id.all_notice);
        webView= findViewById(R.id.webView);
        lottieAnimationView = findViewById(R.id.lottieAnimation);


        ConnectivityManager connectivityManager =  (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();






        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                if (item.getItemId()==R.id.home_tab){

                    home.setVisibility(View.VISIBLE);
                    web_site.setVisibility(View.GONE);


                }
                else if(item.getItemId()==R.id.contact_tab){

                    Intent nextActivity = new Intent(MainActivity.this, Map.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("VAL", 41);
                    nextActivity.putExtras(bundle);
                    startActivity(nextActivity);


                }
                else {

                    web_site.setVisibility(View.VISIBLE);
                    home.setVisibility(View.GONE);

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

                else if (item.getItemId()==R.id.vctab){

                    Intent nextActivity = new Intent(MainActivity.this, News_all.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("VAL", 15);
                    nextActivity.putExtras(bundle);
                    startActivity(nextActivity);

                    drawerLayout.closeDrawer(GravityCompat.START);
                }




                else if (item.getItemId()==R.id.more){

                    String devmane = "abcd";
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

        epayment.setOnClickListener(v -> {
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




}