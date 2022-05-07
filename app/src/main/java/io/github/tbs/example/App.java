package io.github.tbs.example;

import android.app.Application;

import io.github.wong1988.tbs.TBSCore;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        TBSCore.init(this, true);
    }
}
