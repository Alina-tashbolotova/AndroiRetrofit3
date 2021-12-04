package com.example.androiretrofit3.ui.fragments.episode.detail;

import androidx.lifecycle.LiveData;

import com.example.androiretrofit3.base.BaseViewModel;
import com.example.androiretrofit3.data.network.dtos.episode.EpisodeModel;
import com.example.androiretrofit3.data.repositories.episode.EpisodeRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class EpisodeDetailViewModel extends BaseViewModel {

    private final EpisodeRepository episodeRepository;

    @Inject
    public EpisodeDetailViewModel(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    public LiveData<EpisodeModel> fetchEpisode(int id) {
        return episodeRepository.fetchEpisode(id);
    }

    public LiveData<Boolean> fetchLoading() {
        return episodeRepository._isLoading;
    }
}
