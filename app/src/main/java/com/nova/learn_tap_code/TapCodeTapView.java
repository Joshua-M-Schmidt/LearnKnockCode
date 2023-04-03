package com.nova.learn_tap_code;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.ToneGenerator;
import android.os.Build;
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

    SoundPool knockingSound;
    int knockingSoundId;

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

    private void init(){
        inflate(getContext(), R.layout.question_morse_task,this);

        question = findViewById(R.id.question);

        createSoundPool();

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

    protected void createSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        } else {
            createOldSoundPool();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createNewSoundPool(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        knockingSound = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
        knockingSoundId = knockingSound.load(getContext(),R.raw.doorknock,1);
    }

    @SuppressWarnings("deprecation")
    protected void createOldSoundPool(){
        knockingSound = new SoundPool(5,AudioManager.STREAM_MUSIC,0);
        knockingSoundId = knockingSound.load(getContext(),R.raw.doorknock,1);
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
    CountDownTimer longPauseTimer;
    float pause = 600;
    float longPause = 2000;

    private void initBackground(){
        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        background = findViewById(R.id.background);
        pauseTimer = new CountDownTimer((long) pause,5) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) (((pause-millisUntilFinished)/pause)*100f);
                Log.i("progress",progress+" ");
                progressBar.setProgress(100-progress);
                pauseText.setText("short pause");
            }

            @Override
            public void onFinish() {
                answer.setText(answer.getText().toString()+" ");
                longPauseTimer.start();
                progressBar.setProgress(100);
                pauseText.setText("");
            }
        };

        longPauseTimer = new CountDownTimer((long) longPause,5) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) (((longPause-millisUntilFinished)/longPause)*100f);
                Log.i("progress",progress+" ");
                progressBar.setProgress(100-progress);
                pauseText.setText("long pause");
            }

            @Override
            public void onFinish() {
                answer.setText(answer.getText().toString().trim()+"/");
                progressBar.setProgress(100);
                pauseText.setText("");
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
                        knockingSound.play(knockingSoundId, 1, 1, 0, 0, 1);

                        pauseTimer.cancel();
                        longPauseTimer.cancel();
                        break;
                    case MotionEvent.ACTION_UP:
                        answer.setText(answer.getText().toString()+"·");
                        toneGen1.stopTone();
                        vibrator.cancel();
                        pauseTimer.start();
                        break;
                    default:
                        break;
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
