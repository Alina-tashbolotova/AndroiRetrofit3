package com.example.androiretrofit3.ui.fragments.episode.detail;

import androidx.lifecycle.LiveData;

import com.example.androiretrofit3.base.BaseViewModel;
import com.example.androiretrofit3.data.network.dtos.episode.EpisodeModel;
import com.example.androiretrofit3.data.repositories.episode.EpisodeDetailRepository;

public class EpisodeDetailViewModel extends BaseViewModel {

    private final EpisodeDetailRepository episodeDetailRepository = new EpisodeDetailRepository();

    public LiveData<EpisodeModel> fetchEpisode(int id) {
        return episodeDetailRepository.fetchEpisode(id);
    }

    public LiveData<Boolean> fetchLoading() {
        return episodeDetailRepository._isLoading;
    }
}
