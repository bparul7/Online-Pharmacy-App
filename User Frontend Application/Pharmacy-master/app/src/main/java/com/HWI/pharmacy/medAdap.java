package com.HWI.pharmacy;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class medAdap extends RecyclerView.Adapter<medAdap.Viewholder> {

    private List<medModel> medList;

    public medAdap(List<medModel> medList) {
        this.medList = medList;
    }

    @NonNull
    @Override
    public medAdap.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.med_item, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull medAdap.Viewholder holder, int position) {
        holder.setData(medList.get(position).getImage(),medList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return medList.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
        private ImageView cimage;
        private TextView ctitle;

        public Viewholder(@NonNull final View itemView) {
            super(itemView);

            cimage = itemView.findViewById(R.id.med_image);
            ctitle = itemView.findViewById(R.id.med_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(itemView.getContext(),DeliveryActivity.class);
                    i.putExtra("flag",2);
                    itemView.getContext().startActivity(i);

                }
            });
        }

        private void setData(String url, String title ) {
            ctitle.setText(title);
        }
    }
}
