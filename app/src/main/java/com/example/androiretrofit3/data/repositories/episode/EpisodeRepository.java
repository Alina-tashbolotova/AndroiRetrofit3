package com.example.androiretrofit3.data.repositories.episode;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.androiretrofit3.App;
import com.example.androiretrofit3.data.db.daos.EpisodeDao;
import com.example.androiretrofit3.data.network.apiservice.EpisodeApiService;
import com.example.androiretrofit3.data.network.dtos.RickAndMortyResponse;
import com.example.androiretrofit3.data.network.dtos.episode.EpisodeModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpisodeRepository {

    private final EpisodeApiService episodeApiService;
    private final EpisodeDao episodeDao;
    @Inject
    public EpisodeRepository(EpisodeApiService episodeApiService,EpisodeDao episodeDao) {
        this.episodeApiService = episodeApiService;
        this.episodeDao = episodeDao;
    }

    public final MutableLiveData<RickAndMortyResponse<EpisodeModel>> _episode = new MutableLiveData<>();
    public final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();

    public LiveData<RickAndMortyResponse<EpisodeModel>> fetchEpisodes(
            int page
    ) {
        _isLoading.setValue(true);
        episodeApiService.fetchEpisodes(page).enqueue(new Callback<RickAndMortyResponse<EpisodeModel>>() {
            @Override
            public void onResponse(@NotNull Call<RickAndMortyResponse<EpisodeModel>> call, @NotNull Response<RickAndMortyResponse<EpisodeModel>> response) {
                if (response.body() != null) {
                    episodeDao.InsertAll(response.body().getResult());
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


    public final MutableLiveData<EpisodeModel> _episodeDetail = new MutableLiveData<>();

    public MutableLiveData<EpisodeModel> fetchEpisode(int id) {
        _isLoading.setValue(true);
        episodeApiService.fetchEpisode(id).enqueue(new Callback<EpisodeModel>() {
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

    public List<EpisodeModel> getEpisodes() {
        return episodeDao.getAll();
    }
}
