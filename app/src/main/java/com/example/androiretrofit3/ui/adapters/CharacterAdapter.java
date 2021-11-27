package com.example.androiretrofit3.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androiretrofit3.data.models.character.CharacterModel;
import com.example.androiretrofit3.databinding.ItemCharacterBinding;
import com.example.androiretrofit3.interfaces.OnItemClickListener;
import com.example.androiretrofit3.interfaces.OnLongItemClickListener;

import org.jetbrains.annotations.NotNull;

public class CharacterAdapter extends ListAdapter<CharacterModel, CharacterAdapter.CharacterViewHolder> {

    private OnItemClickListener onItemClickListener;
    private OnLongItemClickListener onLongItemClickListener;

    public CharacterAdapter() {
        super(new CharacterDiffUtil());
    }

    public static class CharacterDiffUtil extends DiffUtil.ItemCallback<CharacterModel> {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull CharacterModel oldItem, @NonNull @NotNull CharacterModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull @NotNull CharacterModel oldItem, @NonNull @NotNull CharacterModel newItem) {
            return oldItem == newItem;
        }
    }

    @NotNull
    @Override
    public CharacterAdapter.CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CharacterViewHolder(
                ItemCharacterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterAdapter.CharacterViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnLongItemClickListener(OnLongItemClickListener longItemClickListener) {
        this.onLongItemClickListener = longItemClickListener;
    }


    public class CharacterViewHolder extends RecyclerView.ViewHolder {

        private final ItemCharacterBinding binding;

        public CharacterViewHolder(@NonNull ItemCharacterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void onBind(CharacterModel characterModel) {
            Glide.with(binding.imageItemCharacter)
                    .load(characterModel.getImage())
                    .into(binding.imageItemCharacter);
            binding.txtItemCharacterName.setText(characterModel.getName());
            binding.getRoot().setOnClickListener(v -> onItemClickListener.itemClick(characterModel.getId()));
            binding.getRoot().setOnLongClickListener(v -> {
                onLongItemClickListener.onLongItemClick(getAdapterPosition(), characterModel);
                return false;
            });
        }
    }
}
