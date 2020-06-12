package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("vZ2vUTKBNL6PzcMP9ms0yLzfDQdKj6qSIKRjKTqG")
                // if defined
                .clientKey("VLwEFfm3qYYKqWgWYC2nTDbeKMSqEydhqW0XEJhB")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
