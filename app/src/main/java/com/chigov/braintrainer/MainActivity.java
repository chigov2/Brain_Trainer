package com.chigov.braintrainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView textViewTimer;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        preferences.edit().putInt("test",5).apply();
//        int test = preferences.getInt("test", 0);
//        //Toast.makeText(this, "" + test, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, Integer.toString(test), Toast.LENGTH_SHORT).show();
        textViewTimer = findViewById(R.id.textViewTimer);
        //создаем таймер
        CountDownTimer timer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

            }
        };

    }
}