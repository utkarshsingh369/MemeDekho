package com.example.memedekho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        VideoView videoview = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.mdsplash);
        videoview.setVideoURI(uri);
        videoview.start();
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.mdsound);
//        mediaPlayer.start();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mediaPlayer.start();
            }
        },600);

        Intent intent=new Intent(this, Screen0.class);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Splash.this, Screen0.class));
                mediaPlayer.stop();
                finish();
            }
        },4000);
    }
}