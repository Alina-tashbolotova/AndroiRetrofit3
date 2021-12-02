package com.example.androiretrofit3.ui.fragments.location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiretrofit3.base.BaseFragment;
import com.example.androiretrofit3.data.network.dtos.RickAndMortyResponse;
import com.example.androiretrofit3.data.network.dtos.location.LocationModel;
import com.example.androiretrofit3.databinding.FragmentLocationBinding;
import com.example.androiretrofit3.ui.adapters.LocationAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LocationFragment extends BaseFragment<LocationViewModel, FragmentLocationBinding> {

    private final LocationAdapter locationAdapter = new LocationAdapter();
    private final ArrayList<LocationModel> locationModels = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private int visibleItemCount, totalItemCount, postVisibleItems;

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
        locationAdapter.setOnItemClickListener((id, name) -> {
            if (internetCheck()) {
                Navigation.findNavController(requireView()).navigate(
                        LocationFragmentDirections.actionNavigationLocationToLocationDetailFragment(id));
                Toast.makeText(LocationFragment.this.requireContext(), "Click position" + id, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LocationFragment.this.getContext(), "Нет интернета!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void setupRequest() {
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
                                                locationModels.addAll(locationModel.getResult());
                                                locationAdapter.submitList(locationModels);
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
        if (internetCheck()) {
            viewModel.fetchLocations().observe(getViewLifecycleOwner(), locationModel ->
                    locationAdapter.submitList(locationModel.getResult()));
        } else {
            locationAdapter.submitList((ArrayList<LocationModel>) viewModel.getLocations());
        }
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