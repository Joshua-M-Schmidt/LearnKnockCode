package com.nova.learn_tap_code;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.nova.learn_tap_code.framework.QuestionFeedbackView;


public class FeedbackView extends QuestionFeedbackView {
    public FeedbackView(Context context) {
        super(context);
    }

    public FeedbackView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FeedbackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FeedbackView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    TextView title;

    public void init(String morse){
        inflate(getContext(), R.layout.feedback_layout,this);
        title = findViewById(R.id.feedback);

        TextView m = findViewById(R.id.feedback_detail);
        m.setText(morse);
    }

    @Override
    public void setPositiveNegative(boolean positive){
        if(positive){
            title.setText("Right");
            title.setTextColor(getResources().getColor(R.color.green));
        }else{
            title.setText("False");
            title.setTextColor(getResources().getColor(R.color.red));
        }
    }


}
