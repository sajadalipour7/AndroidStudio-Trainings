package com.example.telegramclone;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import java.util.TimerTask;


public class RefreshTask extends TimerTask {

    private Context context;
    private View view;
    private Handler mHandler=new Handler();

    public RefreshTask(Context context, View view){
        this.context=context;
        this.view=view;
    }



    @Override
    public void run() {


        new Thread(new Runnable() {

            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"Hello",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();

    }
}
