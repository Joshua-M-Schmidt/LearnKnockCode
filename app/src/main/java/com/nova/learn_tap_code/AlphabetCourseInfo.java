package com.nova.learn_tap_code;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


import java.util.ArrayList;
import java.util.Random;

import static com.nova.learn_tap_code.TapCodeInfo.letterToTapCode;
import static com.nova.learn_tap_code.framework.Courselist.LAST_CLASS;

import com.nova.learn_tap_code.framework.CourseInfo;
import com.nova.learn_tap_code.framework.Question;

public class AlphabetCourseInfo extends CourseInfo {

    public SharedPreferences prefs;

    public AlphabetCourseInfo(Context ctx){
        super(R.drawable.abc_course,0xff704f3f,"Learn Tap Code\nAlphabet","alphabet_course_key",ctx,false);
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    @Override
    public Class getNextActivity(){
        ArrayList<Class> question_list = new ArrayList<>();
        question_list.add(TapCodeListenFrame.class);
        question_list.add(TapCodeTapFrame.class);

        Log.i("getNextActivity","last "+prefs.getString(LAST_CLASS,""));


        Class cl = question_list.get(new Random().nextInt(question_list.size()));
        prefs.edit().putString(cl.getName(),"").commit();

        return cl;
    }

    @Override
    public int[] getLevels(){
        return new int[]{
                0,3,6,15,35,55,75,95,115,135,155,175,195,215,235,255,275,295,315,335,355,375,395,415,435,455,475
        };
    }

    public  String levelLetter(){
        switch (getLevel()){
            case 1:
                return "A";
            case 2:
                return "B";
            case 3:
                return "C";
            case 4:
                return "D";
            case 5:
                return "E";
            case 6:
                return "F";
            case 7:
                return "G";
            case 8:
                return "H";
            case 9:
                return "I";
            case 10:
                return "J";
            case 11:
                return "K";
            case 12:
                return "L";
            case 13:
                return "M";
            case 14:
                return "N";
            case 15:
                return "O";
            case 16:
                return "P";
            case 17:
                return "Q";
            case 18:
                return "R";
            case 19:
                return "S";
            case 20:
                return "T";
            case 21:
                return "U";
            case 22:
                return "V";
            case 23:
                return "W";
            case 24:
                return "X";
            case 25:
                return "Y";
            case 26:
                return "Z";
            default:
                return "C";
        }
    }

    @Override
    public Question getQuestion(){
        String[] letters;

        switch (getLevel()){
            case 1:
                letters = new String[]{"A"};
                break;
            case 2:
                letters = new String[]{"A","B"};
                break;
            case 3:
                letters = new String[]{"A","B","C"};
                break;
            case 4:
                letters = new String[]{"A","B","C","D"};
                break;
            case 5:
                letters = new String[]{"A","B","C","D","E"};
                break;
            case 6:
                letters = new String[]{"A","B","C","D","E","F"};
                break;
            case 7:
                letters = new String[]{"A","B","C","D","E","F","G"};
                break;
            case 8:
                letters = new String[]{"A","B","C","D","E","F","G","H"};
                break;
            case 9:
                letters = new String[]{/*"A","B","C","D","E",*/"F","G","H","I"};
                break;
            case 10:
                letters = new String[]{/*"A","B","C","D","E","F",*/"G","H","I","J"};
                break;
            case 11:
                letters = new String[]{/*"A","B","C","D","E","F","G",*/"H","I","J","K"};
                break;
            case 12:
                letters = new String[]{/*"A","B","C","D","E","F","G","H",*/"I","J","K","L"};
                break;
            case 13:
                letters = new String[]{/*"A","B","C","D","E","F","G","H","I",*/"J","K","L","M"};
                break;
            case 14:
                letters = new String[]{/*"A","B","C","D","E","F","G","H","I","J",*/"K","L","M","N"};
                break;
            case 15:
                letters = new String[]{/*"A","B","C","D","E","F","G","H","I","J","K",*/"L","M","N","O"};
                break;
            case 16:
                letters = new String[]{/*"A","B","C","D","E","F","G","H","I","J","K","L",*/"M","N","O","P"};
                break;
            case 17:
                letters = new String[]{/*"A","B","C","D","E","F","G","H","I","J","K","L","M",*/"N","O","P","Q"};
                break;
            case 18:
                letters = new String[]{/*"A","B","C","D","E","F","G","H","I","J","K","L","M","N",*/"O","P","Q","R"};
                break;
            case 19:
                letters = new String[]{/*"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O",*/"P","Q","R","S"};
                break;
            case 20:
                letters = new String[]{/*"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P",*/"Q","R","S","T"};
                break;
            case 21:
                letters = new String[]{/*"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q",*/"R","S","T","U"};
                break;
            case 22:
                letters = new String[]{/*"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R",*/"S","T","U","V"};
                break;
            case 23:
                letters = new String[]{/*"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S",*/"T","U","V","W"};
                break;
            case 24:
                letters = new String[]{/*"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T",*/"U","V","W","X"};
                break;
            case 25:
                letters = new String[]{/*"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U",*/"V","W","X","Y"};
                break;
            case 26:
                letters = new String[]{/*"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V",*/"W","X","Y","Z"};
                break;
            default:
                letters = new String[]{"A","B","C","D","E"};
                break;
        }

        String normal;
        Random r = new Random();

        normal = letters[r.nextInt(letters.length)];

        return new Question(letterToTapCode(normal.toUpperCase()),normal.toUpperCase(),key);
    }

}
