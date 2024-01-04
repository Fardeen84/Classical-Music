package com.example.classicalmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Splesh_screen extends AppCompatActivity {

    String gmail =null;
    String key=null, pass=null,pass_db="f",gmail_db="f";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splesh_screen);

        Paper.init(getApplicationContext());
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth=FirebaseAuth.getInstance();


            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {


                    if (execut()) {
                        gmail= Paper.book().read(database.gmail);
                        pass = Paper.book().read(database.pass);



                        Log.d("daxs",gmail+"\n"+pass);
                        Log.d("daxs","ok");
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                      key =auth.getCurrentUser().getUid();
                                      Log.d("daxss",key);
                                      gmail_db  = snapshot.child("User").child(key).child("Gmail").getValue().toString();
                                      pass_db = snapshot.child("User").child(key).child("Pass").getValue().toString();

                                      Log.d("daxss",gmail_db+"\n"+pass_db+"ssssss");


                                      if (gmail_db.equals(gmail) && pass_db.equals(pass)) {
                                          Intent intent = new Intent(getApplicationContext(), Profile.class);
                                          startActivity(intent);
                                          finishAffinity();

                                      } else {
                                          Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                          startActivity(intent);
                                          finishAffinity();

                                      }

                                  }





                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    } else {
                        Intent intent = new Intent(getApplicationContext(), No_Internet.class);
                        startActivity(intent);
                    }
                }
            },2000);


        }




    boolean execut()
    {
        boolean connection=false;

        try {
         ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info=connectivityManager.getActiveNetworkInfo();
            connection=info!=null&&info.isConnected()&&info.isAvailable();
        }
        catch (Exception e)
        {
            Log.d("conection",e.getMessage());
        }
        return connection;
    }
}