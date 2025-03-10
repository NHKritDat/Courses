package com.example.lab_12.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Integer> shareData = new MutableLiveData<>(0);
    public MutableLiveData<Integer> getShareData() {
        return shareData;
    }
    public void incrementData() {
        if (shareData.getValue() != null) {
            shareData.setValue(shareData.getValue() + 1);
        } else {
            shareData.setValue(1);
        }
    }
}
