package com.example.liaodh.keeprun.livedata;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LifecycleObserver;

public class BaseLiveData extends LiveData implements LifecycleObserver {


    @Override
    protected void onActive() {
        super.onActive();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }

    @Override
    protected void setValue(Object value) {
        super.setValue(value);
    }
}
