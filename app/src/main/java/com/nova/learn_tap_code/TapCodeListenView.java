package com.nova.learn_tap_code;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.Animatable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nova.learn_tap_code.framework.Question;
import com.nova.learn_tap_code.framework.QuestionForm;


public class TapCodeListenView extends QuestionForm {

    private ImageView background;
    private EditText answer;
    private Question questionInfo;
    private SharedPreferences prefs;
    private Context ctx;
    private RelativeLayout container;

    Runnable knocking = null;
    Handler handler = null;

    SoundPool knockingSound;
    int knockingSoundId;

    public TapCodeListenView(Context context) {
        super(context);
        this.ctx = context;
        init();
    }

    public TapCodeListenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        init();
    }

    public TapCodeListenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.ctx = context;
        init();
    }

    public TapCodeListenView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.ctx = context;
        init();
    }

    @Override
    public boolean isRight() {
        return getAnswer().equals(questionInfo.normal);
    }

    @Override
    public void onDetachedFromWindow(){
        super.onDetachedFromWindow();
        if(knocking != null){
            handler.removeCallbacks(knocking);
        }
    }


    @Override
    public void setQuestion(Question question) {
        this.questionInfo = question;
        knocking = getKnockingRunnable(question.morse);
        blink(question.morse);
    }

    @Override
    public String getAnswer() {
        try {
            prefs.edit().putBoolean(RUN_KEY,false).commit();
            Log.i("blink_test", "put_false");
        } finally {
            return answer.getText().toString();
        }
    }



    private void addKnock(){
        Animation translateAnimation = new TranslateAnimation(width/2-200, width-400,300, 0);
        translateAnimation.setDuration(1000);
        translateAnimation.setFillAfter(true);

        /*Animation anim = new ScaleAnimation(
                2f, 4f, // Start and end values for the X axis scaling
                2f, 4f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(1000);*/

        final ImageView image = new ImageView(getContext());
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(400,800);
        layout.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.TRUE);
        layout.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
        layout.topMargin = 50;
        image.setLayoutParams(layout);
        image.setImageResource(R.drawable.knock);
        container.addView(image);
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(1000);

        AnimationSet s = new AnimationSet(false);//false means don't share interpolators
        s.addAnimation(fadeOut);
        s.addAnimation(translateAnimation);
        //s.addAnimation(anim);
        image.startAnimation(s);

        fadeOut.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation arg0) {
                image.setVisibility(GONE);
            }
        });

    }

    int width;
    int height;

    private void init() {
        inflate(getContext(), R.layout.question_sound, this);

        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        container = findViewById(R.id.container);

        createSoundPool();

        background = findViewById(R.id.background);
        background.setTag(1);

        answer = findViewById(R.id.answer);

        answer.requestFocus();

        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(answer, InputMethodManager.SHOW_IMPLICIT);

        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        prefs.edit().putBoolean(RUN_KEY, true).commit();

        handler = new Handler();
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

    public static final String RUN_KEY = "running";
    int counter;
    boolean destroy;
    String tag = "blink_test";

    private void blink(final String sequence){

        Log.i(tag,sequence);

        prefs.edit().putBoolean(RUN_KEY, true).commit();

        Log.i("blink_test","run "+prefs.getBoolean(RUN_KEY,false));
        destroy = false;
        counter = 0;

        handler.postDelayed(knocking, 1000);
    }

    private Runnable getKnockingRunnable(final String sequence){
        return new Runnable() {
            @Override
            public void run() {
                Log.i("blink_test", "run " + prefs.getBoolean(RUN_KEY, false));

                if ((int) background.getTag() == 1) {
                    //ship.setImageResource(2);
                    background.setTag(2);
                    if (String.valueOf(sequence.charAt(counter)).equals("·")) {
                        if (prefs.getBoolean(RUN_KEY, false)) {

                            knockingSound.play(knockingSoundId, 1, 1, 0, 0, 1);

                            addKnock();
                        }
                    } else if (String.valueOf(sequence.charAt(counter)).equals(" ")) {

                    }
                } else {
                    //ship.setImageResource(1);
                    background.setTag(1);
                }

                Log.i(tag, "" + counter);

                if ((int) background.getTag() == 1) {
                    if (counter == sequence.length()) {
                        counter = 0;
                        if (prefs.getBoolean(RUN_KEY, false)) {
                            Log.i("TapCodeListenView", "long pause between repetitions");
                            handler.postDelayed(this, 2000);
                        }
                    } else {
                        if (prefs.getBoolean(RUN_KEY, false))
                            handler.postDelayed(this, 100);
                    }

                } else {

                    Log.i(tag, String.valueOf(sequence.charAt(counter)));

                    if (String.valueOf(sequence.charAt(counter)).equals("·")) {
                        if (prefs.getBoolean(RUN_KEY, false))
                            handler.postDelayed(this, 150);
                    } else if (String.valueOf(sequence.charAt(counter)).equals(" ")) {
                        if (prefs.getBoolean(RUN_KEY, false))
                            handler.postDelayed(this, 600);
                    } else if (String.valueOf(sequence.charAt(counter)).equals("/")) {
                        if (prefs.getBoolean(RUN_KEY, false))
                            handler.postDelayed(this, 1200);
                    }

                    counter++;

                }
            }
        };
    }
}