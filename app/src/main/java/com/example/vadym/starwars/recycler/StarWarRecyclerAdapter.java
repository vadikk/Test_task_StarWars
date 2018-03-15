package com.example.vadym.starwars.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vadym.starwars.R;
import com.example.vadym.starwars.model.StarWar;
import com.example.vadym.starwars.util.OnStarWarClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vadym on 14.03.2018.
 */

public class StarWarRecyclerAdapter extends RecyclerView.Adapter<StarWarViewHolder> {

    private List<StarWar> starWarList = new ArrayList<>();
    private OnStarWarClickListener listener;

    public StarWarRecyclerAdapter() {
    }

    public void onStarWarClickListener(OnStarWarClickListener listener) {
        this.listener = listener;
    }

    public void addAllStarWar(List<StarWar> list) {
        int starPos = getItemCount();
        notifyItemRangeInserted(starPos, list.size());
        starWarList.addAll(list);
    }

    public StarWar getStarWar(int position) {
        if (position < 0 || position > getItemCount())
            return null;

        return starWarList.get(position);
    }

    @Override
    public StarWarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new StarWarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StarWarViewHolder holder, int position) {
        StarWar starWar = starWarList.get(holder.getAdapterPosition());

        if (starWar != null) {
            holder.setText(starWar);

            holder.itemView.setOnClickListener(view -> onStarWarClick(holder.getAdapterPosition()));
        }
    }

    @Override
    public int getItemCount() {
        return starWarList.size();
    }

    private void onStarWarClick(int position) {
        if (listener != null) {
            listener.onClick(position);
        }
    }
}
