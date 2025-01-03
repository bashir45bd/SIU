package com.eduhub.siu;

import android.content.Context;
import android.util.Log;
import android.widget.Adapter;

import androidx.constraintlayout.helper.widget.Carousel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FirebaseGet_data {

      public  void  data (String title, HashMap<String,String> hashMap, ArrayList< HashMap<String,String> > arrayList, Adapter adapter, RecyclerView recyclerView,Context context){

          DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(title);
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


                      hashMap.put("atitle",title);
                      hashMap.put("aname",name);
                      hashMap.put("apic",pic);
                      hashMap.put("amail",mail);
                      hashMap.put("anumber",number);
                      hashMap.put("ades",des);
                      hashMap.put("fblink",fblink);
                      arrayList.add(hashMap);


                  }

                 // recyclerView.setAdapter(adapter);
                  recyclerView.setLayoutManager(new LinearLayoutManager(context));


              }

              @Override
              public void onCancelled(DatabaseError error) {

                  Log.w("MainActivity", "Failed to read value.", error.toException());
              }
          });


      }



}
