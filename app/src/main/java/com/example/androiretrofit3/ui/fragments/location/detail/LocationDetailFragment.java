package com.example.androiretrofit3.ui.fragments.location.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.example.androiretrofit3.base.BaseFragment;
import com.example.androiretrofit3.databinding.FragmentLocationDetailBinding;

import org.jetbrains.annotations.NotNull;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LocationDetailFragment extends BaseFragment<LocationDetailViewModel, FragmentLocationDetailBinding> {

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLocationDetailBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    protected void initialization() {
        viewModel = new ViewModelProvider(this).get(LocationDetailViewModel.class);
    }

    @Override
    protected void setupListeners() {
    }

    @Override
    protected void setupRequest() {
        viewModel.fetchLocation(LocationDetailFragmentArgs.fromBundle(getArguments()).getId())
                .observe(getViewLifecycleOwner(), locationModel -> {
                    binding.txtItemId.setText(String.valueOf(locationModel.getId()));
                    binding.txtItemName.setText(String.valueOf(locationModel.getName()));
                    binding.txtItemCreated.setText(String.valueOf(locationModel.getCreated()));
                    binding.txtItemDimension.setText(String.valueOf(locationModel.getDimension()));
                    binding.txtItemType.setText(String.valueOf(locationModel.getType()));
                });
        viewModel.fetchLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.progressBarLocationDetail.setVisibility(View.VISIBLE);
                binding.txtItemId.setVisibility(View.GONE);
                binding.txtItemName.setVisibility(View.GONE);
                binding.txtItemCreated.setVisibility(View.GONE);
                binding.txtItemType.setVisibility(View.GONE);
                binding.txtItemDimension.setVisibility(View.GONE);
            } else {
                binding.progressBarLocationDetail.setVisibility(View.GONE);
                binding.txtItemId.setVisibility(View.VISIBLE);
                binding.txtItemName.setVisibility(View.VISIBLE);
                binding.txtItemCreated.setVisibility(View.VISIBLE);
                binding.txtItemDimension.setVisibility(View.VISIBLE);
                binding.txtItemType.setVisibility(View.VISIBLE);
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