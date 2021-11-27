package com.example.androiretrofit3.ui.fragments.episode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiretrofit3.base.BaseFragment;
import com.example.androiretrofit3.data.models.RickAndMortyResponse;
import com.example.androiretrofit3.data.models.episode.EpisodeModel;
import com.example.androiretrofit3.databinding.FragmentEpisodeBinding;
import com.example.androiretrofit3.ui.adapters.EpisodeAdapter;

import org.jetbrains.annotations.NotNull;

public class EpisodeFragment extends BaseFragment<EpisodeViewModel, FragmentEpisodeBinding> {

    private final EpisodeAdapter episodeAdapter = new EpisodeAdapter();
    private LinearLayoutManager linearLayoutManager;
    private int visibleItemCount;
    private int totalItemCount;
    private int postVisibleItems;

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
        episodeAdapter.setOnItemClickListener(id -> Navigation.findNavController(requireView()).navigate(
                EpisodeFragmentDirections.actionNavigationEpisodeToEpisodeDetailFragment(id)
        ));
    }

    @Override
    protected void setupRequest() {
        viewModel.fetchEpisodes();
        viewModel.fetchEpisodes().observe(getViewLifecycleOwner(), new Observer<RickAndMortyResponse<EpisodeModel>>() {
            @Override
            public void onChanged(RickAndMortyResponse<EpisodeModel> episodeModel) {
                if (episodeModel != null) {
                    episodeAdapter.submitList(episodeModel.getResult());
                    String next = episodeModel.getInfo().getNext();
                    if (next != null) {
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
                                                episodeAdapter.submitList(episodeModel.getResult());
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    protected void setupObservers() {
        viewModel.fetchEpisodes().observe(getViewLifecycleOwner(), episodeModel -> episodeAdapter.submitList(episodeModel.getResult()));
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
