package com.nova.learn_tap_code;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.nova.learn_tap_code.framework.Question;
import com.nova.learn_tap_code.framework.QuestionFrame;

public class TapCodeListenWordFrame extends QuestionFrame {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final WordCourseInfo courseInfo = new WordCourseInfo(getApplicationContext());

        parent = StartScreen.class;
        startAfter = courseInfo.getNextActivity();

        TapCodeListenView questionView = new TapCodeListenView(getApplicationContext());

        NewInfoView infoView = new NewInfoView(getApplicationContext());
        FeedbackView feedbackView = new FeedbackView(getApplicationContext());

        Question question = courseInfo.getQuestion();

        feedbackView.init(question.morse);

        infoView.init(courseInfo.levelLetter(), TapCodeInfo.letterToTapCode(courseInfo.levelLetter()));

        init(question,courseInfo,infoView,questionView,feedbackView);

        if(!courseInfo.showNewInfo()){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }
}

