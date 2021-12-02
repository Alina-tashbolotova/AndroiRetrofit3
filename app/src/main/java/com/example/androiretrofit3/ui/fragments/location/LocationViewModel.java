package com.example.androiretrofit3.ui.fragments.location;

import androidx.lifecycle.LiveData;

import com.example.androiretrofit3.base.BaseViewModel;
import com.example.androiretrofit3.data.network.dtos.RickAndMortyResponse;
import com.example.androiretrofit3.data.network.dtos.location.LocationModel;
import com.example.androiretrofit3.data.repositories.location.LocationRepository;

import java.util.List;

public class LocationViewModel extends BaseViewModel {

    private final LocationRepository locationRepository = new LocationRepository();
    public int page = 1;

    public LiveData<RickAndMortyResponse<LocationModel>> fetchLocations() {
        return locationRepository.fetchLocations(page);
    }

    public LiveData<Boolean> liveDataLocation() {
        return locationRepository._isLoading;
    }

    public List<LocationModel> getLocations() {
        return locationRepository.getLocations();
    }


}
