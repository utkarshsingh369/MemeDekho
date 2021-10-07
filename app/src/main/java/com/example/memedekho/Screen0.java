package com.example.memedekho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class Screen0 extends AppCompatActivity {
    String subReddit="";
    String title0="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_screen0);
    }
    public void setIntent(String subReddit,String title0){
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra(MainActivity.subReddit,subReddit);
        intent.putExtra(MainActivity.title,title0);
        startActivity(intent);
    }

    public void exitApp(View view) {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void general(View view) {
        subReddit="";
        setIntent(subReddit,"GENERAL");
    }

    public void bollywood(View view) {
        subReddit="bollywoodmemes";
        setIntent(subReddit,"BOLLY\nWOOD");
    }

    public void indianDank(View view) {
        subReddit="indiandankmemes";
        setIntent(subReddit,"INDIAN\nDANK");
    }

    public void anime(View view) {
        subReddit="animememes";
        setIntent(subReddit,"ANIME");
    }

    public void animal(View view) {
        subReddit="adviceanimals";
        setIntent(subReddit,"ANIMAL\nMEMES");
    }

    public void marvel(View view) {
        subReddit="marvelmemes";
        setIntent(subReddit,"MARVEL");
    }

    public void science(View view) {
        subReddit="sciencememes";
        setIntent(subReddit,"SCIENCE");
    }

    public void gaming(View view) {
        subReddit="gamingmemes";
        setIntent(subReddit,"GAMING");
    }

    public void pubg(View view) {
        subReddit="pubgmemes";
        setIntent(subReddit,"PUBG");
    }

    public void cuteAnimals(View view) {
        subReddit="aww";
        setIntent(subReddit,"CUTE\nANIMALS");
    }

    public void showUpdate(View view) {
        Intent intent=new Intent(this,UpdateLogs.class);
        startActivity(intent);
    }
}