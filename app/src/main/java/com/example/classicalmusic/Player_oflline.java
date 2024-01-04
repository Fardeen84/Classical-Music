package com.example.classicalmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Player_oflline extends AppCompatActivity {

    ImageView Play,left,Right,imageView,backarrow;
    SeekBar seekBar;
    TextView name;
    Uri uri;
    int index=0,i=0,count=0;
    File[] file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_oflline);


        Handler handler=new Handler();

        Play=findViewById(R.id.play_btn_F);
        left=findViewById(R.id.left_skip_F);
        Right=findViewById(R.id.right_btn_F);
        seekBar=findViewById(R.id.seekbar_F);
        name=findViewById(R.id.song_name_F);
        backarrow=findViewById(R.id.arrow_F);

        Intent intent=getIntent();
        if (intent!=null) {
            index=intent.getIntExtra("po",0);
             file = (File[]) intent.getSerializableExtra("song");
            uri= Uri.fromFile(file[index]);


            Toast.makeText(this, file[index].getName(), Toast.LENGTH_SHORT).show();
             name.setText(file[index].getName());
        }


        final MediaPlayer[] mediaPlayer = {MediaPlayer.create(getApplicationContext(), uri)};
        mediaPlayer[0].start();
        anim();

        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer[0].isPlaying())
                {
                    mediaPlayer[0].pause();
                    Play.setImageResource(R.drawable.play_arrow);
                }
                else {
                    mediaPlayer[0].start();
                    Play.setImageResource(R.drawable.pause_fill);
                }
            }
        });


        Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer[0].stop();
                mediaPlayer[0].release();
                if (index==file.length-1)
                {
                    index=0;
                    mediaPlayer[0] =MediaPlayer.create(getApplicationContext(),Uri.fromFile(file[index]));
                    mediaPlayer[0].start();
                    Play.setImageResource(R.drawable.pause_fill);
                    name.setText(file[index].getName());
                }
                else {
                    index++;
                    mediaPlayer[0]=MediaPlayer.create(getApplicationContext(),Uri.fromFile(file[index]));
                    mediaPlayer[0].start();
                    Play.setImageResource(R.drawable.pause_fill);
                    name.setText(file[index].getName());
                }
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer[0].stop();
                mediaPlayer[0].release();
                if(index==0)
                {
                    index=file.length-1;
                    mediaPlayer[0] =MediaPlayer.create(getApplicationContext(),Uri.fromFile(file[index]));
                    mediaPlayer[0].start();
                    Play.setImageResource(R.drawable.pause_fill);
                    name.setText(file[index].getName());

                }
                else {
                    index--;
                    mediaPlayer[0] =MediaPlayer.create(getApplicationContext(),Uri.fromFile(file[index]));
                    mediaPlayer[0].start();
                    Play.setImageResource(R.drawable.pause_fill);
                    name.setText(file[index].getName());
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

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mediaPlayer[0].pause();
               mediaPlayer[0].stop();
               mediaPlayer[0]=null;
                finish();
            }
        });

        Player_oflline.this.runOnUiThread(new Runnable() {
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
                            name.setText(file[index].getName());
                            mediaPlayer[0] =MediaPlayer.create(getApplicationContext(),Uri.fromFile(file[index]));
                            mediaPlayer[0].start();
                        }
                    handler.postDelayed(this,1000);
                    }


            }
        });

//        Thread seekth=new Thread(){
//            @Override
//            public void run() {
//                try {
//                seekBar.setMax(mediaPlayer[0].getDuration());
//
//                count=0;
//
//                while (i<=mediaPlayer[0].getDuration())
//                {
//                    count=mediaPlayer[0].getCurrentPosition();
//                    seekBar.setProgress(count);
//
//                    if (count==mediaPlayer[0].getDuration())
//                    {
//                        index++;
//                        mediaPlayer[0] =MediaPlayer.create(getApplicationContext(),Uri.fromFile(file[index]));
//                        mediaPlayer[0].start();
//                        Play.setImageResource(R.drawable.pause_fill);
//                        name.setText(file[index].getName());
//                        count=0;
//                        i=0;
//
//                    }
//
//
//                    sleep(500);
//                }
//
//
//
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        };seekth.start();

        if (mediaPlayer[0].isPlaying())
        {
            Play.setImageResource(R.drawable.pause_fill);
        }
    }

    void anim()
    {
        imageView=findViewById(R.id.image);
        TranslateAnimation animation=new TranslateAnimation(0,-10,0,-200);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setDuration(600);
        animation.setFillEnabled(true);
        animation.setFillAfter(true);
        animation.setRepeatMode(animation.REVERSE);
        animation.setRepeatCount(1);
        imageView.setAnimation(animation);
    }
}