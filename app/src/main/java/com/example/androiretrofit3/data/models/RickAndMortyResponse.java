package com.example.androiretrofit3.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RickAndMortyResponse<T> {

    @SerializedName("info")
    private Info info;

    @SerializedName("results")
    private ArrayList<T> result;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public ArrayList<T> getResult() {
        return result;
    }

    public void setResult(ArrayList<T> result) {
        this.result = result;
    }
}
