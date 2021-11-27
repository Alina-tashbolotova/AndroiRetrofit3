package com.example.androiretrofit3.ui.fragments.location;

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
import com.example.androiretrofit3.data.models.location.LocationModel;
import com.example.androiretrofit3.databinding.FragmentLocationBinding;
import com.example.androiretrofit3.ui.adapters.LocationAdapter;

import org.jetbrains.annotations.NotNull;

public class LocationFragment extends BaseFragment<LocationViewModel, FragmentLocationBinding> {

    private final LocationAdapter locationAdapter = new LocationAdapter();
    private LinearLayoutManager linearLayoutManager;
    private int visibleItemCount;
    private int totalItemCount;
    private int postVisibleItems;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLocationBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    protected void initialization() {
        viewModel = new ViewModelProvider(this).get(LocationViewModel.class);
        setupRecycler();
    }

    private void setupRecycler() {
        linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.recyclerLocation.setLayoutManager(linearLayoutManager);
        binding.recyclerLocation.setAdapter(locationAdapter);
    }

    @Override
    protected void setupListeners() {
        locationAdapter.setOnItemClickListener(id -> Navigation.findNavController(requireView()).navigate(
                LocationFragmentDirections.actionNavigationLocationToLocationDetailFragment(id)
        ));
    }

    @Override
    protected void setupRequest() {
        viewModel.fetchLocations();
        viewModel.fetchLocations().observe(getViewLifecycleOwner(), locationModel -> {
            if (locationModel != null) {
                locationAdapter.submitList(locationModel.getResult());
                String next = locationModel.getInfo().getNext();
                if (next != null) {
                    binding.recyclerLocation.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if (dy > 0) {
                                viewModel.liveDataLocation().observe(getViewLifecycleOwner(), isLoading -> {
                                    if (isLoading) {
                                        binding.progressBarLocationPage.setVisibility(View.GONE);
                                        binding.recyclerLocation.setVisibility(View.VISIBLE);
                                        binding.progressBarLocation.setVisibility(View.GONE);
                                        binding.progressBarLocationPage.setVisibility(View.VISIBLE);
                                    } else {
                                        binding.progressBarLocationPage.setVisibility(View.GONE);
                                    }
                                });
                                visibleItemCount = linearLayoutManager.getChildCount();
                                totalItemCount = linearLayoutManager.getItemCount();
                                postVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                                if ((visibleItemCount + postVisibleItems) >= totalItemCount) {
                                    viewModel.page++;
                                    viewModel.fetchLocations().observe(getViewLifecycleOwner(), new Observer<RickAndMortyResponse<LocationModel>>() {
                                        @Override
                                        public void onChanged(RickAndMortyResponse<LocationModel> locationModel) {
                                            if (locationModel != null) {
                                                locationAdapter.submitList(locationModel.getResult());
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void setupObservers() {
        viewModel.fetchLocations().observe(getViewLifecycleOwner(), locationModel -> locationAdapter.submitList(locationModel.getResult()));
        viewModel.liveDataLocation().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.progressBarLocation.setVisibility(View.VISIBLE);
                binding.recyclerLocation.setVisibility(View.GONE);
            } else {
                binding.progressBarLocation.setVisibility(View.GONE);
                binding.recyclerLocation.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}