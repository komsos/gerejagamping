package com.kerjapraktik.androidgereja.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kerjapraktik.androidgereja.DetailBeritaActivity;
import com.kerjapraktik.androidgereja.R;
import com.kerjapraktik.androidgereja.models.BeritaModel;

import java.util.ArrayList;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.MyViewHolder>{
    ArrayList<BeritaModel> list = new ArrayList<>();
    private Context context;

    public BeritaAdapter(Context context, ArrayList<BeritaModel> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_berita,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final BeritaModel beritaModel = list.get(position);

        holder.judulBerita.setText(Html.fromHtml(beritaModel.getJudulBerita()));
        holder.tanggalBerita.setText(beritaModel.getTanggalBerita());
        holder.isiBerita.setText(Html.fromHtml(beritaModel.getIsiBerita()));

        Glide.with(context)
                .load(beritaModel.getUrlImage())
                .centerCrop()
                .crossFade()
                .into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailBeritaActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idBerita", String.valueOf(beritaModel.getIdBerita()));
                intent.putExtra("featured", String.valueOf(beritaModel.getFeatured_image()));
                intent.putExtra("judul", beritaModel.getJudulBerita());
                intent.putExtra("isi", beritaModel.getIsiBerita());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView judulBerita, tanggalBerita, isiBerita;

        public MyViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            judulBerita = (TextView) itemView.findViewById(R.id.judulBerita);
            tanggalBerita = (TextView) itemView.findViewById(R.id.tanggalBerita);
            isiBerita = (TextView) itemView.findViewById(R.id.isiBerita);
        }
    }
}
