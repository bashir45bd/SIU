package com.eduhub.siu;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity{

   // WebView webView;
    TextView tvUserName,tvUserEmail,bio;
    ImageView tvprofile;
    MaterialToolbar toolbar;
    NavigationView nav_View;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    BottomNavigationView bottomNavigationView;
    RelativeLayout home, profile_page;
    CardView dep, admin, admission, epayment, event, result, athourity, all_notice,logout2,Update_profile;
    LottieAnimationView lottieAnimationView;
    SharedPreferences sharedPreferences;

    String a_name,a_bio,a_session,a_roll,a_reg,a_fblink,a_phone,a_dep,a_mail,a_image,a_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        nav_View = findViewById(R.id.nav_View);
        drawerLayout = findViewById(R.id.drawer);
        home = findViewById(R.id.home);
        profile_page = findViewById(R.id.profile_page);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        dep = findViewById(R.id.dep);
        logout2 = findViewById(R.id.logout2);
        admin = findViewById(R.id.admin);
        admission = findViewById(R.id.admission);
        epayment = findViewById(R.id.epayment);
        event = findViewById(R.id.event);
        result = findViewById(R.id.result1);
        athourity = findViewById(R.id.authority);
        all_notice = findViewById(R.id.all_notice);
        bio= findViewById(R.id.bio);
        tvUserName= findViewById(R.id.tvUserName);
        Update_profile= findViewById(R.id.Update_profile);
        tvUserEmail= findViewById(R.id.tvUserEmail);
        tvprofile= findViewById(R.id.tvprofile);
        lottieAnimationView = findViewById(R.id.lottieAnimation);

        ConnectivityManager connectivityManager =  (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        Encyption.Mykey=Encyption.EncryptData("2021");


        if(networkInfo!=null&&networkInfo.isConnected()){


            sharedPreferences=getSharedPreferences("siu",MODE_PRIVATE);
            String email= sharedPreferences.getString("email","");
            if (email.length()<=0){


                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //Code here
                        Intent myIntent = new Intent(MainActivity.this, SignInPage.class);
                        startActivity(myIntent);
                        finish();



                    }
                },1500);


            }
            else {
                ObjectRequest();
            }






            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                    if (item.getItemId()==R.id.home_tab){

                        home.setVisibility(View.VISIBLE);
                        profile_page.setVisibility(View.GONE);


                    }
                    else if(item.getItemId()==R.id.contact_tab){

                        Intent nextActivity = new Intent(MainActivity.this, Map.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("VAL", 41);
                        nextActivity.putExtras(bundle);
                        startActivity(nextActivity);


                    }
                    else {


                        profile_page.setVisibility(View.VISIBLE);
                        home.setVisibility(View.GONE);



                        Update_profile.setOnClickListener(view -> {

                            Intent nextActivity = new Intent(MainActivity.this, Profile.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("name", a_name);
                            bundle.putString("bio", a_bio);
                            bundle.putString("session", a_session);
                            bundle.putString("reg", a_reg);
                            bundle.putString("roll", a_roll);
                            bundle.putString("phone", a_phone);
                            bundle.putString("mail", a_mail);
                            bundle.putString("fblink", a_fblink);
                            bundle.putString("dep", a_dep);
                            bundle.putString("image", a_image);
                            bundle.putString("id", a_id);
                            nextActivity.putExtras(bundle);
                            startActivityForResult(nextActivity, 1);




                        });


                        logout2.setOnClickListener(view -> {

                            sharedPreferences= getSharedPreferences("siu",MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("email","");
                            editor.putString("user_type","");
                            editor.apply();

                            //Code here
                            Intent myIntent = new Intent(MainActivity.this, SignInPage.class);
                            startActivity(myIntent);
                            finish();

                        });



                        //=================webviewcode

//                    web_site.setVisibility(View.VISIBLE);
//                    home.setVisibility(View.GONE);
//
//                    if(networkInfo!=null&&networkInfo.isConnected()){
//                        // Initialize the WebView
//                        webView = findViewById(R.id.webView);
//                        WebSettings webSettings = webView.getSettings();
//                        webSettings.setJavaScriptEnabled(true); // Enable JavaScript
//                        webSettings.setDomStorageEnabled(true); // Enable DOM storage for modern web pages
//                        webSettings.setLoadWithOverviewMode(true); // Fit content to screen
//                        webSettings.setUseWideViewPort(true); // Enable responsive layout
//
//                        webView.setWebViewClient(new WebViewClient());
//                        webView.setWebChromeClient(new WebChromeClient() {
//                            @Override
//                            public void onProgressChanged(WebView view, int newProgress) {
//                                // Hide animation when page loading is complete
//                                if (newProgress == 100) {
//                                    lottieAnimationView.setVisibility(View.GONE);
//                                    webView.setVisibility(View.VISIBLE);
//                                }
//                            }
//                        });
//
//                        // Initially hide WebView and start animation
//                        webView.setVisibility(View.GONE);
//                        lottieAnimationView.setVisibility(View.VISIBLE);
//
//                        webView.loadUrl("https://siu.edu.bd/");
//
//                        // Handle navigation within WebView
//
//                    }
//                    else {
//                        check_internet.no_internet(MainActivity.this);
//                    }
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

                    else if(item.getItemId()==R.id.dev){
                        profile(MainActivity.this);
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    else if (item.getItemId()==R.id.logout){


                        sharedPreferences= getSharedPreferences("siu",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("email","");
                        editor.putString("user_type","");
                        editor.apply();

                        //Code here
                        Intent myIntent = new Intent(MainActivity.this, SignInPage.class);
                        startActivity(myIntent);
                        finish();


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




        }else no_internet(MainActivity.this);


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


//=================webviewcode
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // Check if the back button was pressed and if WebView can navigate back
//        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
//            webView.goBack(); // Navigate back
//            return true; // Indicate that the event has been handled
//        }
//        return super.onKeyDown(keyCode, event); // Default back press behavior
//    }
//


    private void ObjectRequest() {

        String url = "http://192.168.1.104/SIU/Objectrequest.php"; // Replace with your API URL

        // Creating JSON Object
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("mail", sharedPreferences.getString("email", ""));
            jsonRequest.put("key", Encyption.Mykey);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Making a POST request with JSON object
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, jsonRequest,
                response -> {
                    try {
                        String name_s=response.getString("name");
                        String mail_s=response.getString("email");
                        String image_s=response.getString("image");
                        String roll_s=response.getString("roll");
                        String reg_s=response.getString("reg");
                        String session_s=response.getString("session");
                        String dep_s=response.getString("dep");
                        String fblink_s=response.getString("fblink");
                        String bio_s=response.getString("bio");
                        String phone_s=response.getString("phone");
                        String pos_s=response.getString("pos");
                        String user_type_s=response.getString("user_type");
                        String id_s=response.getString("id");

                        a_name=name_s;
                        a_bio=bio_s;
                        a_session=session_s;
                        a_dep=dep_s;
                        a_reg=reg_s;
                        a_roll=roll_s;
                        a_fblink=fblink_s;
                        a_phone=phone_s;
                        a_mail=mail_s;
                        a_image=image_s;
                        a_id=id_s;
                        tvUserName.setText(name_s);
                        tvUserEmail.setText(mail_s);
                        bio.setText(bio_s);

                        SharedPreferences sharedPreferences= getSharedPreferences("siu",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("user_type",user_type_s);
                        editor.apply();

                        Picasso.get()
                                .load(""+image_s)
                                .placeholder(R.drawable.itempic)
                                .error(R.drawable.itempic)
                                .into(tvprofile);

                        setDrawerHeaderData(name_s,mail_s,image_s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("ObjectRequest", "JSON Parsing Error: " + e.getMessage());
                    }
                },
                error -> {
                    // Handle error
                    String errorMessage;
                    if (error.networkResponse != null) {
                        errorMessage = "Error Code: " + error.networkResponse.statusCode;
                    } else {
                        errorMessage = error.getMessage();
                    }

                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Failed!")
                            .setMessage(errorMessage)
                            .setNegativeButton("Thank You!", (dialog, which) -> dialog.dismiss())
                            .show();
                });

        // Adding request to queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Refresh MainActivity data
            recreate(); // Restart activity to load updated data
        }
    }


    private void setDrawerHeaderData(String name,String email, String image) {
        View headerView = nav_View.getHeaderView(0);

        // Find header views
        ImageView profileImage = headerView.findViewById(R.id.pic);
        TextView userName = headerView.findViewById(R.id.name);
        TextView userEmail = headerView.findViewById(R.id.email);

        userName.setText(name);
        userEmail.setText(email);

        Picasso.get()
                .load(""+image)
                .placeholder(R.drawable.itempic)
                .error(R.drawable.itempic)
                .into(profileImage);

    }



    public void no_internet(Context context) {
        // Inflate the custom layout
        View view = LayoutInflater.from(context).inflate(R.layout.no_internet, null);

        // Create AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);


        AlertDialog dialog = builder.create();
        view.findViewById(R.id.close).setOnClickListener(v -> {

            dialog.dismiss();
            onBackPressed();


        });

        dialog.show();
    }


}