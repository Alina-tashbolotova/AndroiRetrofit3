package com.example.androiretrofit3.ui.fragments.location;

import androidx.lifecycle.LiveData;

import com.example.androiretrofit3.base.BaseViewModel;
import com.example.androiretrofit3.data.models.RickAndMortyResponse;
import com.example.androiretrofit3.data.models.location.LocationModel;
import com.example.androiretrofit3.data.repositories.location.LocationRepository;

public class LocationViewModel extends BaseViewModel {

    public int page = 1;
    private final LocationRepository locationRepository = new LocationRepository();

    public LiveData<RickAndMortyResponse<LocationModel>> fetchLocations() {
        return locationRepository.fetchLocations(page);
    }
    public LiveData<Boolean> liveDataLocation() {
        return locationRepository._isLoading;
    }
}
