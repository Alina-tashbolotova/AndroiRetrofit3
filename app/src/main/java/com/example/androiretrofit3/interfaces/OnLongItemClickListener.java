package com.example.androiretrofit3.interfaces;

import com.example.androiretrofit3.data.network.dtos.character.CharacterModel;

public interface OnLongItemClickListener {
    void onLongItemClick(int position, CharacterModel characterModel);
}
