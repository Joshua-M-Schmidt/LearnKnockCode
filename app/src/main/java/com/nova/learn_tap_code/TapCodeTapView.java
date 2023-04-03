package com.nova.learn_tap_code;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nova.learn_tap_code.framework.Question;
import com.nova.learn_tap_code.framework.QuestionForm;

public class TapCodeTapView extends QuestionForm {
    private TextView answer;
    private TextView question;
    private Question questionInfo;
    private Context ctx;
    private ImageView background;
    private ProgressBar progressBar;
    private TextView pauseText;
    private ImageButton back;

    public TapCodeTapView(Context context) {
        super(context);
        this.ctx = context;
        init();
    }

    public TapCodeTapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        init();
    }

    public TapCodeTapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.ctx = context;
        init();
    }

    public TapCodeTapView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.ctx = context;
        init();
    }

    @Override
    public boolean isRight(){
        String unprocessed = getAnswer();
        Log.i("is right before", unprocessed);
        while(unprocessed.endsWith(" ") || unprocessed.endsWith("/")){
            unprocessed = unprocessed.substring(0,unprocessed.length()-1);
        }
        Log.i("is right after", unprocessed);
        return unprocessed.equals(questionInfo.morse);
    }

    @Override
    public void setQuestion(Question question){
        this.questionInfo = question;
        this.question.setText("Morse \""+question.normal+"\":");
    }

    @Override
    public String getAnswer(){
        return answer.getText().toString();
    }

    Vibrator vibrator;
    MediaPlayer mp;

    private void init(){
        inflate(getContext(), R.layout.question_morse_task,this);

        question = findViewById(R.id.question);

        mp = MediaPlayer.create(getContext(), R.raw.doorknock);

        progressBar = findViewById(R.id.pr_progress_in_circle);
        progressBar.setMax(100);
        progressBar.setProgress(100);

        vibrator = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);

        back = findViewById(R.id.edit_delete);
        back.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                if(answer.getText().length() > 0){
                    answer.setText(answer.getText().toString().substring(0,answer.getText().toString().length()-1));
                }
                pauseTimer.cancel();
            }
        });

        pauseText = findViewById(R.id.pause_test);

        initAnswerEdit();
        initBackground();
    }

    @Override
    public boolean performClick() {
        // Calls the super implementation, which generates an AccessibilityEvent
        // and calls the onClick() listener on the view, if any
        super.performClick();

        // Handle the action for the custom click here

        return true;
    }

    ToneGenerator toneGen1;
    CountDownTimer pauseTimer;
    float pause = 600;

    private void initBackground(){
        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        background = findViewById(R.id.background);
        pauseTimer = new CountDownTimer((long) pause,5) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) (((pause-millisUntilFinished)/pause)*100f);
                Log.i("progress",progress+" ");
                progressBar.setProgress(100-progress);
            }

            @Override
            public void onFinish() {
                answer.setText(answer.getText().toString()+" ");
                progressBar.setProgress(100);
            }
        };

        background.setOnTouchListener(new OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("touch events",event.getAction()+ " Action");
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        vibrator.vibrate(10);
                        progressBar.setProgress(100);
                        mp.start();
                        pauseTimer.cancel();
                        break;
                    case MotionEvent.ACTION_UP:

                        answer.setText(answer.getText().toString()+"·");
                        toneGen1.stopTone();
                        vibrator.cancel();
                        pauseTimer.start();
                        break;
                    default:
                }
                return true;
            }
        });
    }

    private void initAnswerEdit(){
        answer = findViewById(R.id.answer);
        answer.setClickable(false);
    }


}
