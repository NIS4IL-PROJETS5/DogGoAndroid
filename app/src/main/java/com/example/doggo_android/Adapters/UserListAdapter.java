package com.example.doggo_android.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doggo_android.Adapters.ViewHolders.UserViewHolder;
import com.example.doggo_android.Interfaces.adminUserItemClickListener;
import com.example.doggo_android.Models.IUser;
import com.example.doggo_android.R;
import com.example.doggo_android.databinding.RvUserlistItemBinding;

import java.util.List;

public class UserListAdapter extends ListAdapter<IUser, UserViewHolder> {

    private adminUserItemClickListener listener;

    public UserListAdapter(@NonNull DiffUtil.ItemCallback<IUser> diffCallback, adminUserItemClickListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return UserViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        IUser current = getItem(position);
        holder.bind(current,listener);
    }

    @Override
    public void onCurrentListChanged(@NonNull List<IUser> previousList, @NonNull List<IUser> currentList) {
        super.onCurrentListChanged(previousList, currentList);
    }

    public static class UserDiffCallback extends DiffUtil.ItemCallback<IUser> {

        @Override
        public boolean areItemsTheSame(@NonNull IUser oldItem, @NonNull IUser newItem) {
            if (oldItem.getId() == null || newItem.getId() == null) {
                return false;
            }
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull IUser oldItem, @NonNull IUser newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }



}
