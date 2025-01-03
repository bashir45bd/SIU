package com.eduhub.siu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class News_all extends AppCompatActivity {

    RelativeLayout event,notice,epayment,result,vc;
    ImageView eventback,noticeback,newsback,resultback,vcback;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    private Handler sliderHandler;
    RecyclerView vc_recyclerView,notice_list,news_list;

    HashMap<String,String> hashMap,notice_map,news_map;
    ArrayList< HashMap<String,String> > arrayList = new ArrayList<>();
    ArrayList< HashMap<String,String> > arrayList1 = new ArrayList<>();
    ArrayList< HashMap<String,String> > arrayList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_all);

        event=findViewById(R.id.event1);
        notice=findViewById(R.id.notice1);
        epayment=findViewById(R.id.epayment);
        result=findViewById(R.id.result);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        eventback=findViewById(R.id.eventback);
        noticeback=findViewById(R.id.noticeback);
        newsback=findViewById(R.id.newsback);
        resultback=findViewById(R.id.resultback);
        vcback=findViewById(R.id.vcback);
        vc=findViewById(R.id.vc);
        vc_recyclerView=findViewById(R.id.vc_list);
        notice_list=findViewById(R.id.notice_list);
        news_list=findViewById(R.id.news_list);


        Bundle bun=getIntent().getExtras();
        int val =bun.getInt("VAL");

        if(val==11) {
            event.setVisibility(View.VISIBLE);




            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("notice");
            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot2) {


                    for (DataSnapshot snapshot2 : dataSnapshot2.getChildren()) {

                        String title = snapshot2.child("title").getValue(String.class);
                        String all_notice = snapshot2.child("all_notice").getValue(String.class);
                        String pic = snapshot2.child("pic").getValue(String.class);
                        String time = snapshot2.child("time").getValue(String.class);



                        news_map= new HashMap<>();
                        news_map.put("title",title);
                        news_map.put("all_notice",all_notice);
                        news_map.put("pic",pic);
                        news_map.put("time",time);
                        arrayList2.add(news_map);


                    }

                    news_list adapter6 = new news_list();
                    news_list.setAdapter(adapter6);
                    news_list.setLayoutManager(new LinearLayoutManager(News_all.this));

                }

                @Override
                public void onCancelled(DatabaseError error) {

                    Log.w("MainActivity", "Failed to read value.", error.toException());
                }
            });



            eventback.setOnClickListener(v -> {
                onBackPressed();
            });


        }
        else if(val==12) {

            notice.setVisibility(View.VISIBLE);




            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("notice");
            databaseReference1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot1) {


                    for (DataSnapshot snapshot1 : dataSnapshot1.getChildren()) {

                        String title = snapshot1.child("title").getValue(String.class);
                        String all_notice = snapshot1.child("all_notice").getValue(String.class);
                        String pic = snapshot1.child("pic").getValue(String.class);
                        String time = snapshot1.child("time").getValue(String.class);



                        notice_map= new HashMap<>();
                        notice_map.put("title",title);
                        notice_map.put("all_notice",all_notice);
                        notice_map.put("pic",pic);
                        notice_map.put("time",time);
                        arrayList1.add(notice_map);


                    }

                    notice_list adapter1 = new notice_list();
                    notice_list.setAdapter(adapter1);
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    notice_list.setLayoutManager(layoutManager);

                }

                @Override
                public void onCancelled(DatabaseError error) {

                    Log.w("MainActivity", "Failed to read value.", error.toException());
                }
            });




        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem("https://siu.edu.bd/storage/uploaded/notice/SIU%20-%20Quota%20Movement%20Sylhet_1722702459.jpg", "Title 1"));
        sliderItems.add(new SliderItem("https://siu.edu.bd/storage/uploaded/event/CSE-AP-2024-June_1719918595.jpg", "Title 2"));
        sliderItems.add(new SliderItem("https://siu.edu.bd/storage/uploaded/notice/banner_1707320890.jpg", "Title 3"));
        sliderItems.add(new SliderItem("https://siu.edu.bd/storage/uploaded/notice/unnamed_1697551273.jpg", "Title 4"));
        sliderItems.add(new SliderItem("https://siu.edu.bd/storage/uploaded/notice/Notice_1696530836.jpg", "Title 5"));

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

    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = viewPager.getCurrentItem();
            int nextItem = (currentItem + 1) % viewPager.getAdapter().getItemCount();
            viewPager.setCurrentItem(nextItem, true);
        }
    };

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        sliderHandler.removeCallbacks(sliderRunnable);
//    }


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
                Toast.makeText(News_all.this,"Coming soon",Toast.LENGTH_LONG).show();
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
            String title = hashMap.get("title");
            String all_notice = hashMap.get("all_notice");
            String pic = hashMap.get("pic");
            String time = hashMap.get("time");


            holder.notice_title.setText(title);
            holder.all_notice.setText(all_notice);
            holder.time.setText(time);

            Picasso.get()
                    .load(""+pic)
                    .placeholder(R.drawable.itempic)
                    .error(R.drawable.itempic)
                    .into(holder.notice_pic);

            holder.notice_item.setOnClickListener(v -> {



                Map.notice=all_notice;
                Bitmap bitmap1 = ((BitmapDrawable) holder.notice_pic.getDrawable()).getBitmap();
                Map.notice_pic1=bitmap1;

                Intent nextActivity = new Intent(News_all.this, Map.class);
                Bundle bundle = new Bundle();
                bundle.putInt("VAL", 43);
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
            String title = hashMap.get("title");
            String all_notice = hashMap.get("all_notice");
            String pic = hashMap.get("pic");
            String time = hashMap.get("time");


            holder.notice_title.setText(title);
            holder.all_notice.setText(all_notice);
            holder.time.setText(time);

            Picasso.get()
                    .load(""+pic)
                    .placeholder(R.drawable.itempic)
                    .error(R.drawable.itempic)
                    .into(holder.notice_pic);

            holder.notice_item.setOnClickListener(v -> {



                Map.notice=all_notice;
                Bitmap bitmap1 = ((BitmapDrawable) holder.notice_pic.getDrawable()).getBitmap();
                Map.notice_pic1=bitmap1;

                Intent nextActivity = new Intent(News_all.this, Map.class);
                Bundle bundle = new Bundle();
                bundle.putInt("VAL", 43);
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




}