package com.example.androiretrofit3.data.repositories.episode;

import androidx.lifecycle.MutableLiveData;

import com.example.androiretrofit3.App;
import com.example.androiretrofit3.data.models.episode.EpisodeModel;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpisodeDetailRepository {

    public final MutableLiveData<EpisodeModel> _episodeDetail = new MutableLiveData<>();
    public final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();

    public MutableLiveData<EpisodeModel> fetchEpisode(int id) {
        _isLoading.setValue(true);
        App.episodeApiService.fetchEpisode(id).enqueue(new Callback<EpisodeModel>() {
            @Override
            public void onResponse(@NotNull Call<EpisodeModel> call, @NotNull Response<EpisodeModel> response) {
                _episodeDetail.setValue(response.body());
                _isLoading.setValue(false);
            }

            @Override
            public void onFailure(@NotNull Call<EpisodeModel> call, @NotNull Throwable t) {
                _episodeDetail.setValue(null);
                _isLoading.setValue(false);
            }
        });
        return _episodeDetail;

    }
}
