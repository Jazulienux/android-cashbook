package com.example.bnsp_jazulienux.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bnsp_jazulienux.Models.ItemModels;
import com.example.bnsp_jazulienux.R;

import java.util.List;

public class ItemAdapters extends RecyclerView.Adapter<ItemAdapters.MyHolderItem> {

    public List<ItemModels> data;
    LayoutInflater layoutInflater;
    Context c;

    public ItemAdapters(Context c , List<ItemModels> data) {
        this.c = c;
        this.layoutInflater = LayoutInflater.from(this.c);
        this.data = data;
    }

    @NonNull
    @Override
    public ItemAdapters.MyHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item,parent,false);
        return new MyHolderItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapters.MyHolderItem holder, int position) {
        ItemModels itemModels = data.get(position);

        holder.cash.setText(itemModels.getCash());
        holder.keterangan.setText(itemModels.getKeterangan());
        holder.tgl.setText(itemModels.getTgl());
        holder.iconCash.setImageResource(itemModels.getImgItem());
    }

    @Override
    public int getItemCount() {
        return (data != null) ? data.size() : 0;
    }

    public class MyHolderItem extends RecyclerView.ViewHolder {
        public TextView cash, keterangan, tgl;
        public ImageView iconCash;

        public MyHolderItem(@NonNull View itemView) {
            super(itemView);

            cash = itemView.findViewById(R.id.cash);
            keterangan = itemView.findViewById(R.id.keterangan);
            tgl = itemView.findViewById(R.id.tgl);
            iconCash = itemView.findViewById(R.id.iconCash);
        }

    }
}
