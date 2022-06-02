package com.tees.checklist.commons;

import android.content.Context;

public class SingletonContext {
    private static Context appContext;

    private static final SingletonContext ourInstance = new SingletonContext();

    public void init(Context context) {
        if(appContext == null) {
            this.appContext = context;
        }
    }
    private Context getContext() {
        return appContext;
    }
    public static Context get() {
        return getInstance().getContext();
    }
    public static synchronized SingletonContext getInstance() {
        return ourInstance;
    }
    public SingletonContext() { }
}