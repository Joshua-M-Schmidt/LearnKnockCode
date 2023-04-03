package com.nova.learn_tap_code;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

public class NewInfoView extends FrameLayout {

    public NewInfoView(Context context) {
        super(context);
    }

    public NewInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NewInfoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(String letter, String morse){
        inflate(getContext(), R.layout.new_info_layout,this);
        TextView title = findViewById(R.id.new_text);
        title.setText("New Letter \""+letter+"\"");
        TextView m = findViewById(R.id.new_morse);
        m.setText(morse);
    }

}
