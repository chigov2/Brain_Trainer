package com.chigov.braintrainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView textViewQuestion;
    private TextView textViewCounter;
    private TextView textViewTimer;
    // text in TextView будет заноситься в цикле
    private ArrayList<TextView> options = new ArrayList<>();

    private String question;
    private int rightAnswer;
    private int rightAnswerPosition;
    private boolean isPositive;
    private int min = 5;
    private int max = 30;
    private int countOfQuestions = 0;
    private int countOfRightAnswers = 0;
    //закончилась ли игра
    private boolean gameOver = false;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textViewOpinion0 = findViewById(R.id.textViewOpinion0);
        TextView textViewOpinion1 = findViewById(R.id.textViewOpinion1);
        TextView textViewOpinion2 = findViewById(R.id.textViewOpinion2);
        TextView textViewOpinion3 = findViewById(R.id.textViewOpinion3);
        textViewTimer = findViewById(R.id.textViewTimer);
        textViewCounter = findViewById(R.id.textViewCounter);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        options.add(textViewOpinion0);
        options.add(textViewOpinion1);
        options.add(textViewOpinion2);
        options.add(textViewOpinion3);
        playNext();
        //создание таймера - анонимный класс
        CountDownTimer timer = new CountDownTimer(8000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTimer.setText(getTime(millisUntilFinished));
                if (millisUntilFinished <= 4100){
                    textViewTimer.setTextColor(getResources().getColor(android.R.color.holo_red_light));////////////////!!!!!!!!!!!!!!!
                }
            }

            @Override
            public void onFinish() {
                gameOver = true;
                //когда таймер завершается надо получить SharedPreferences preferences
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());//because anonymous class
                //проверка - не сохранен ли уже результат максимального значения
                int max = preferences.getInt("max",0);
                if (countOfRightAnswers >=max){
                    preferences.edit().putInt("max",countOfRightAnswers).apply();
                }

                //создаем запрос на открытие нового окна при завершении работы таймера
                Intent intent = new Intent(MainActivity.this,ScoreActivity.class);
                intent.putExtra("result",countOfRightAnswers);
                startActivity(intent);
            }
        };
        timer.start();
    }

    private void playNext(){
        generateQuestion();
        // в цикле установим значения вариантов
        for (int i = 0; i < options.size(); i++){
            if (i == rightAnswerPosition){
                options.get(i).setText(Integer.toString(rightAnswer));////!!!!!!!!!!!!!!!
            }else{
                options.get(i).setText(Integer.toString(generateWrongAnswer()));
            }
        }
        String score = String.format("%s / %s",countOfRightAnswers,countOfQuestions);
        textViewCounter.setText(score);
    }

    //метод преобразовывает милисекунды в читабельный формат и возвращать фремя в виде строки
    private String getTime(long millis){
        //получить количество секунд
        int seconds = (int) (millis / 1000);
        // из секунд получим минуты и секунды
        int minutes = seconds / 60;
        seconds =seconds % 60;
        //возвращаем строку в нужном формате
        String time = String.format(Locale.getDefault(),"%02d : %02d",minutes,seconds);
        return time;
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

    public void onClickAnswer(View view) {
        //нажимать только если игра не закончена
        if(!gameOver){
        //надо получить getText  с кнопки, на которую нажали - даункаст
        TextView textView = (TextView) view;
        //String answer = (String) textView.getText();
        String answer = textView.getText().toString();
        //чтобв сохранить полученный ответ с правильным ответом - типы должны совпадать
        int chosenAnswer = Integer.parseInt(answer);
        //сравнение правильного ответа с выбранным
        if(chosenAnswer == rightAnswer){
            countOfRightAnswers++;
            Toast.makeText(this, "Okay", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Not Okay", Toast.LENGTH_SHORT).show();
        }
        countOfQuestions++;
        playNext();
        }
    }
}