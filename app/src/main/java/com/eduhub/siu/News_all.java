package com.eduhub.siu;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
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

    RelativeLayout event,notice,epayment,result,vc,result_sheet;
    ImageView eventback,noticeback,newsback,resultback,vcback;
    LinearLayout inputpage;
    TextView fgpa;
   // TabLayout tabLayout;
   // ViewPager2 viewPager;
  //  private Handler sliderHandler;
    RecyclerView vc_recyclerView,notice_list,news_list,grade_list;
    ExtendedFloatingActionButton notice_fav,event_fav,reslut_fav;

    TextInputEditText title,n_body,rollR,subR,cRoll,depR,semR;
    ImageView n_image,select_image;
    String imageBase64;
    Button n_btn,submin_btn,c_btn,delete_btn;
    SearchView searchView_notice,news_search;
    AutoCompleteTextView cDep,cSem;

    HashMap<String,String> hashMap;
    ArrayList< HashMap<String,String> > arrayList = new ArrayList<>();
    ArrayList< HashMap<String,String> > result_list = new ArrayList<>();
    ArrayList< HashMap<String,String> > arrayList_event = new ArrayList<>();
    news_list adapter6 = new news_list(arrayList_event);
    ArrayList< HashMap<String,String> > arrayList_notice = new ArrayList<>();
    notice_list adapter = new notice_list(arrayList_notice);
    String g_roll="";
    String g_dep="";
    String g_sem="";


    String[] options = {"CSE", "ECE", "LAW", "ENG","BBA"};
    String[] semester = {"1st", "2nd", "3rd", "4th","5th","6th", "7th","8th"};

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_all);

        event=findViewById(R.id.event1);
        fgpa=findViewById(R.id.fGPA);
        searchView_notice=findViewById(R.id.searchView_notice);
        notice=findViewById(R.id.notice1);
        epayment=findViewById(R.id.epayment);
        result=findViewById(R.id.result);
        c_btn=findViewById(R.id.c_btn);
        cRoll=findViewById(R.id.cRoll);
        cSem=findViewById(R.id.cSemester);
        cDep=findViewById(R.id.cDepartment);
        result_sheet = findViewById(R.id.result_sheet);
        inputpage = findViewById(R.id.inputpage);
        eventback=findViewById(R.id.eventback);
        noticeback=findViewById(R.id.noticeback);
        reslut_fav=findViewById(R.id.result_fav);
        news_search=findViewById(R.id.searchView_event);
        newsback=findViewById(R.id.newsback);
        resultback=findViewById(R.id.resultback);
        vcback=findViewById(R.id.vcback);
        vc=findViewById(R.id.vc);
        vc_recyclerView=findViewById(R.id.vc_list);
        notice_list=findViewById(R.id.notice_list);
        grade_list=findViewById(R.id.grade_list);
        news_list=findViewById(R.id.news_list);
        notice_fav=findViewById(R.id.notice_fav);
        event_fav=findViewById(R.id.event_fav);

        EditText searchEditText = searchView_notice.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.parseColor("#009fb5"));
        searchEditText.setHintTextColor(Color.GRAY);
        EditText searchEditText2 = news_search.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText2.setTextColor(Color.parseColor("#009fb5"));
        searchEditText2.setHintTextColor(Color.GRAY);

        sharedPreferences=getSharedPreferences("siu",MODE_PRIVATE);
        String types= sharedPreferences.getString("user_type","");

        Bundle bun=getIntent().getExtras();
        int val =bun.getInt("VAL");

        if(val==11) {
            event.setVisibility(View.VISIBLE);
            event_fav.setVisibility(View.GONE);

            ArrayRequestforevent();

            // SearchView logic
            news_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filterList2(newText);
                    return true;
                }
            });


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

            // SearchView logic
            searchView_notice.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filterList(newText);
                    return true;
                }
            });





            if (types.contains("teacher")){
                notice_fav.setVisibility(View.VISIBLE);
            }


            notice_fav.setOnClickListener(view -> {

                add_notice(News_all.this);

            });


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
            reslut_fav.setVisibility(View.GONE);

            ArrayAdapter<String> adapter3 = new ArrayAdapter<>(News_all.this, android.R.layout.simple_dropdown_item_1line, semester);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(News_all.this, android.R.layout.simple_dropdown_item_1line, options);
            cDep.setAdapter(adapter);
            cSem.setAdapter(adapter3);

            resultback.setOnClickListener(v -> {
                onBackPressed();
            });

            if (types.contains("teacher")){
                reslut_fav.setVisibility(View.VISIBLE);
            }

            c_btn.setOnClickListener(view -> {

             inputpage.setVisibility(View.GONE);
             result_sheet.setVisibility(View.VISIBLE);
             fetchResult(News_all.this);

            });






            reslut_fav.setOnClickListener(view -> {


                add_result(News_all.this);

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

    private class Grade_adpter extends RecyclerView.Adapter<Grade_adpter.viewholder>{


        private class viewholder extends RecyclerView.ViewHolder{


            TextView name,code,gpa,grade;
            LinearLayout grade_item;



            public viewholder(@NonNull View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.name);
                code=itemView.findViewById(R.id.code);
                grade=itemView.findViewById(R.id.grade);
                gpa=itemView.findViewById(R.id.gpa);
                grade_item=itemView.findViewById(R.id.grade_item);

            }
        }

        @NonNull
        @Override
        public Grade_adpter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.resultshow, parent, false);

            return new Grade_adpter.viewholder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull Grade_adpter.viewholder holder, int position) {

            HashMap<String,String> hashMap=result_list.get(position);
            String fcode = hashMap.get("subject_code");
            String fname = hashMap.get("subject_name");
            String fgpa = hashMap.get("grade_point");
            String fgrade = hashMap.get("letter_grade");

            holder.name.setText(fname);
            holder.code.setText(fcode);
            holder.grade.setText(fgrade);
            holder.gpa.setText(fgpa);

            sharedPreferences=getSharedPreferences("siu",MODE_PRIVATE);
            String types= sharedPreferences.getString("user_type","");
            if (types.contains("teacher")){

                holder.grade_item.setOnClickListener(view -> {

                    update_result_layout(News_all.this,fcode);

                });
            }



        }

        @Override
        public int getItemCount() {
            return result_list.size();
        }





    }

    private class notice_list extends RecyclerView.Adapter<notice_list.viewholder> {

        ArrayList<HashMap<String, String>> arrayList1;

        public notice_list(ArrayList<HashMap<String, String>> arrayList1) {
            this.arrayList1 = arrayList1;
        }

        public void setFilteredList(ArrayList<HashMap<String, String>> filteredList) {
            this.arrayList1 = filteredList;
            notifyDataSetChanged();
        }

        private class viewholder extends RecyclerView.ViewHolder {

            ImageView notice_pic;
            LinearLayout notice_item;
            TextView notice_title, all_notice, time;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                notice_item = itemView.findViewById(R.id.notice_item);
                notice_pic = itemView.findViewById(R.id.notice_pic);
                notice_title = itemView.findViewById(R.id.notice_title);
                all_notice = itemView.findViewById(R.id.all_notice);
                time = itemView.findViewById(R.id.notice_time);
            }
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.notice_card, parent, false);
            return new viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewholder holder, int position) {

            HashMap<String, String> hashMap = arrayList1.get(position);
            String id = hashMap.get("id");
            String title = hashMap.get("title");
            String all_notice = hashMap.get("all_notice");
            String pic = hashMap.get("pic");
            String time = hashMap.get("s_time");

            holder.notice_title.setText(title);
            holder.all_notice.setText(all_notice);

            try {
                double a_time = Double.parseDouble(time);
                SimpleDateFormat simpletimeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                SimpleDateFormat simpledateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

                String stime = simpletimeFormat.format(a_time);
                String date = simpledateFormat.format(a_time);
                holder.time.setText(stime + " - " + date);
            } catch (Exception e) {
                holder.time.setText("Invalid date");
            }

            Picasso.get()
                    .load(pic)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.notice_pic);

            holder.notice_item.setOnClickListener(v -> {
                Map.notice = all_notice;
                Bitmap bitmap1 = ((BitmapDrawable) holder.notice_pic.getDrawable()).getBitmap();
                Map.notice_pic1 = bitmap1;

                Intent nextActivity = new Intent(v.getContext(), Map.class);
                Bundle bundle = new Bundle();
                bundle.putInt("VAL", 43);
                bundle.putString("id", id);
                bundle.putString("title", title);
                bundle.putString("body", all_notice);
                bundle.putString("image", pic);
                nextActivity.putExtras(bundle);
                v.getContext().startActivity(nextActivity);
            });
        }

        @Override
        public int getItemCount() {
            return arrayList1.size();
        }
    }


    private class news_list extends RecyclerView.Adapter<news_list.viewholder1>{


        ArrayList<HashMap<String, String>> arrayList2;

        public news_list(ArrayList<HashMap<String, String>> arrayList1) {
            this.arrayList2 = arrayList1;
        }

        public void setFilteredList(ArrayList<HashMap<String, String>> filteredList) {
            this.arrayList2 = filteredList;
            notifyDataSetChanged();
        }


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


        String url = "http://192.168.1.106/SIU/notice.php";

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


        String url = "http://192.168.1.106/SIU/notice.php";

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

    private void add_result(Context context) {

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.add_result, null);
        dialog.setContentView(view);
        rollR=view.findViewById(R.id.editRoll);
        depR=view.findViewById(R.id.g_dep);
        semR=view.findViewById(R.id.g_sem);
        subR=view.findViewById(R.id.editSubject);
        submin_btn=view.findViewById(R.id.submit_btn);




        submin_btn.setOnClickListener(view1 -> {

         submitResult(News_all.this);

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
        String url = "http://192.168.1.106/SIU/notice_get.php"; // Update with actual URL

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
                                    arrayList_notice.add(noticeMap);
                                }

                                if (!arrayList_notice.isEmpty()) {

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
        String url = "http://192.168.1.106/SIU/notice_get.php"; // Update with actual URL

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

                                    arrayList_event.add(noticeMap2);
                                }

                                if (!arrayList_event.isEmpty()) {

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

    private void filterList(String newText) {

        ArrayList<HashMap<String, String>> filteredList = new ArrayList<>();
        for (HashMap<String, String> item : arrayList_notice) {
            if (item.get("title").toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        }
        adapter.setFilteredList(filteredList);
    }


    private void filterList2(String newText) {

        ArrayList<HashMap<String, String>> filteredList2 = new ArrayList<>();
        for (HashMap<String, String> item : arrayList_event) {
            if (item.get("title").toLowerCase().contains(newText.toLowerCase())) {
                filteredList2.add(item);
            }
        }

        if (filteredList2.isEmpty()) {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        }
        adapter6.setFilteredList(filteredList2);
    }


    private void submitResult(Context context) {
        String insertUrl = "http://192.168.1.106/SIU/addresult.php";

        String roll = rollR.getText().toString().trim();
        String dept = depR.getText().toString().trim();
        String semester = semR.getText().toString().trim();
        String subjectLines = subR.getText().toString().trim();

        if (roll.isEmpty() || dept.isEmpty() || semester.isEmpty() || subjectLines.isEmpty()) {
            new AlertDialog.Builder(context)
                    .setTitle("Missing Info")
                    .setMessage("Please fill in all fields.")
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
            return;
        }

        try {
            JSONArray resultArray = new JSONArray();
            String[] lines = subjectLines.split("\n");

            for (String line : lines) {
                String[] parts = line.trim().split(" ");
                if (parts.length == 2) {
                    JSONObject obj = new JSONObject();
                    obj.put("subject_code", parts[0]);
                    obj.put("grade_point", parts[1]);
                    resultArray.put(obj);
                }
            }

            RequestQueue queue = Volley.newRequestQueue(context);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, insertUrl,
                    response -> {
                        try {
                            JSONObject res = new JSONObject(response);
                            String status = res.getString("status");

                            if (status.equals("success")) {

                                new AlertDialog.Builder(context)
                                        .setTitle("Success")
                                        .setMessage("Results submitted.")
                                        .setPositiveButton("OK", null)
                                        .show();
                            } else {
                                String message = res.getString("message");
                                new AlertDialog.Builder(context)
                                        .setTitle("Failed")
                                        .setMessage(message)
                                        .setNegativeButton("Close", null)
                                        .show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(context, "Invalid response from server", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()) {

                @Nullable
                @Override
                protected java.util.Map<String, String> getParams() throws AuthFailureError {
                    java.util.Map<String, String> map = new HashMap<>();
                    map.put("roll", roll);
                    map.put("department", dept);
                    map.put("semester_id", semester);
                    map.put("results", resultArray.toString());
                    map.put("key", Encyption.Mykey);
                    return map;
                }
            };

            queue.add(stringRequest);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error processing input", Toast.LENGTH_SHORT).show();
        }
    }


    private void fetchResult(Context context) {

        String url = "http://192.168.1.106/SIU/getResult.php";

        // Create a ProgressDialog
        ProgressDialog progressDialog = new ProgressDialog(News_all.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String roll = cRoll.getText().toString().trim();
        String dept = cDep.getText().toString().trim();
        String semester = cSem.getText().toString().trim();

        if (roll.isEmpty() || dept.isEmpty() || semester.isEmpty()) {
            new AlertDialog.Builder(context)
                    .setTitle("Missing Info")
                    .setMessage("Please fill in all fields.")
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
                    progressDialog.dismiss();

        }else {
            RequestQueue queue = Volley.newRequestQueue(this);


            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        progressDialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("status").equals("success")) {
                                JSONArray results = json.getJSONArray("results");


                                for (int i = 0; i < results.length(); i++) {

                                    JSONObject obj = results.getJSONObject(i);
                                    HashMap<String, String> Rmap = new HashMap<>();
                                    Rmap.put("subject_code", obj.getString("subject_code"));
                                    Rmap.put("subject_name", obj.getString("subject_name"));
                                    Rmap.put("grade_point", obj.getString("grade_point"));
                                    Rmap.put("letter_grade", obj.getString("letter_grade"));
                                    result_list.add(Rmap);

                                }
                                String cgpa = json.getString("cgpa");
                                fgpa.setText("Your GPA is: " + cgpa);

                                Grade_adpter adapter9 = new Grade_adpter();
                                grade_list.setAdapter(adapter9);
                                grade_list.setLayoutManager(new LinearLayoutManager(News_all.this));

                                g_roll=roll;
                                g_dep=dept;
                                g_sem=semester;


                            } else {
                                Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                        }
                    },

                    error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()) {

                @Nullable
                @Override
                protected java.util.Map<String, String> getParams() throws AuthFailureError {
                    java.util.Map<String, String> map = new HashMap<>();
                    map.put("roll", roll);
                    map.put("department", dept);
                    map.put("semester_id", semester);
                    map.put("key", Encyption.Mykey);
                    return map;
                }
            };

            queue.add(stringRequest);

        }


    }


    private void update_result_layout(Context context, String code) {

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.add_result, null);
        dialog.setContentView(view);
        rollR=view.findViewById(R.id.editRoll);
        depR=view.findViewById(R.id.g_dep);
        semR=view.findViewById(R.id.g_sem);
        subR=view.findViewById(R.id.editSubject);
        submin_btn=view.findViewById(R.id.submit_btn);
        delete_btn=view.findViewById(R.id.delete_btn);
        delete_btn.setVisibility(View.VISIBLE);
        submin_btn.setText("Update");
        rollR.setText(g_roll);
        depR.setText(g_dep);
        subR.setText(code);
        semR.setText(g_sem);



        submin_btn.setOnClickListener(view1 -> {

            updateResults(News_all.this);


        });

        delete_btn.setOnClickListener(view1 -> {

            deleteResults(News_all.this);

        });


        dialog.show();

    }

    private void updateResults(Context context) {
        String updateUrl = "http://192.168.1.106/SIU/updateresult.php";

        String roll = rollR.getText().toString().trim();
        String dept = depR.getText().toString().trim(); // department_id
        String semester = semR.getText().toString().trim();
        String subjectLines = subR.getText().toString().trim();

        if (roll.isEmpty() || dept.isEmpty() || semester.isEmpty() || subjectLines.isEmpty()) {
            new AlertDialog.Builder(context)
                    .setTitle("Missing Info")
                    .setMessage("Please fill in all fields.")
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
            return;
        }

        try {
            JSONArray subjectArray = new JSONArray();
            String[] lines = subjectLines.split("\n");

            for (String line : lines) {
                String[] parts = line.trim().split(" ");
                if (parts.length == 2) {
                    JSONObject obj = new JSONObject();
                    obj.put("subject_code", parts[0]);
                    obj.put("grade_point", parts[1]); // Raw marks
                    subjectArray.put(obj);
                }
            }

            RequestQueue queue = Volley.newRequestQueue(context);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, updateUrl,
                    response -> {
                        try {
                            JSONObject res = new JSONObject(response);
                            String status = res.getString("status");

                            if (status.equals("success")) {
                                new AlertDialog.Builder(context)
                                        .setTitle("Success")
                                        .setMessage("Results updated.")
                                        .setPositiveButton("OK", null)
                                        .show();
                            } else {
                                String message = res.getString("message");
                                new AlertDialog.Builder(context)
                                        .setTitle("Failed")
                                        .setMessage(message)
                                        .setNegativeButton("Close", null)
                                        .show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(context, "Invalid response from server", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()) {

                @Nullable
                @Override
                protected java.util.Map<String, String> getParams() throws AuthFailureError {
                    java.util.Map<String, String> map = new HashMap<>();
                    map.put("roll", roll);
                    map.put("department_id", dept); // ✅ fixed key
                    map.put("semester_id", semester);
                    map.put("subjects", subjectArray.toString()); // ✅ match PHP
                    map.put("action", "update");
                    map.put("key", Encyption.Mykey);
                    return map;
                }
            };

            queue.add(stringRequest);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error processing input", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteResults(Context context) {

        String deleteUrl = "http://192.168.1.106/SIU/updateresult.php";

        String roll = rollR.getText().toString().trim();
        String dept = depR.getText().toString().trim();
        String semester = semR.getText().toString().trim();
        String subjectLines = subR.getText().toString().trim();

        if (roll.isEmpty() || dept.isEmpty() || semester.isEmpty() || subjectLines.isEmpty()) {
            new AlertDialog.Builder(context)
                    .setTitle("Missing Info")
                    .setMessage("Please fill in all fields.")
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
            return;
        }

        try {
            JSONArray subjectArray = new JSONArray();
            String[] lines = subjectLines.split("\n");

            for (String line : lines) {
                String subjectCode = line.trim();
                if (!subjectCode.isEmpty()) {
                    JSONObject obj = new JSONObject();
                    obj.put("subject_code", subjectCode);
                    subjectArray.put(obj);
                }
            }

            RequestQueue queue = Volley.newRequestQueue(context);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, deleteUrl,
                    response -> {
                        try {
                            JSONObject res = new JSONObject(response);
                            String status = res.getString("status");

                            if (status.equals("success")) {
                                new AlertDialog.Builder(context)
                                        .setTitle("Success")
                                        .setMessage("Results deleted successfully.")
                                        .setPositiveButton("OK", null)
                                        .show();
                            } else {
                                String message = res.getString("message");
                                new AlertDialog.Builder(context)
                                        .setTitle("Failed")
                                        .setMessage(message)
                                        .setNegativeButton("Close", null)
                                        .show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(context, "Invalid server response", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()) {

                @Nullable
                @Override
                protected java.util.Map<String, String> getParams() {
                    java.util.Map<String, String> map = new HashMap<>();
                    map.put("roll", roll);
                    map.put("department_id", dept);
                    map.put("semester_id", semester);
                    map.put("subjects", subjectArray.toString());
                    map.put("action", "delete");
                    map.put("key", Encyption.Mykey);
                    return map;
                }
            };

            queue.add(stringRequest);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error preparing request", Toast.LENGTH_SHORT).show();
        }
    }







}