package com.example.classicalmusic;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AD extends RecyclerView.Adapter<AD.song_hold>{

    ArrayList<song_list_model> list;
    AD(ArrayList<song_list_model> list)
    {
        this.list=list;

    }

    @NonNull
    @Override
    public song_hold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list,parent,false);
        return new song_hold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull song_hold holder, int position) {

        String pic=list.get(position).getPic();
        String name=list.get(position).getName();
        String singer=list.get(position).getSinger();
        String song_acc=list.get(position).getSong_acc();

       holder.name.setText(name);
       holder.singer.setText(singer);
        Picasso.get().load(Uri.parse(pic)).into(holder.imageView);


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), player.class);
//                intent.putExtra("img",pic);
//                intent.putExtra("name",name);
//                intent.putExtra("acc",song_acc);
                intent.putExtra("key",String.valueOf(position));
                        v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class song_hold extends RecyclerView.ViewHolder{
TextView name,singer;
ImageView imageView;
LinearLayout layout;
        public song_hold(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            singer=itemView.findViewById(R.id.singer);
            imageView=itemView.findViewById(R.id.image);
            layout=itemView.findViewById(R.id.liner);


        }
    }
}
