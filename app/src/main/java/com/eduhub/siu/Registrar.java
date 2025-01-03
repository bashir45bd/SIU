package com.eduhub.siu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


public class Registrar extends Fragment {

    RecyclerView recyclerView;
    HashMap<String,String> hashMap;
    ArrayList< HashMap<String,String> > arrayList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_registrar, container, false);


        recyclerView=view.findViewById(R.id.list);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("registrar");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String title = snapshot.child("title").getValue(String.class);
                    String name = snapshot.child("name").getValue(String.class);
                    String pic = snapshot.child("pic").getValue(String.class);
                    String mail = snapshot.child("email").getValue(String.class);
                    String number = snapshot.child("number").getValue(String.class);
                    String des = snapshot.child("des").getValue(String.class);
                    String fblink = snapshot.child("fblink").getValue(String.class);



                    hashMap= new HashMap<>();
                    hashMap.put("atitle",title);
                    hashMap.put("aname",name);
                    hashMap.put("apic",pic);
                    hashMap.put("amail",mail);
                    hashMap.put("anumber",number);
                    hashMap.put("ades",des);
                    hashMap.put("fblink",fblink);
                    arrayList.add(hashMap);


                }

                list adapter = new list();
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w("MainActivity", "Failed to read value.", error.toException());
            }
        });




        return view;
    }

    private class list extends RecyclerView.Adapter<Registrar.list.viewholder>{


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
        public Registrar.list.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.item, parent, false);

            return new Registrar.list.viewholder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull Registrar.list.viewholder holder, int position) {

            HashMap<String,String> hashMap=arrayList.get(position);
            String ftitle = hashMap.get("atitle");
            String fname = hashMap.get("aname");
            String fmail = hashMap.get("amail");
            String fnumber = hashMap.get("anumber");
            String fdes = hashMap.get("ades");
            String fpic = hashMap.get("apic");
            String flink = hashMap.get("fblink");




            holder.profession.setText(ftitle);
            holder.profession.setTextColor(Color.parseColor("#fae208"));
            holder.name.setText(fname);
            holder.name.setTextColor(Color.parseColor("#ffffff"));
            holder.mail.setText(fmail);
            holder.mail.setTextColor(Color.parseColor("#ffffff"));
            holder.number.setText(fnumber);
            holder.number.setTextColor(Color.parseColor("#ffffff"));

            holder.clicker.setBackgroundColor(Color.parseColor("#009fb5"));


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
}