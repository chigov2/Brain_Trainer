package com.chigov.braintrainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView textViewOpinion0;
    private TextView textViewOpinion1;
    private TextView textViewOpinion2;
    private TextView textViewOpinion3;
    private TextView textViewTimer;
    private TextView textViewCounter;
    private TextView textViewQuestion;
    // text in TextView будет заноситься в цикле
    private ArrayList<TextView> options = new ArrayList<>();


    private String question;
    private int rightAnswer;
    private int rightAnswerPosition;
    private boolean isPositive;
    private int min = 5;
    private int max = 30;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewOpinion0 = findViewById(R.id.textViewOpinion0);
        textViewOpinion1 = findViewById(R.id.textViewOpinion1);
        textViewOpinion2 = findViewById(R.id.textViewOpinion2);
        textViewOpinion3 = findViewById(R.id.textViewOpinion3);
        textViewTimer = findViewById(R.id.textViewTimer);
        textViewCounter = findViewById(R.id.textViewCounter);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        options.add(textViewOpinion0);
        options.add(textViewOpinion1);
        options.add(textViewOpinion2);
        options.add(textViewOpinion3);
        generateQuestion();
        // в цикле установим значения вариантов
        for (int i = 0; i < options.size(); i++){
            if (i == rightAnswerPosition){
                options.get(i).setText(Integer.toString(rightAnswer));////!!!!!!!!!!!!!!!
            }else{
                options.get(i).setText(Integer.toString(generateWrongAnswer()));
            }
        }
    }
    //метод , генерирующий вопрос и правильный ответ
    private void generateQuestion(){
        //получаем два произвольных числа
        int a = (int) (Math.random() * (max-min+1) + min);
        int b = (int) (Math.random() * (max-min+1) + min);
        int mark = (int)( Math.random()*2); // или 0 или 1
        //Log.i("test", String.valueOf(mark));
        isPositive = mark == 1;
        if (isPositive){
            rightAnswer = a+b;
            //формируем вопрос
            question = String.format("%s + %s",a,b);
        }else{
            rightAnswer = a-b;
            question = String.format("%s - %s",a,b);
        }
        //утсанавливаем текст в поле textViewQuestion
        textViewQuestion.setText(question);
        rightAnswerPosition = (int) (Math.random()*4);// 0 till 3
    }

    //метод , генерирующий неправильный ответ
    private int generateWrongAnswer(){
        int result;
        do{
         result = (int) ((Math.random()*max * 2) - (max - min));// 60 - 25 = 35
        }while (result == rightAnswer);
        return result;
    }

}