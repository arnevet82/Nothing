package com.chuk3d;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Admin on 21/08/2017.
 */

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

    }

    @Override
    public void onResume(){
        super.onResume();
        moveToNextScreen();

    }


    public void moveToNextScreen(){

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplication(), CategoriesActivity.class);
                startActivity(intent);

            }
        }, 1500);

    }

}
