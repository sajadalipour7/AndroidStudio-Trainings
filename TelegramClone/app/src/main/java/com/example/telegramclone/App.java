package com.example.telegramclone;

import android.app.Application;

import com.parse.Parse;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("a0s7Y9hab8WE7sL6oVf8rPGFMkQsGeXgDyO9BJyG")
                // if defined
                .clientKey("G8B1b7hicqljMuxLVyk4okXsIu3wktBXy43fE9dq")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
