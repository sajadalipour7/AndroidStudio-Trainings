package com.example.instagramclone;

import android.app.Application;
import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("408C0e5U5H7M5bcEnFLqjg49vVMBmy9uyC5CYc70")
                // if defined
                .clientKey("3xI0DUK7ECkP1ndev59fikpBnpcWKzep1iIaeDlE")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
