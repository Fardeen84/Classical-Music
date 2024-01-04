package com.example.classicalmusic;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class offline_AD  extends RecyclerView.Adapter<offline_AD.offlinsong>{

    ArrayList<offlin_list_model> list=new ArrayList();

    offline_AD(ArrayList<offlin_list_model> list)
    {
        this.list=list;
    }
    @NonNull
    @Override
    public offlinsong onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_offline,parent,false);
        return new offlinsong(view);
    }

    @Override
    public void onBindViewHolder(@NonNull offlinsong holder, int position) {
        File[] file=list.get(position).getFiles();
        String nam=file[position].getName();
        int number=list.get(position).getNum();
        holder.song.setText(nam);
        holder.num.setText(number+"");
        int p=position;
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),Player_oflline.class);
                intent.putExtra("song",file);
                intent.putExtra("po",p);
                v.getContext().startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class offlinsong extends RecyclerView.ViewHolder{

        TextView song,num;
        LinearLayout linearLayout;
        public offlinsong(@NonNull View itemView) {
            super(itemView);
            song=itemView.findViewById(R.id.name);
            linearLayout=itemView.findViewById(R.id.liner);
            num=itemView.findViewById(R.id.number);
        }
    }
}
