package com.HWI.pharmacy;

import android.media.Image;
import android.view.ViewGroup;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class prescAdapater extends RecyclerView.Adapter<prescAdapater.Viewholder> {

    private List<prescModel> presList;

    public prescAdapater(List<prescModel> presList) {
        this.presList = presList;
    }

    @NonNull
    @Override
    public prescAdapater.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pres_item, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull prescAdapater.Viewholder holder, int position) {
        holder.setData(presList.get(position).getUrl(),presList.get(position).getTitle(),presList.get(position).getMedicine(),
                presList.get(position).getReq());

    }

    @Override
    public int getItemCount() {
        return presList.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
        private ImageView cimage;
        private TextView ctitle, cmedicine, creq;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            cimage = itemView.findViewById(R.id.pres_image);
            ctitle = itemView.findViewById(R.id.pres_title);
            cmedicine = itemView.findViewById(R.id.medicines);
            creq = itemView.findViewById(R.id.pres_req);
        }

        private void setData(String url, String title, String med, String req) {
            ctitle.setText(title);
            cmedicine.setText(med);
            creq.setText(req);
        }
    }
}
