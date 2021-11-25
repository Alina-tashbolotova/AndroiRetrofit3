package com.example.androiretrofit3.ui.fragments.character;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.androiretrofit3.databinding.FragmentCharacterDialogBinding;

public class CharacterDialogFragment extends DialogFragment {

    private FragmentCharacterDialogBinding binding;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentCharacterDialogBinding.inflate(LayoutInflater.from(getContext()));

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(binding.getRoot())
                .create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setupArgs();
        return alertDialog;

    }

    private void setupArgs() {
        Glide.with(binding.imageDialogCharacter)
                .load(CharacterDialogFragmentArgs.fromBundle(getArguments()).getPhoto())
                .into(binding.imageDialogCharacter);

    }
}