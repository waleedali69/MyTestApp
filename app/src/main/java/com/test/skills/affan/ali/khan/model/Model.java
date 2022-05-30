package com.test.skills.affan.ali.khan.model;

import android.widget.ProgressBar;

public class Model {

    private String title;
    private String imageUrl;
    private String progressBar;

    public Model(String title, String imageUrl, String progressBar) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.progressBar = progressBar;
    }

    public Model() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProgress() {
        return progressBar;
    }

    public void setProgress(String progress) {
        this.progressBar = progress;
    }

}