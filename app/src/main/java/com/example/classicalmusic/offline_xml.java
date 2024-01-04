package com.example.classicalmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class offline_xml extends AppCompatActivity {

    File[] filesG;
    TextView textView;
    ArrayList<offlin_list_model> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_xml);

        primstion();
        offline_AD ad=new offline_AD(list);
        RecyclerView recyclerView =findViewById(R.id.reccal_offline);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(ad);
        ad.notifyDataSetChanged();



    }

    void primstion()
    {

        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                display();

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();
    }

    ArrayList<File> readmemory(File file)
    {
        ArrayList<File> fileslist=new ArrayList<>();

        File[] filesx=file.listFiles();


        for (File files : filesx)
        {
            if (files.isDirectory()&& !files.isHidden())
            {
                fileslist.addAll(readmemory(files));
            }
            else {
                if (files.getName().endsWith(".mp3")||files.getName().endsWith(".mp4")||files.getName().endsWith(".MP3")||files.getName().endsWith(".MP4")||files.getName().endsWith(".wav"))
                {
                    fileslist.add(files);
                }
            }
        }


        return fileslist;
    }

    void  display()
    {
        ArrayList<File> songlist=readmemory(Environment.getExternalStorageDirectory());
        filesG=new File[songlist.size()];

        {
            for (int i=0;i<songlist.size();i++)
            {
                filesG[i]=songlist.get(i);
                Log.d("dxls",songlist.get(i).getName());
                list.add(new offlin_list_model(filesG,i+1));
            }
        }

    }
}
