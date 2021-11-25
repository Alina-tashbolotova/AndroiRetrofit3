package com.example.androiretrofit3.data.repositories.episode;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.androiretrofit3.App;
import com.example.androiretrofit3.data.models.RickAndMortyResponse;
import com.example.androiretrofit3.data.models.episode.EpisodeModel;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpisodeRepository {

    public final MutableLiveData<RickAndMortyResponse<EpisodeModel>> _episode = new MutableLiveData<>();
    public final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();

    public LiveData<RickAndMortyResponse<EpisodeModel>> fetchEpisodes(
            int page
    ) {
        _isLoading.setValue(true);
        App.episodeApiService.fetchEpisodes(page).enqueue(new Callback<RickAndMortyResponse<EpisodeModel>>() {
            @Override
            public void onResponse(@NotNull Call<RickAndMortyResponse<EpisodeModel>> call, @NotNull Response<RickAndMortyResponse<EpisodeModel>> response) {
                if (response.body() != null) {
                    _episode.setValue(response.body());
                    _isLoading.setValue(false);
                }
            }

            @Override
            public void onFailure(@NotNull Call<RickAndMortyResponse<EpisodeModel>> call, @NotNull Throwable t) {
                _episode.setValue(null);
                _isLoading.setValue(false);
            }
        });
        return _episode;
    }
}
