package com.example.androiretrofit3.ui.fragments.episode;

import androidx.lifecycle.LiveData;

import com.example.androiretrofit3.base.BaseViewModel;
import com.example.androiretrofit3.data.network.dtos.RickAndMortyResponse;
import com.example.androiretrofit3.data.network.dtos.episode.EpisodeModel;
import com.example.androiretrofit3.data.repositories.episode.EpisodeRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class EpisodeViewModel extends BaseViewModel {

    private final EpisodeRepository episodeRepository;
    public int page = 1;

    @Inject
    public EpisodeViewModel(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    public LiveData<RickAndMortyResponse<EpisodeModel>> fetchEpisodes() {
        return episodeRepository.fetchEpisodes(page);
    }

    public LiveData<Boolean> liveDataEpisode() {
        return episodeRepository._isLoading;
    }

    public List<EpisodeModel> getEpisodes() {
        return episodeRepository.getEpisodes();
    }
}

