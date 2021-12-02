package com.example.androiretrofit3.ui.fragments.character.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.androiretrofit3.R;
import com.example.androiretrofit3.base.BaseFragment;
import com.example.androiretrofit3.databinding.FragmentCharacterDetailBinding;

import org.jetbrains.annotations.NotNull;

public class CharacterDetailFragment extends BaseFragment<CharacterDetailViewModel, FragmentCharacterDetailBinding> {

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initialization() {
        viewModel = new ViewModelProvider(this).get(CharacterDetailViewModel.class);
    }

    @Override
    protected void setupListeners() {
    }

    @Override
    protected void setupRequest() {
        viewModel.fetchCharacter(CharacterDetailFragmentArgs.fromBundle(getArguments()).getId())
                .observe(getViewLifecycleOwner(), characterModel -> {
                    Glide.with(binding.imageItemCharacterDetail)
                            .load(characterModel.getImage())
                            .into(binding.imageItemCharacterDetail);
                    binding.txtItemIdDetail.setText(String.valueOf(characterModel.getId()));
                    binding.txtItemNameDetail.setText(String.valueOf(characterModel.getName()));
                    binding.txtItemStatusDetail.setText(String.valueOf(characterModel.getStatus()));
                    binding.txtItemCharacterType.setText(String.valueOf(characterModel.getType()));
                    if (characterModel.getStatus() != null) {
                        switch (characterModel.getStatus()) {
                            case "Alive":
                                binding.viewStatusDetail.setBackgroundResource(R.drawable.oval);
                                break;
                            case "Dead":
                                binding.viewStatusDetail.setBackgroundResource(R.drawable.oval2);
                                break;

                        }
                    }
                });
        viewModel.fetchLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.progressBarCharacterDetail.setVisibility(View.VISIBLE);
                binding.imageItemCharacterDetail.setVisibility(View.GONE);
                binding.txtItemIdDetail.setVisibility(View.GONE);
                binding.txtItemNameDetail.setVisibility(View.GONE);
                binding.txtItemStatusDetail.setVisibility(View.GONE);
                binding.txtItemCharacterType.setVisibility(View.GONE);
            } else {
                binding.progressBarCharacterDetail.setVisibility(View.GONE);
                binding.imageItemCharacterDetail.setVisibility(View.VISIBLE);
                binding.txtItemIdDetail.setVisibility(View.VISIBLE);
                binding.txtItemNameDetail.setVisibility(View.VISIBLE);
                binding.txtItemStatusDetail.setVisibility(View.VISIBLE);
                binding.txtItemCharacterType.setVisibility(View.VISIBLE);
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