package com.example.androiretrofit3.data.repositories.location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.androiretrofit3.App;
import com.example.androiretrofit3.data.db.daos.LocationDao;
import com.example.androiretrofit3.data.network.apiservice.LocationApiService;
import com.example.androiretrofit3.data.network.dtos.RickAndMortyResponse;
import com.example.androiretrofit3.data.network.dtos.location.LocationModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationRepository {

    private final LocationApiService locationApiService;
    private final LocationDao locationDao;
    @Inject
    public LocationRepository(LocationApiService locationApiService,LocationDao locationDao) {
        this.locationApiService = locationApiService;
        this.locationDao = locationDao;
    }

    public final MutableLiveData<RickAndMortyResponse<LocationModel>> _location = new MutableLiveData<>();
    public final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();

    public LiveData<RickAndMortyResponse<LocationModel>> fetchLocations(
            int page
    ) {
        _isLoading.setValue(true);
        locationApiService.fetchLocations(page).enqueue(new Callback<RickAndMortyResponse<LocationModel>>() {
            @Override
            public void onResponse(@NotNull Call<RickAndMortyResponse<LocationModel>> call, @NotNull Response<RickAndMortyResponse<LocationModel>> response) {
                if (response.body() != null) {
                    locationDao.InsertAll(response.body().getResult());
                    _location.setValue(response.body());
                    _isLoading.setValue(false);
                }
            }

            @Override
            public void onFailure(@NotNull Call<RickAndMortyResponse<LocationModel>> call, @NotNull Throwable t) {
                _location.setValue(null);
                _isLoading.setValue(false);
            }
        });
        return _location;
    }

    public final MutableLiveData<LocationModel> _locationDetail = new MutableLiveData<>();

    public MutableLiveData<LocationModel> fetchLocation(int id) {
        _isLoading.setValue(true);
        locationApiService.fetchLocation(id).enqueue(new Callback<LocationModel>() {
            @Override
            public void onResponse(@NotNull Call<LocationModel> call, @NotNull Response<LocationModel> response) {
                _locationDetail.setValue(response.body());
                _isLoading.setValue(false);
            }

            @Override
            public void onFailure(@NotNull Call<LocationModel> call, @NotNull Throwable t) {
                _locationDetail.setValue(null);
                _isLoading.setValue(false);
            }
        });
        return _locationDetail;
    }

    public List<LocationModel> getLocations() {
        return locationDao.getAll();
    }


}
