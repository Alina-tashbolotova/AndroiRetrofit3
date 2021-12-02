package com.example.androiretrofit3.data.repositories.character;

import androidx.lifecycle.MutableLiveData;

import com.example.androiretrofit3.App;
import com.example.androiretrofit3.data.network.dtos.character.CharacterModel;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailRepository {

    public final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    private final MutableLiveData<CharacterModel> _characterDetail = new MutableLiveData<>();

    public MutableLiveData<CharacterModel> fetchCharacter(int id) {
        _isLoading.setValue(true);
        App.characterApiService.fetchCharacter(id).enqueue(new Callback<CharacterModel>() {
            @Override
            public void onResponse(@NotNull Call<CharacterModel> call, @NotNull Response<CharacterModel> response) {
                _characterDetail.setValue(response.body());
                _isLoading.setValue(false);
            }

            @Override
            public void onFailure(@NotNull Call<CharacterModel> call, @NotNull Throwable t) {
                _characterDetail.setValue(null);
                _isLoading.setValue(false);
            }
        });
        return _characterDetail;
    }
}
