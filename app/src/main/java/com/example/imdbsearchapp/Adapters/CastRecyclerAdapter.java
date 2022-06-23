package com.example.imdbsearchapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imdbsearchapp.Models.ActorList;
import com.example.imdbsearchapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CastRecyclerAdapter extends RecyclerView.Adapter<CastViewHolder>{

    Context context;
    List<ActorList> list;

    public CastRecyclerAdapter(Context context, List<ActorList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CastViewHolder(LayoutInflater.from(context).inflate(R.layout.cast_list, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        holder.actor_tv.setText(list.get(position).getName());
        holder.character_tv.setText(list.get(position).getAsCharacter());
        Picasso.get().load(list.get(position).getImage()).resize(792, 1008)
                .onlyScaleDown().into(holder.imageView_actor);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class CastViewHolder extends RecyclerView.ViewHolder {

    TextView actor_tv, character_tv;
    ImageView imageView_actor;
    public CastViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView_actor = itemView.findViewById(R.id.imageView_actor);
        actor_tv = itemView.findViewById(R.id.actor_tv);
        character_tv = itemView.findViewById(R.id.character_tv);
    }
}
