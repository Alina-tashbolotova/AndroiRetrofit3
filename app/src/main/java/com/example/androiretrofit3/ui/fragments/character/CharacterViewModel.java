package com.example.androiretrofit3.ui.fragments.character;

import androidx.lifecycle.LiveData;

import com.example.androiretrofit3.base.BaseViewModel;
import com.example.androiretrofit3.data.models.RickAndMortyResponse;
import com.example.androiretrofit3.data.models.character.CharacterModel;
import com.example.androiretrofit3.data.repositories.character.CharacterRepository;

public class CharacterViewModel extends BaseViewModel {

    public int page = 1;
    private final CharacterRepository characterRepository = new CharacterRepository();

    public LiveData<RickAndMortyResponse<CharacterModel>> fetchCharacters() {
        return characterRepository.fetchCharacters(page);
    }

    public LiveData<Boolean> liveDataCharacter() {
        return characterRepository._isLoading;
    }

}
