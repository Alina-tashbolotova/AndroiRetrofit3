package com.example.androiretrofit3.ui.fragments.character.detail;

import androidx.lifecycle.LiveData;

import com.example.androiretrofit3.base.BaseViewModel;
import com.example.androiretrofit3.data.network.dtos.character.CharacterModel;
import com.example.androiretrofit3.data.repositories.character.CharacterRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CharacterDetailViewModel extends BaseViewModel {

    private final CharacterRepository characterRepository;

    @Inject
    public CharacterDetailViewModel(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public LiveData<CharacterModel> fetchCharacter(int id) {
        return characterRepository.fetchCharacter(id);
    }

    public LiveData<Boolean> fetchLoading() {
        return characterRepository._isLoading;
    }
}
