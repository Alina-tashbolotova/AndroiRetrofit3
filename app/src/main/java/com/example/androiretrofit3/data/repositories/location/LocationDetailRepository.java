package com.example.androiretrofit3.data.repositories.location;

import androidx.lifecycle.MutableLiveData;

import com.example.androiretrofit3.App;
import com.example.androiretrofit3.data.network.dtos.location.LocationModel;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationDetailRepository {

    public final MutableLiveData<LocationModel> _locationDetail = new MutableLiveData<>();
    public final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();

    public MutableLiveData<LocationModel> fetchLocation(int id) {
        _isLoading.setValue(true);
        App.locationApiService.fetchLocation(id).enqueue(new Callback<LocationModel>() {
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
}
