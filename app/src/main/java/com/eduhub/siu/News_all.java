package com.eduhub.siu;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;


public class News_all extends AppCompatActivity {

    RelativeLayout event,notice,news,result;
    ImageView eventback,noticeback,newsback,resultback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_all);

        event=findViewById(R.id.event1);
        notice=findViewById(R.id.notice1);
        news=findViewById(R.id.news1);
        result=findViewById(R.id.result);

        eventback=findViewById(R.id.eventback);
        noticeback=findViewById(R.id.noticeback);
        newsback=findViewById(R.id.newsback);
        resultback=findViewById(R.id.resultback);


        Bundle bun=getIntent().getExtras();
        int val =bun.getInt("VAL");

        if(val==11) {
            event.setVisibility(View.VISIBLE);

        }
        else if(val==12) {
            notice.setVisibility(View.VISIBLE);

        }
        else if(val==13) {
            news.setVisibility(View.VISIBLE);

        }
        else if(val==14) {
            result.setVisibility(View.VISIBLE);

        }






        eventback.setOnClickListener(v -> {
            onBackPressed();
        });

        noticeback.setOnClickListener(v -> {
            onBackPressed();
        });

        newsback.setOnClickListener(v -> {
            onBackPressed();
        });

        resultback.setOnClickListener(v -> {
            onBackPressed();
        });

    }
}