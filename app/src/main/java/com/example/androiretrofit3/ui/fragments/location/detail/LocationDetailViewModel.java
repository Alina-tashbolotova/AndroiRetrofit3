package com.example.androiretrofit3.ui.fragments.location.detail;

import androidx.lifecycle.LiveData;

import com.example.androiretrofit3.base.BaseViewModel;
import com.example.androiretrofit3.data.network.dtos.location.LocationModel;
import com.example.androiretrofit3.data.repositories.location.LocationRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LocationDetailViewModel extends BaseViewModel {

    private final LocationRepository locationRepository;

    @Inject
    public LocationDetailViewModel(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public LiveData<LocationModel> fetchLocation(int id) {
        return locationRepository.fetchLocation(id);
    }

    public LiveData<Boolean> fetchLoading() {
        return locationRepository._isLoading;
    }
}
