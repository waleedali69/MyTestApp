package com.test.skills.affan.ali.khan.viewmodel;

import android.annotation.SuppressLint;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.test.skills.affan.ali.khan.model.Model;
import com.test.skills.affan.ali.khan.repository.Repository;

import java.util.List;

public class ModelViewModel extends ViewModel {

    private MutableLiveData<List<Model>> models;
    private Repository repo;

    public void init() {
        if (models != null) {
            return;
        }
        repo = Repository.getInstance();
        models = repo.getData();
    }

    public LiveData<List<Model>> getModels() {
        return models;
    }

}