package com.example.androiretrofit3.ui.fragments.episode.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.example.androiretrofit3.base.BaseFragment;
import com.example.androiretrofit3.databinding.FragmentEpisodeDetailBinding;

import org.jetbrains.annotations.NotNull;

public class EpisodeDetailFragment extends BaseFragment<EpisodeDetailViewModel, FragmentEpisodeDetailBinding> {

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEpisodeDetailBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    protected void initialization() {
        viewModel = new ViewModelProvider(this).get(EpisodeDetailViewModel.class);
    }

    @Override
    protected void setupListeners() {

    }

    @Override
    protected void setupRequest() {
        viewModel.fetchEpisode(EpisodeDetailFragmentArgs.fromBundle(getArguments()).getId())
                .observe(getViewLifecycleOwner(), episodeModel -> {
                    binding.txtItemId.setText(String.valueOf(episodeModel.getId()));
                    binding.txtItemName.setText(String.valueOf(episodeModel.getName()));
                    binding.txtItemEpisode.setText(String.valueOf(episodeModel.getEpisode()));
                    binding.txtItemCreated.setText(String.valueOf(episodeModel.getCreated()));
                    binding.txtItemAirDate.setText(episodeModel.getAir_date());
                });
        viewModel.fetchLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.progressBarEpisodeDetail.setVisibility(View.VISIBLE);
                binding.txtItemName.setVisibility(View.GONE);
                binding.txtItemEpisode.setVisibility(View.GONE);
                binding.txtItemCreated.setVisibility(View.GONE);
                binding.txtItemAirDate.setVisibility(View.GONE);
            } else {
                binding.progressBarEpisodeDetail.setVisibility(View.GONE);
                binding.txtItemName.setVisibility(View.VISIBLE);
                binding.txtItemEpisode.setVisibility(View.VISIBLE);
                binding.txtItemCreated.setVisibility(View.VISIBLE);
                binding.txtItemAirDate.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void setupObservers() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}