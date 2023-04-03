package com.nova.learn_tap_code;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.nova.learn_tap_code.framework.Courselist;


public class StartScreen extends Courselist {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        background.setImageResource(R.drawable.knock_code_back);

        addCourse(new AlphabetCourseInfo(getApplicationContext()),StartScreen.this);
        addCourse(new WordCourseInfo(getApplicationContext()),StartScreen.this);
    }
}
