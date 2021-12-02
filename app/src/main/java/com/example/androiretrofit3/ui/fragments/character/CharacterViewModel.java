package com.example.androiretrofit3.ui.fragments.character;

import androidx.lifecycle.LiveData;

import com.example.androiretrofit3.base.BaseViewModel;
import com.example.androiretrofit3.data.network.dtos.RickAndMortyResponse;
import com.example.androiretrofit3.data.network.dtos.character.CharacterModel;
import com.example.androiretrofit3.data.repositories.character.CharacterRepository;

import java.util.List;

public class CharacterViewModel extends BaseViewModel {

    private final CharacterRepository characterRepository = new CharacterRepository();
    public int page = 1;

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
