package com.example.androiretrofit3.data.repositories.character;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.androiretrofit3.App;
import com.example.androiretrofit3.data.network.dtos.RickAndMortyResponse;
import com.example.androiretrofit3.data.network.dtos.character.CharacterModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterRepository {

    public final MutableLiveData<RickAndMortyResponse<CharacterModel>> _character = new MutableLiveData<>();
    public final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();

    public LiveData<RickAndMortyResponse<CharacterModel>> fetchCharacters(
            int page
    ) {
        _isLoading.setValue(true);
        App.characterApiService.fetchCharacters(page).enqueue(new Callback<RickAndMortyResponse<CharacterModel>>() {
            @Override
            public void onResponse(@NotNull Call<RickAndMortyResponse<CharacterModel>> call, @NotNull Response<RickAndMortyResponse<CharacterModel>> response) {
                if (response.body() != null) {
                    App.characterDao.InsertAll(response.body().getResult());
                    _character.setValue(response.body());
                    _isLoading.setValue(false);
                }
            }

            @Override
            public void onFailure(@NotNull Call<RickAndMortyResponse<CharacterModel>> call, @NotNull Throwable t) {
                _character.setValue(null);
                _isLoading.setValue(false);
            }
        });
        return _character;
    }

    public List<CharacterModel> getCharacters() {
        return App.characterDao.getAll();
    }


}
