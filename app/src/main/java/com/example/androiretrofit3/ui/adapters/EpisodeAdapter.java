package com.example.androiretrofit3.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiretrofit3.data.network.dtos.episode.EpisodeModel;
import com.example.androiretrofit3.databinding.ItemEpisodeBinding;
import com.example.androiretrofit3.interfaces.OnItemClickListener;

import org.jetbrains.annotations.NotNull;

public class EpisodeAdapter extends ListAdapter<EpisodeModel, EpisodeAdapter.EpisodeViewHolder> {

    private OnItemClickListener onItemClickListener;

    public EpisodeAdapter() {
        super(new EpisodeDiffUtil());
    }

    @NonNull
    @Override
    public EpisodeAdapter.EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EpisodeViewHolder(
                ItemEpisodeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.onItemClickListener = clickListener;
    }

    public static class EpisodeDiffUtil extends DiffUtil.ItemCallback<EpisodeModel> {

        @Override
        public boolean areItemsTheSame(@NonNull @NotNull EpisodeModel oldItem, @NonNull @NotNull EpisodeModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull @NotNull EpisodeModel oldItem, @NonNull @NotNull EpisodeModel newItem) {
            return oldItem == newItem;
        }
    }

    public class EpisodeViewHolder extends RecyclerView.ViewHolder {

        ItemEpisodeBinding binding;

        public EpisodeViewHolder(@NonNull ItemEpisodeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(EpisodeModel episodeModel) {
            binding.txtItemIdEpisode.setText(String.valueOf(episodeModel.getId()));
            binding.txtItemNameEpisode.setText(String.valueOf(episodeModel.getName()));
            binding.getRoot().setOnClickListener(v -> onItemClickListener.itemClick(episodeModel.getId(), episodeModel.getName()));
        }
    }
}
