package com.example.androiretrofit3.ui.fragments.character.detail;

import androidx.lifecycle.LiveData;

import com.example.androiretrofit3.base.BaseViewModel;
import com.example.androiretrofit3.data.network.dtos.character.CharacterModel;
import com.example.androiretrofit3.data.repositories.character.CharacterDetailRepository;

public class CharacterDetailViewModel extends BaseViewModel {

   private final CharacterDetailRepository characterDetailRepository = new CharacterDetailRepository();

   public LiveData<CharacterModel> fetchCharacter(int id){
      return characterDetailRepository.fetchCharacter(id);
   }
   public LiveData<Boolean> fetchLoading(){
      return characterDetailRepository._isLoading;
   }
}
