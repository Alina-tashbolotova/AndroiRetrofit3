package com.example.androiretrofit3.ui.fragments.character;

import androidx.lifecycle.LiveData;

import com.example.androiretrofit3.base.BaseViewModel;
import com.example.androiretrofit3.data.network.dtos.RickAndMortyResponse;
import com.example.androiretrofit3.data.network.dtos.character.CharacterModel;
import com.example.androiretrofit3.data.repositories.character.CharacterRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CharacterViewModel extends BaseViewModel {

    private final CharacterRepository characterRepository;
    public int page = 1;

    @Inject
    public CharacterViewModel(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public LiveData<RickAndMortyResponse<CharacterModel>> fetchCharacters() {
        return characterRepository.fetchCharacters(page);
    }

    public LiveData<Boolean> liveDataCharacter() {
        return characterRepository._isLoading;
    }

    public List<CharacterModel> getCharacters() {
        return characterRepository.getCharacters();
    }

}
