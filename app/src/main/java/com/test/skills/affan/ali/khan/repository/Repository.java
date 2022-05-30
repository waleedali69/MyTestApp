package com.test.skills.affan.ali.khan.repository;

import androidx.lifecycle.MutableLiveData;

import com.test.skills.affan.ali.khan.model.Model;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static Repository instance;
    private List<Model> dataSet = new ArrayList<>();

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }

        return instance;
    }

    public MutableLiveData<List<Model>> getData() {
        setImages();
        MutableLiveData<List<Model>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setImages() {
        dataSet.add(new Model("Video 1",
                "https://www.sample-videos.com/video123/mp4/720/big_buck_bunny_720p_30mb.mp4", "0"));

        dataSet.add(new Model("Video 2",
                "https://download.samplelib.com/mp4/sample-15s.mp4", "0"));

        dataSet.add(new Model("Video 3",
                "https://download.samplelib.com/mp4/sample-30s.mp4", "0"));

        dataSet.add(new Model("Video 4",
                "https://www.appsloveworld.com/wp-content/uploads/2018/10/640.mp4", "0"));

        dataSet.add(new Model("Video 5",
                "https://www.appsloveworld.com/wp-content/uploads/2018/10/Sample-Videos-Mp425.mp4", "0"));

    }
}
