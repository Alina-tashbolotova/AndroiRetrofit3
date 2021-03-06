package com.example.androiretrofit3.ui.fragments.episode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiretrofit3.base.BaseFragment;
import com.example.androiretrofit3.data.network.dtos.episode.EpisodeModel;
import com.example.androiretrofit3.databinding.FragmentEpisodeBinding;
import com.example.androiretrofit3.ui.adapters.EpisodeAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EpisodeFragment extends BaseFragment<EpisodeViewModel, FragmentEpisodeBinding> {

    private final EpisodeAdapter episodeAdapter = new EpisodeAdapter();
    private final ArrayList<EpisodeModel> episodeModels = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private int visibleItemCount, totalItemCount, postVisibleItems;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEpisodeBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    protected void initialization() {
        viewModel = new ViewModelProvider(this).get(EpisodeViewModel.class);
        setupRecycler();
    }

    private void setupRecycler() {
        linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.recyclerEpisode.setLayoutManager(linearLayoutManager);
        binding.recyclerEpisode.setAdapter(episodeAdapter);

    }

    @Override
    protected void setupListeners() {
        episodeAdapter.setOnItemClickListener((id, name) -> {
            if (internetCheck()) {
                Navigation.findNavController(requireView()).navigate(
                        EpisodeFragmentDirections.actionNavigationEpisodeToEpisodeDetailFragment(id));
                Toast.makeText(EpisodeFragment.this.requireContext(), "Click position" + id, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(EpisodeFragment.this.getContext(), "?????? ??????????????????!!!", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    protected void setupObservers() {
        if (internetCheck()) {
            viewModel.fetchEpisodes().observe(getViewLifecycleOwner(), episodeModel -> {
                episodeModels.addAll(episodeModel.getResult());
                episodeAdapter.submitList(episodeModels);
            });
        } else {
            episodeAdapter.submitList((ArrayList<EpisodeModel>) viewModel.getEpisodes());
        }
        viewModel.liveDataEpisode().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.progressBarEpisode.setVisibility(View.VISIBLE);
                binding.recyclerEpisode.setVisibility(View.GONE);
            } else {
                binding.progressBarEpisode.setVisibility(View.GONE);
                binding.recyclerEpisode.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void setupRequest() {
        binding.recyclerEpisode.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    viewModel.liveDataEpisode().observe(getViewLifecycleOwner(), isLoading -> {
                        if (isLoading) {
                            binding.progressBarEpisodePage.setVisibility(View.GONE);
                            binding.recyclerEpisode.setVisibility(View.VISIBLE);
                            binding.progressBarEpisode.setVisibility(View.GONE);
                            binding.progressBarEpisodePage.setVisibility(View.VISIBLE);
                        } else {
                            binding.progressBarEpisodePage.setVisibility(View.GONE);
                        }
                    });
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    postVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + postVisibleItems) >= totalItemCount) {
                        viewModel.page++;
                        viewModel.fetchEpisodes().observe(getViewLifecycleOwner(), episodeModel -> {
                            if (episodeModel != null) {
                                episodeModels.addAll(episodeModel.getResult());
                                episodeAdapter.submitList(episodeModels);
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
