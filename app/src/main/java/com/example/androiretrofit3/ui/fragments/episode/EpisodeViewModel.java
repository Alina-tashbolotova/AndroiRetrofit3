package com.example.androiretrofit3.ui.fragments.episode;

import androidx.lifecycle.LiveData;

import com.example.androiretrofit3.base.BaseViewModel;
import com.example.androiretrofit3.data.models.RickAndMortyResponse;
import com.example.androiretrofit3.data.models.episode.EpisodeModel;
import com.example.androiretrofit3.data.repositories.episode.EpisodeRepository;

public class EpisodeViewModel extends BaseViewModel {

    public int page = 1;
    private final EpisodeRepository episodeRepository = new EpisodeRepository();

    public LiveData<RickAndMortyResponse<EpisodeModel>> fetchEpisodes() {
        return episodeRepository.fetchEpisodes(page);
    }
    public LiveData<Boolean> liveDataEpisode() {
        return episodeRepository._isLoading;
    }
}