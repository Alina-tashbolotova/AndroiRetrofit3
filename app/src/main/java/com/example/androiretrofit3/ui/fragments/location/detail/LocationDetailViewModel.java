package com.example.androiretrofit3.ui.fragments.location.detail;

import androidx.lifecycle.LiveData;

import com.example.androiretrofit3.base.BaseViewModel;
import com.example.androiretrofit3.data.network.dtos.location.LocationModel;
import com.example.androiretrofit3.data.repositories.location.LocationDetailRepository;

public class LocationDetailViewModel extends BaseViewModel {

    private final LocationDetailRepository locationDetailRepository = new LocationDetailRepository();

    public LiveData<LocationModel> fetchLocation(int id) {
        return locationDetailRepository.fetchLocation(id);
    }
    public LiveData<Boolean> fetchLoading() {
        return locationDetailRepository._isLoading;
    }
}
