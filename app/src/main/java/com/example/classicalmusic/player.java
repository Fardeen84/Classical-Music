package com.example.classicalmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class player extends AppCompatActivity  {

    ImageView play,left,right,pic,back_arrow;
    TextView namet;
    int index=0,count=0;

    SeekBar seekBar;

    ArrayList<song_list_model> list;


    Handler handler=new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        namet=findViewById(R.id.song_name);
        play=findViewById(R.id.play_btn);
        left=findViewById(R.id.left_skip);
        right=findViewById(R.id.right_btn);
        pic=findViewById(R.id.image);
        back_arrow=findViewById(R.id.arrow);
        seekBar=findViewById(R.id.seekbar);
        play=findViewById(R.id.play_btn);

        String keyi;
        Intent intent=getIntent();
        keyi=intent.getStringExtra("key");
        index=Integer.parseInt(keyi);

         list=new ArrayList<>();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        final MediaPlayer[] mediaPlayer = {new MediaPlayer()};

        reference.child("Song").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key,name,sing,song_acc,pis;


                for(DataSnapshot sn:snapshot.getChildren())
                {
                    key=sn.getKey();
                    name=snapshot.child(key).child("name").getValue().toString();
                    sing=snapshot.child(key).child("sin").getValue().toString();
                    pis=snapshot.child(key).child("img").getValue().toString();
                    song_acc=snapshot.child(key).child("acc").getValue().toString();


                    list.add(new song_list_model(pis,name,sing,song_acc));
                }




                mediaPlayer[0].setAudioStreamType(AudioManager.STREAM_MUSIC);
                namet.setText(list.get(index).getName());
                Picasso.get().load(list.get(index).getPic()).into(pic);

//                try {
//                    mediaPlayer.setDataSource(getApplicationContext(),Uri.parse(list.get(index).getSong_acc()));
//
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared(MediaPlayer mp) {
//                        mp.start();
//                    }
//                });
//
//


                mediaPlayer[0] =MediaPlayer.create(getApplicationContext(),Uri.parse(list.get(index).getSong_acc()));
                mediaPlayer[0].start();



                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("daxsize",String.valueOf(list.size()));
                        if (mediaPlayer[0].isPlaying())
                        {
                            mediaPlayer[0].pause();
                            play.setImageResource(R.drawable.play_arrow);
                        }
                        else {
                            mediaPlayer[0].start();
                            play.setImageResource(R.drawable.pause_fill);
                        }
                    }
                });


                right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mediaPlayer[0].stop();
                        index++;

                        if (index==list.size())
                        {

                            index=0;
                            namet.setText(list.get(index).getName());
                            Picasso.get().load(list.get(index).getPic()).into(pic);
                            mediaPlayer[0] =MediaPlayer.create(getApplicationContext(),Uri.parse(list.get(index).getSong_acc()));
                            mediaPlayer[0].start();

                        }else {

                            namet.setText(list.get(index).getName());
                            Picasso.get().load(list.get(index).getPic()).into(pic);
                            mediaPlayer[0] =MediaPlayer.create(getApplicationContext(),Uri.parse(list.get(index).getSong_acc()));
                            mediaPlayer[0].start();
                        }


                                      }
                });


                left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mediaPlayer[0].stop();
                        mediaPlayer[0].release();
                        index--;

                        if (index==0)
                        {

                            index=list.size();
                            namet.setText(list.get(index).getName());
                            Picasso.get().load(list.get(index).getPic()).into(pic);
                            mediaPlayer[0] =MediaPlayer.create(getApplicationContext(),Uri.parse(list.get(index).getSong_acc()));
                            mediaPlayer[0].start();

                        }else {

                            namet.setText(list.get(index).getName());
                            Picasso.get().load(list.get(index).getPic()).into(pic);
                            mediaPlayer[0] =MediaPlayer.create(getApplicationContext(),Uri.parse(list.get(index).getSong_acc()));
                            mediaPlayer[0].start();
                        }


                    }
                });


                seekBar.setMax(mediaPlayer[0].getDuration());
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                        // mediaPlayer[0].seekTo(seekBar.getProgress());

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                        mediaPlayer[0].seekTo(seekBar.getProgress());
                    }
                });

                player.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer[0]!=null)
                        {
                            count=0;

                            count = mediaPlayer[0].getCurrentPosition();
                            seekBar.setProgress(count);
                            if (count==mediaPlayer[0].getDuration())
                            {
                                mediaPlayer[0].stop();
                                mediaPlayer[0].release();
                                index++;
                                namet.setText(list.get(index).getName());
                                mediaPlayer[0] =MediaPlayer.create(getApplicationContext(),Uri.parse(String.valueOf(list.get(index))));
                                mediaPlayer[0].start();
                            }
                            handler.postDelayed(this,1000);
                        }


                    }
                });

//                mediaPlayer[0].prepareAsync();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        player.this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (mediaPlayer[0]!=null)
//                {
//                    count=0;
//
//                    count = mediaPlayer[0].getCurrentPosition();
//                    seekBar.setProgress(count);
//                    if (count==mediaPlayer[0].getDuration())
//                    {
//                        mediaPlayer[0].stop();
//                        mediaPlayer[0].release();
//                        index++;
//                       if (index<list.size())
//                       {
//                           namet.setText(list.get(index).getName());
//                           mediaPlayer[0] =MediaPlayer.create(getApplicationContext(),Uri.parse(list.get(index).getSong_acc()));
//                           mediaPlayer[0].start();
//                       }
//                       else {
//                           index=0;
//                           namet.setText(list.get(index).getName());
//                           mediaPlayer[0] =MediaPlayer.create(getApplicationContext(),Uri.parse(list.get(index).getSong_acc()));
//                           mediaPlayer[0].start();
//                       }
//                    }
//                    handler.postDelayed(this,1000);
//                }
//
//
//            }
//        });
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer[0].stop();
                mediaPlayer[0]=null;
            finish();
            }
        });




        if (mediaPlayer[0].isPlaying())
        {
            play.setImageResource(R.drawable.pause_fill);

        }
    }



}