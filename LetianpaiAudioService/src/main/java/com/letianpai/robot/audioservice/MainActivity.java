package com.letianpai.robot.audioservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.letianpai.robot.audioservice.service.LTPAudioService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Intent intent = new Intent(MainActivity.this, LTPAudioService.class);
       startService(intent);


    }
}