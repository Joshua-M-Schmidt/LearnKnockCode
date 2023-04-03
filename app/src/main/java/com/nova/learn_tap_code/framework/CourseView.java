package com.nova.learn_tap_code.framework;

import static com.nova.learn_tap_code.framework.Courselist.isStartable;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.nova.learn_tap_code.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CourseView extends LinearLayout {

    public CourseInfo course;
    private TextView title;
    private ImageView back;
    private TextView progressText;
    private ProgressBar progress;
    private ImageView start;
    private TextView unlockTime;
    private SharedPreferences prefs;
    private CardView background;

    public CourseView(@NonNull Context context, CourseInfo course) {
        super(context);

        this.course = course;

        init();
    }


    private void init(){
        inflate(getContext(), R.layout.course_layout,this);

        this.prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        this.setPadding(0,0,0,50);

        this.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        this.title = findViewById(R.id.course_title);
        this.title.setText(course.title);

        background = findViewById(R.id.course_view_back);
        background.setCardBackgroundColor(course.color);

        this.unlockTime = findViewById(R.id.unlock_time);

        this.back = findViewById(R.id.course_image);
        this.progressText = findViewById(R.id.progress);
        progressText.setText("Level "+course.getLevel()+"/"+course.getLevelCount());
        this.progress = findViewById(R.id.progress_bar);
        progress.setProgress(course.getLevelProgress());
        this.back.setImageResource(course.imageResource);

        start = findViewById(R.id.start_button);

        if(course.locked){
            start.setImageResource(R.drawable.lock);
        }
    }

    public void premium(){
        unlockTime.setVisibility(GONE);
        start.setImageResource(R.drawable.play);
    }

    public void lock(){
        unlockTime.setVisibility(GONE);
        start.setImageResource(R.drawable.lock);
    }

    public void unlock(){
        unlockTime.setVisibility(VISIBLE);
        start.setImageResource(R.drawable.unlock);
        String time = prefs.getString(course.title,"");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        Log.i("unlock",time);
        try {
            Date t = sdf.parse(time);
            Log.i("unlock",sdf.parse(time).toString());


            unlockTime.setText(sdf2.format(t));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isStartable(course.title,getContext())){
                    handler.postDelayed(this, 1000);
                }else{
                    lock();
                }

            }
        }, 1000);
    }
}
