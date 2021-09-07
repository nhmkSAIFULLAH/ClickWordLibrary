package com.domesoft.clickwordlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.domesoft.wordclicker.WordClicker;

public class MainActivity extends AppCompatActivity {

    TextView tvContent, tvShow;
    String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvContent = findViewById(R.id.tvContent);
        tvShow = findViewById(R.id.tvShow);
        content = "The things we do best in life are the things life we enjoy doing. " +
                "If you aren't having fun learning English, you're not studying the right way! " +
                "You can be a serious student who has fun at the same time. Make up your own " +
                "If you aren't having fun learning English, you're not studying the right way! " +
                "You can be a serious student who has fun at the same time. Make up your own " +
                "If you aren't having fun learning English, you're not studying the right way! " +
                "You can be a serious student who has fun at the same time. Make up your own " +
                "If you aren't having fun learning English, you're not studying the right way! " +
                "You can be a serious student who has fun at the same time. Make up your own " +
                "If you aren't having fun learning English, you're not studying the right way! " +
                "You can be a serious student who has fun at the same time. Make up your own " +
                "rewards program to give yourself incentives to stay on task.";

        new WordClicker(tvContent,content).getClickedWord(clickedWord -> tvShow.setText(clickedWord)).setSelectedColor("#fcba03").create();


    }
}