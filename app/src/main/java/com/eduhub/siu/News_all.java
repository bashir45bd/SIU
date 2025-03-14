package com.eduhub.siu;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;



public class News_all extends AppCompatActivity {

    RelativeLayout event,notice,epayment,result,vc;
    ImageView eventback,noticeback,newsback,resultback,vcback;
   // TabLayout tabLayout;
   // ViewPager2 viewPager;
  //  private Handler sliderHandler;
    RecyclerView vc_recyclerView,notice_list,news_list;
    ExtendedFloatingActionButton notice_fav,event_fav;

    TextInputEditText title,n_body,news_search,notice_search;
    ImageView n_image,select_image;
    String imageBase64;
    Button n_btn;

    HashMap<String,String> hashMap,notice_map,news_map;
    ArrayList< HashMap<String,String> > arrayList = new ArrayList<>();
    ArrayList< HashMap<String,String> > arrayList1 = new ArrayList<>();
    ArrayList< HashMap<String,String> > arrayList2 = new ArrayList<>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_all);

        event=findViewById(R.id.event1);
        notice=findViewById(R.id.notice1);
        epayment=findViewById(R.id.epayment);
        result=findViewById(R.id.result);
  //      tabLayout = findViewById(R.id.tab_layout);
  //      viewPager = findViewById(R.id.view_pager);
        eventback=findViewById(R.id.eventback);
        noticeback=findViewById(R.id.noticeback);
        notice_search=findViewById(R.id.notice_search);
        news_search=findViewById(R.id.news_search);
        newsback=findViewById(R.id.newsback);
        resultback=findViewById(R.id.resultback);
        vcback=findViewById(R.id.vcback);
        vc=findViewById(R.id.vc);
        vc_recyclerView=findViewById(R.id.vc_list);
        notice_list=findViewById(R.id.notice_list);
        news_list=findViewById(R.id.news_list);
        notice_fav=findViewById(R.id.notice_fav);
        event_fav=findViewById(R.id.event_fav);


        Bundle bun=getIntent().getExtras();
        int val =bun.getInt("VAL");

        if(val==11) {
            event.setVisibility(View.VISIBLE);
            event_fav.setVisibility(View.GONE);

            ArrayRequestforevent();


// TextWatcher for search input
            news_search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String key1 = s.toString().trim(); // Fix: Use s.toString() directly
                    if (!key1.isEmpty()) {
                        search_news(key1);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            sharedPreferences=getSharedPreferences("siu",MODE_PRIVATE);
            String types= sharedPreferences.getString("user_type","");

            if (types.contains("teacher")){

                event_fav.setVisibility(View.VISIBLE);
            }


            event_fav.setOnClickListener(view -> {

                add_event(News_all.this);

            });



            eventback.setOnClickListener(v -> {
                onBackPressed();
            });


        }
        else if(val==12) {

            notice.setVisibility(View.VISIBLE);
            notice_fav.setVisibility(View.GONE);

            ArrayRequest();


            notice_fav.setOnClickListener(view -> {

              add_notice(News_all.this);

            });

            sharedPreferences=getSharedPreferences("siu",MODE_PRIVATE);
            String types= sharedPreferences.getString("user_type","");

            if (types.contains("teacher")){

                notice_fav.setVisibility(View.VISIBLE);
            }


//===========================silder=======================================

//            List<SliderItem> sliderItems = new ArrayList<>();
//        sliderItems.add(new SliderItem("https://siu.edu.bd/storage/uploaded/notice/banner_1707320890.jpg";
//        sliderItems.add(new SliderItem("https://siu.edu.bd/storage/uploaded/notice/banner_1707320890.jpg", "Title 3"));
//        sliderItems.add(new SliderItem("https://siu.edu.bd/storage/uploaded/notice/unnamed_1697551273.jpg", "Title 4"));
//        sliderItems.add(new SliderItem("https://siu.edu.bd/storage/uploaded/notice/Notice_1696530836.jpg", "Title 5"));
//
//        SliderAdapter adapter = new SliderAdapter(this, sliderItems);
//        viewPager.setAdapter(adapter);
//
//
//
//        // Auto-scroll
//        sliderHandler = new Handler(Looper.getMainLooper());
//        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                sliderHandler.removeCallbacks(sliderRunnable);
//                sliderHandler.postDelayed(sliderRunnable, 3000); // Auto-scroll every 3 seconds
//            }
//        });
//
//
//        // Link TabLayout with ViewPager2
//        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
//            // Optionally set tab content (not necessary for dots)
//        }).attach();
//===========================silder=======================================

            noticeback.setOnClickListener(v -> {
                onBackPressed();
            });


        }

        else if(val==13) {
            epayment.setVisibility(View.VISIBLE);




            newsback.setOnClickListener(v -> {
                onBackPressed();
            });

        }
        else if(val==14) {
            result.setVisibility(View.VISIBLE);





            resultback.setOnClickListener(v -> {
                onBackPressed();
            });
        }
        else if(val==15) {

            vc.setVisibility(View.VISIBLE);


            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("vc");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        String title = snapshot.child("title").getValue(String.class);
                        String name = snapshot.child("name").getValue(String.class);
                        String pic = snapshot.child("pic").getValue(String.class);
                        String des = snapshot.child("des").getValue(String.class);



                        hashMap= new HashMap<>();
                        hashMap.put("atitle",title);
                        hashMap.put("aname",name);
                        hashMap.put("apic",pic);
                        hashMap.put("ades",des);
                        arrayList.add(hashMap);


                    }

                    vc_list adapter = new vc_list();
                    vc_recyclerView.setAdapter(adapter);
                    vc_recyclerView.setLayoutManager(new LinearLayoutManager(News_all.this));

                }

                @Override
                public void onCancelled(DatabaseError error) {

                    Log.w("MainActivity", "Failed to read value.", error.toException());
                }
            });



            vcback.setOnClickListener(v -> {
                onBackPressed();

            });


        }








    }


    private class vc_list extends RecyclerView.Adapter<vc_list.viewholder>{


        private class viewholder extends RecyclerView.ViewHolder{

            ImageView vcpic;
            TextView name1,pro1;
            CardView readmore;


            public viewholder(@NonNull View itemView) {
                super(itemView);
                vcpic=itemView.findViewById(R.id.vcpic);
                name1=itemView.findViewById(R.id.name1);
                pro1=itemView.findViewById(R.id.pro1);
                readmore=itemView.findViewById(R.id.readmore);

            }
        }

        @NonNull
        @Override
        public vc_list.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.vcitem, parent, false);

            return new vc_list.viewholder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull vc_list.viewholder holder, int position) {

            HashMap<String,String> hashMap=arrayList.get(position);
            String ftitle = hashMap.get("atitle");
            String fname = hashMap.get("aname");
            String fdes = hashMap.get("ades");
            String fpic = hashMap.get("apic");



            holder.pro1.setText(ftitle);
            holder.name1.setText(fname);


            Picasso.get()
                    .load(""+fpic)
                    .placeholder(R.drawable.itempic)
                    .error(R.drawable.itempic)
                    .into(holder.vcpic);



            holder.readmore.setOnClickListener(v -> {


                Map.title=ftitle;
                Map.descrpition=fdes;
                Bitmap bitmap = ((BitmapDrawable) holder.vcpic.getDrawable()).getBitmap();
                Map.bitmap=bitmap;

                Intent nextActivity = new Intent(News_all.this, Map.class);
                Bundle bundle = new Bundle();
                bundle.putInt("VAL", 42);
                nextActivity.putExtras(bundle);
                startActivity(nextActivity);

            });




        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }





    }

    //=========================slider=============================================

//    private final Runnable sliderRunnable = new Runnable() {
//        @Override
//        public void run() {
//            int currentItem = viewPager.getCurrentItem();
//            int nextItem = (currentItem + 1) % viewPager.getAdapter().getItemCount();
//            viewPager.setCurrentItem(nextItem, true);
//        }
//    };

//    public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ViewHolder> {
//
//        private final List<SliderItem> sliderItems;
//        private final Context context;
//
//        public SliderAdapter(Context context, List<SliderItem> sliderItems) {
//            this.context = context;
//            this.sliderItems = sliderItems;
//        }
//
//        @NonNull
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.slider_item, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//            SliderItem item = sliderItems.get(position);
//            holder.titleTextView.setText(item.getTitle());
//            Glide.with(context).load(item.getImageUrl()).into(holder.imageView);
//
//            holder.imageView.setOnClickListener(v -> {
//                Toast.makeText(News_all.this,"Coming soon",Toast.LENGTH_LONG).show();
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return sliderItems.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            ImageView imageView;
//            TextView titleTextView;
//
//            public ViewHolder(@NonNull View itemView) {
//                super(itemView);
//                imageView = itemView.findViewById(R.id.imageView);
//                titleTextView = itemView.findViewById(R.id.titleTextView);
//            }
//        }
//    }


    private class notice_list extends RecyclerView.Adapter<notice_list.viewholder>{


        private class viewholder extends RecyclerView.ViewHolder{

            ImageView notice_pic;
            LinearLayout notice_item;
            TextView notice_title,all_notice,time;


            public viewholder(@NonNull View itemView) {
                super(itemView);
                notice_item=itemView.findViewById(R.id.notice_item);
                notice_pic=itemView.findViewById(R.id.notice_pic);
                notice_title=itemView.findViewById(R.id.notice_title);
                all_notice=itemView.findViewById(R.id.all_notice);
                time=itemView.findViewById(R.id.notice_time);


            }
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.notice_card, parent, false);

            return new notice_list.viewholder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull viewholder holder, int position) {

            HashMap<String,String>hashMap=arrayList1.get(position);
            String id = hashMap.get("id");
            String title = hashMap.get("title");
            String all_notice = hashMap.get("all_notice");
            String pic = hashMap.get("pic");
            String time = hashMap.get("s_time");


            holder.notice_title.setText(title);
            holder.all_notice.setText(all_notice);

            double a_time = Double.parseDouble(time);
            SimpleDateFormat simpletimeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            SimpleDateFormat simpledateFormat = new SimpleDateFormat("dd MMM yyyy ", Locale.getDefault());

            String stime = simpletimeFormat.format(a_time);
            String date = simpledateFormat.format(a_time);
            holder.time.setText(stime+" - "+date);

            Picasso.get()
                    .load(""+pic)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.notice_pic);

            holder.notice_item.setOnClickListener(v -> {



                Map.notice=all_notice;
                Bitmap bitmap1 = ((BitmapDrawable) holder.notice_pic.getDrawable()).getBitmap();
                Map.notice_pic1=bitmap1;

                Intent nextActivity = new Intent(News_all.this, Map.class);
                Bundle bundle = new Bundle();
                bundle.putInt("VAL", 43);
                bundle.putString("id", id);
                bundle.putString("title", title);
                bundle.putString("body", all_notice);
                bundle.putString("image", pic);
                nextActivity.putExtras(bundle);
                startActivity(nextActivity);

            });

//            double a_time = Double.parseDouble(f_time);
//            SimpleDateFormat simpletimeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
//            SimpleDateFormat simpledateFormat = new SimpleDateFormat("dd MMM yyyy ", Locale.getDefault());
//
//            String stime = simpletimeFormat.format(a_time);
//            String date = simpledateFormat.format(a_time);






        }

        @Override
        public int getItemCount() {
            return arrayList1.size();
        }





    }

    private class news_list extends RecyclerView.Adapter<news_list.viewholder1>{


        private class viewholder1 extends RecyclerView.ViewHolder{

            ImageView notice_pic;
            LinearLayout notice_item;
            TextView notice_title,all_notice,time;


            public viewholder1(@NonNull View itemView) {
                super(itemView);
                notice_item=itemView.findViewById(R.id.notice_item);
                notice_pic=itemView.findViewById(R.id.notice_pic);
                notice_title=itemView.findViewById(R.id.notice_title);
                all_notice=itemView.findViewById(R.id.all_notice);
                time=itemView.findViewById(R.id.notice_time);


            }
        }

        @NonNull
        @Override
        public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.news_item, parent, false);

            return new news_list.viewholder1(view);
        }


        @Override
        public void onBindViewHolder(@NonNull viewholder1 holder, int position) {

            HashMap<String,String>hashMap=arrayList2.get(position);
            String id = hashMap.get("id");
            String title = hashMap.get("title");
            String all_notice = hashMap.get("all_notice");
            String pic = hashMap.get("pic");
            String time = hashMap.get("time");


            holder.notice_title.setText(title);
            holder.all_notice.setText(all_notice);
            holder.time.setText(time);

            Picasso.get()
                    .load(""+pic)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.notice_pic);

            holder.notice_item.setOnClickListener(v -> {



                Map.notice=all_notice;
                Bitmap bitmap1 = ((BitmapDrawable) holder.notice_pic.getDrawable()).getBitmap();
                Map.notice_pic1=bitmap1;

                Intent nextActivity = new Intent(News_all.this, Map.class);
                Bundle bundle = new Bundle();
                bundle.putInt("VAL", 43);
                bundle.putString("id", id);
                bundle.putString("title", title);
                bundle.putString("body", all_notice);
                bundle.putString("image", pic);
                nextActivity.putExtras(bundle);
                startActivity(nextActivity);

            });

//            double a_time = Double.parseDouble(f_time);
//            SimpleDateFormat simpletimeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
//            SimpleDateFormat simpledateFormat = new SimpleDateFormat("dd MMM yyyy ", Locale.getDefault());
//
//            String stime = simpletimeFormat.format(a_time);
//            String date = simpledateFormat.format(a_time);






        }

        @Override
        public int getItemCount() {
            return arrayList2.size();
        }


    }


    private void String_Request(String title,String n_body, String n_image){


        String url = "http://192.168.1.104/SIU/notice.php";

        if (n_image == null || n_image.isEmpty()) {
            n_image = "";
        }


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String finalN_image = n_image;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {

                    if (response.contains("Insert Successful")){

                        new AlertDialog.Builder(News_all.this)
                                .setTitle("Uploaded!")
                                .setMessage("Insert Successful")
                                .setNegativeButton("Thank You!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();



                    } else {
                        new AlertDialog.Builder(News_all.this)
                                .setTitle("Failed!")
                                .setMessage("Insert Not Successful")
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

                java.util.Map<String, String> map = new HashMap<>();

                map.put("title", title);
                map.put("n_image", finalN_image);
                map.put("n_body", n_body);
                map.put("type", "notice");
                map.put("time", String.valueOf(System.currentTimeMillis()));
                map.put("key",Encyption.Mykey);

                return map;
            }
        };

        requestQueue.add(postRequest);
    }

    private void String_Requestforevent(String title,String n_body, String n_image){


        String url = "http://192.168.1.104/SIU/notice.php";

        if (n_image == null || n_image.isEmpty()) {
            n_image = "";
        }


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String finalN_image = n_image;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {

                    if (response.contains("Insert Successful")){

                        new AlertDialog.Builder(News_all.this)
                                .setTitle("Uploaded!")
                                .setMessage("Insert Successful")
                                .setNegativeButton("Thank You!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();



                    } else {
                        new AlertDialog.Builder(News_all.this)
                                .setTitle("Failed!")
                                .setMessage("Insert Not Successful")
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

                java.util.Map<String, String> map = new HashMap<>();

                map.put("title", title);
                map.put("n_image", finalN_image);
                map.put("n_body", n_body);
                map.put("type", "event");
                map.put("time", String.valueOf(System.currentTimeMillis()));
                map.put("key",Encyption.Mykey);

                return map;
            }
        };

        requestQueue.add(postRequest);
    }

    private void add_notice(Context context) {

        BottomSheetDialog dialog = new BottomSheetDialog(this);


        View view = getLayoutInflater().inflate(R.layout.add_notice, null);
        dialog.setContentView(view);
        n_image=view.findViewById(R.id.n_image);
        select_image=view.findViewById(R.id.select_image);
        title=view.findViewById(R.id.n_title);
        n_body=view.findViewById(R.id.n_body);
        n_btn=view.findViewById(R.id.n_btn);


        select_image.setOnClickListener(view1 -> {


            ImagePicker.with(this)
                    .crop()         // Enables cropping
                    .compress(1024) // Compress image
                    .start();

        });

        n_btn.setOnClickListener(view1 -> {

            if (title.length()>0&&n_body.length()>0){

                String f_title= title.getText().toString();
                String f_body= n_body.getText().toString();

                String_Request(f_title,f_body,imageBase64);
                dialog.dismiss();

                finish();
                startActivity(getIntent());


            }else {
                new AlertDialog.Builder(News_all.this)
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

    private void add_event(Context context) {

        BottomSheetDialog dialog = new BottomSheetDialog(this);


        View view = getLayoutInflater().inflate(R.layout.add_notice, null);
        dialog.setContentView(view);
        n_image=view.findViewById(R.id.n_image);
        select_image=view.findViewById(R.id.select_image);
        title=view.findViewById(R.id.n_title);
        n_body=view.findViewById(R.id.n_body);
        n_btn=view.findViewById(R.id.n_btn);


        select_image.setOnClickListener(view1 -> {

            ImagePicker.with(this)
                    .crop()         // Enables cropping
                    .compress(1024) // Compress image
                    .start();

        });

        n_btn.setOnClickListener(view1 -> {

            if (title.length()>0&&n_body.length()>0){

                String f_title= title.getText().toString();
                String f_body= n_body.getText().toString();

                String_Requestforevent(f_title,f_body,imageBase64);
                dialog.dismiss();

                finish();
                startActivity(getIntent());


            }else {
                new AlertDialog.Builder(News_all.this)
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
                n_image.setImageBitmap(bitmap);
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


    private void ArrayRequest() {
        String url = "http://192.168.1.104/SIU/notice_get.php"; // Update with actual URL

        // Create JSON payload
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("key", Encyption.Mykey);
            jsonRequest.put("type","notice");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, jsonRequest,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.has("notices")) {
                                JSONArray noticesArray = response.getJSONArray("notices");

                                for (int x = 0; x < noticesArray.length(); x++) {
                                    JSONObject object = noticesArray.getJSONObject(x);

                                    HashMap<String, String> noticeMap = new HashMap<>();
                                    noticeMap.put("id", object.getString("id"));
                                    noticeMap.put("title", object.getString("title"));
                                    noticeMap.put("all_notice", object.getString("notice_body"));
                                    noticeMap.put("s_time",object.getString("time"));
                                    noticeMap.put("pic", object.getString("n_image"));

                                    arrayList1.add(noticeMap);
                                }

                                if (!arrayList1.isEmpty()) {
                                    notice_list adapter = new notice_list();
                                    notice_list.setAdapter(adapter);
                                    notice_list.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                                }

                            } else {
                                Log.e("Response Error", "No 'notices' key in the response");
                                Toast.makeText(getApplicationContext(), "No notices found!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void ArrayRequestforevent() {
        String url = "http://192.168.1.104/SIU/notice_get.php"; // Update with actual URL

        // Create JSON payload
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("key", Encyption.Mykey);
            jsonRequest.put("type","event");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, jsonRequest,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.has("notices")) {
                                JSONArray noticesArray = response.getJSONArray("notices");

                                for (int x = 0; x < noticesArray.length(); x++) {
                                    JSONObject object = noticesArray.getJSONObject(x);

                                    HashMap<String, String> noticeMap2 = new HashMap<>();
                                    noticeMap2.put("id", object.getString("id"));
                                    noticeMap2.put("title", object.getString("title"));
                                    noticeMap2.put("all_notice", object.getString("notice_body"));
                                    noticeMap2.put("s_time",object.getString("time"));
                                    noticeMap2.put("pic", object.getString("n_image"));

                                    arrayList2.add(noticeMap2);
                                }

                                if (!arrayList2.isEmpty()) {
                                    news_list adapter6 = new news_list();
                                    news_list.setAdapter(adapter6);
                                    news_list.setLayoutManager(new LinearLayoutManager(News_all.this));
                                }

                            } else {
                                Log.e("Response Error", "No 'notices' key in the response");
                                Toast.makeText(getApplicationContext(), "No notices found!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void search_news(String data) {
        String url = "http://192.168.1.104/SIU/search.php"; // Update with actual URL

        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("key", Encyption.Mykey);
            jsonRequest.put("search", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, jsonRequest,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            news_list.setVisibility(View.VISIBLE);

                            JSONArray noticesArray = response.getJSONArray("notices");

                            for (int x = 0; x < noticesArray.length(); x++) {
                                JSONObject object = noticesArray.getJSONObject(x);

                                // Filter data by "types"
                                if (object.has("type") && object.getString("type").equals("event")) {
                                    HashMap<String, String> noticeMap2 = new HashMap<>();
                                    noticeMap2.put("id", object.getString("id"));
                                    noticeMap2.put("title", object.getString("title"));
                                    noticeMap2.put("all_notice", object.getString("notice_body"));
                                    noticeMap2.put("s_time", object.getString("time"));
                                    noticeMap2.put("pic", object.getString("n_image"));

                                    arrayList2.add(noticeMap2);
                                }
                            }

                            // Update UI
                            if (!arrayList2.isEmpty()) {
                                news_list adapter6 = new news_list();
                                news_list.setAdapter(adapter6);
                                news_list.setLayoutManager(new LinearLayoutManager(News_all.this));
                            } else {
                                news_list.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "No event notices found!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSON Parsing Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }



}