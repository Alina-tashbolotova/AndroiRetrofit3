package com.example.androiretrofit3.ui.fragments.character;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiretrofit3.base.BaseFragment;
import com.example.androiretrofit3.databinding.FragmentCharacterBinding;
import com.example.androiretrofit3.ui.adapters.CharacterAdapter;

import org.jetbrains.annotations.NotNull;

public class CharacterFragment extends BaseFragment<CharacterViewModel, FragmentCharacterBinding> {

    private final CharacterAdapter characterAdapter = new CharacterAdapter();
    public LinearLayoutManager linearLayoutManager;
    private int visibleItemCount;
    private int totalItemCount;
    private int postVisibleItems;

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
        characterAdapter.setOnItemClickListener(id -> Navigation.findNavController(requireView()).navigate(
                CharacterFragmentDirections.actionNavigationCharacterToNavigationCharacterDetail().setId(id)));

        characterAdapter.setOnLongItemClickListener((position, characterModel) -> Navigation.findNavController(requireView()).navigate(
                CharacterFragmentDirections.actionNavigationCharacterToCharacterDialogFragment(characterModel.getImage())
        ));
    }

    @Override
    protected void setupRequest() {
        viewModel.fetchCharacters();
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
                                characterAdapter.submitList(characterModel.getResult());
                            }
                        });

                    }
                }
            }
        });
    }

    @Override
    protected void setupObservers() {
        viewModel.fetchCharacters().observe(getViewLifecycleOwner(), characterModel -> characterAdapter.submitList(characterModel.getResult()));
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
    
    