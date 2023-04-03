package com.nova.learn_tap_code;

import android.os.Bundle;

import com.nova.learn_tap_code.framework.Question;
import com.nova.learn_tap_code.framework.QuestionFrame;

public class TapCodeTapWordFrame extends QuestionFrame {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final WordCourseInfo courseInfo = new WordCourseInfo(getApplicationContext());

        parent = StartScreen.class;
        startAfter = courseInfo.getNextActivity();

        TapCodeTapView questionView = new TapCodeTapView(getApplicationContext());

        NewInfoView infoView = new NewInfoView(getApplicationContext());
        FeedbackView feedbackView = new FeedbackView(getApplicationContext());

        Question question = courseInfo.getQuestion();

        feedbackView.init(question.morse);

        infoView.init(courseInfo.levelLetter(), TapCodeInfo.letterToTapCode(courseInfo.levelLetter()));

        init(question,courseInfo,infoView,questionView,feedbackView);

    }
}
