package com.eduhub.siu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Author2 extends Fragment {

    RecyclerView recyclerView;
    HashMap<String,String> hashMap;
    ArrayList< HashMap<String,String> > arrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_author2, container, false);

        recyclerView=view.findViewById(R.id.sya_list);
        syndicate();




        return view;
    }

    private class sya_list extends RecyclerView.Adapter<Author2.sya_list.viewholder>{


        private class viewholder extends RecyclerView.ViewHolder{

            ImageView scall,smail,sfb,itempic;
            TextView name,profession,number,mail;
            LinearLayout clicker;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                scall=itemView.findViewById(R.id.scall);
                smail=itemView.findViewById(R.id.smail);
                sfb=itemView.findViewById(R.id.sfb);
                name=itemView.findViewById(R.id.nameshow);
                profession=itemView.findViewById(R.id.profession);
                number=itemView.findViewById(R.id.numbershow);
                mail=itemView.findViewById(R.id.mailshow);
                itempic=itemView.findViewById(R.id.itempic);
                scall=itemView.findViewById(R.id.scall);
                clicker=itemView.findViewById(R.id.clicker);
            }
        }

        @NonNull
        @Override
        public Author2.sya_list.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.item, parent, false);

            return new Author2.sya_list.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewholder holder, int position) {
            HashMap<String,String>hashMap=arrayList.get(position);
            String ftitle = hashMap.get("atitle");
            String fname = hashMap.get("aname");
            String fmail = hashMap.get("amail");
            String fnumber = hashMap.get("anumber");
            String fdes = hashMap.get("ades");
            String fpic = hashMap.get("apic");
            String flink = hashMap.get("fblink");



            holder.profession.setText(ftitle);
            holder.name.setText(fname);
            holder.mail.setText(fmail);
            holder.number.setText(fnumber);


            Picasso.get()
                    .load(""+fpic)
                    .placeholder(R.drawable.itempic)
                    .error(R.drawable.itempic)
                    .into(holder.itempic);



            holder.clicker.setOnClickListener(v -> {


                Map.title=ftitle;
                Map.descrpition=fdes;
                Bitmap bitmap = ((BitmapDrawable) holder.itempic.getDrawable()).getBitmap();
                Map.bitmap=bitmap;

                Intent nextActivity = new Intent(getContext(), Map.class);
                Bundle bundle = new Bundle();
                bundle.putInt("VAL", 42);
                nextActivity.putExtras(bundle);
                startActivity(nextActivity);

            });

            holder.scall.setOnClickListener(v -> {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+fnumber));
                startActivity(intent);

            });

            holder.smail.setOnClickListener(v -> {

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto: "+fmail));
                startActivity(intent);

            });

            holder.sfb.setOnClickListener(v -> {

                String uri = flink;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);

            });





        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }





    }

    private void syndicate() {

        String URL = "http://192.168.1.104/SIU/Alldata.json";
        RequestQueue queue = Volley.newRequestQueue(getContext());


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject trustSiu = response.getJSONObject("syndicate");

                            Iterator<String> keys = trustSiu.keys();

                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject person = trustSiu.getJSONObject(key);

                                String name = person.getString("name");
                                String title = person.getString("title");
                                String email = person.getString("email");
                                String pic = person.getString("pic");
                                String number = person.getString("number");
                                String des = person.getString("des");
                                String fblink = person.getString("fblink");

                                hashMap= new HashMap<>();
                                hashMap.put("atitle",title);
                                hashMap.put("aname",name);
                                hashMap.put("apic",pic);
                                hashMap.put("amail",email);
                                hashMap.put("anumber",number);
                                hashMap.put("ades",des);
                                hashMap.put("fblink",fblink);
                                arrayList.add(hashMap);


                            }

                            sya_list adapter = new sya_list();
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
            }
        });

        queue.add(request);
    }
}