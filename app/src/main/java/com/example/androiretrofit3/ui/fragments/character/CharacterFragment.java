package com.example.androiretrofit3.ui.fragments.character;

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
import com.example.androiretrofit3.data.network.dtos.character.CharacterModel;
import com.example.androiretrofit3.databinding.FragmentCharacterBinding;
import com.example.androiretrofit3.ui.adapters.CharacterAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CharacterFragment extends BaseFragment<CharacterViewModel, FragmentCharacterBinding> {

    private final CharacterAdapter characterAdapter = new CharacterAdapter();
    private final ArrayList<CharacterModel> characterModels = new ArrayList<>();
    public LinearLayoutManager linearLayoutManager;
    private int visibleItemCount, totalItemCount, postVisibleItems;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCharacterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initialization() {
        viewModel = new ViewModelProvider(this).get(CharacterViewModel.class);
        setupRecycler();
    }

    private void setupRecycler() {
        linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.recyclerCharacter.setLayoutManager(linearLayoutManager);
        binding.recyclerCharacter.setAdapter(characterAdapter);
    }

    @Override
    protected void setupListeners() {
        characterAdapter.setOnItemClickListener((id, name) -> {
            if (internetCheck()) {
                Navigation.findNavController(requireView()).navigate(
                        CharacterFragmentDirections.actionNavigationCharacterToNavigationCharacterDetail(id, name));
            } else {
                Toast.makeText(CharacterFragment.this.getContext(), "Нет интернета!!!", Toast.LENGTH_SHORT).show();
            }
        });
        characterAdapter.setOnLongItemClickListener((position, characterModel) -> {
            Navigation.findNavController(requireView()).navigate(
                    CharacterFragmentDirections.actionNavigationCharacterToCharacterDialogFragment(characterModel.getImage())
            );
        });

    }

    @Override
    protected void setupObservers() {
        if (internetCheck()) {
            viewModel.fetchCharacters().observe(getViewLifecycleOwner(), characterModel -> {
                characterModels.addAll(characterModel.getResult());
                characterAdapter.submitList(characterModels);
            });
        } else {
            characterAdapter.submitList((ArrayList<CharacterModel>) viewModel.getCharacters());
        }
        viewModel.liveDataCharacter().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.progressBarCharacter.setVisibility(View.VISIBLE);
                binding.recyclerCharacter.setVisibility(View.GONE);
            } else {
                binding.progressBarCharacter.setVisibility(View.GONE);
                binding.recyclerCharacter.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void setupRequest() {

        binding.recyclerCharacter.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    viewModel.liveDataCharacter().observe(getViewLifecycleOwner(), isLoading -> {
                        if (isLoading) {
                            binding.progressBarCharacterPage.setVisibility(View.GONE);
                            binding.recyclerCharacter.setVisibility(View.VISIBLE);
                            binding.progressBarCharacter.setVisibility(View.GONE);
                            binding.progressBarCharacterPage.setVisibility(View.VISIBLE);
                        } else {
                            binding.progressBarCharacterPage.setVisibility(View.GONE);
                        }
                    });
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    postVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + postVisibleItems) >= totalItemCount) {
                        viewModel.page++;
                        viewModel.fetchCharacters().observe(getViewLifecycleOwner(), characterModel -> {
                            if (characterModel != null) {
                                characterModels.addAll(characterModel.getResult());
                                characterAdapter.submitList(characterModels);
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
    
    