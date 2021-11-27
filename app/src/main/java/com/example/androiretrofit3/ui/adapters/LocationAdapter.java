package com.example.androiretrofit3.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiretrofit3.data.models.location.LocationModel;
import com.example.androiretrofit3.databinding.ItemLocationBinding;
import com.example.androiretrofit3.interfaces.OnItemClickListener;

import org.jetbrains.annotations.NotNull;

public class LocationAdapter extends ListAdapter<LocationModel, LocationAdapter.LocationViewHolder> {

    private OnItemClickListener onItemClickListener;

    public LocationAdapter() {
        super(new LocationDiffUtil());
    }

    public static class LocationDiffUtil extends DiffUtil.ItemCallback<LocationModel> {

        @Override
        public boolean areItemsTheSame(@NonNull @NotNull LocationModel oldItem, @NonNull @NotNull LocationModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull @NotNull LocationModel oldItem, @NonNull @NotNull LocationModel newItem) {
            return oldItem == newItem;
        }
    }

    @NonNull
    @Override
    public LocationAdapter.LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LocationViewHolder(
                ItemLocationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.LocationViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.onItemClickListener = clickListener;
    }


    public class LocationViewHolder extends RecyclerView.ViewHolder {

        ItemLocationBinding binding;

        public LocationViewHolder(@NonNull ItemLocationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(LocationModel locationModel) {
            binding.itemIdLocation.setText(String.valueOf(locationModel.getId()));
            binding.txtItemName.setText(String.valueOf(locationModel.getName()));
            binding.getRoot().setOnClickListener(v -> onItemClickListener.itemClick(locationModel.getId()));
        }
    }
}
