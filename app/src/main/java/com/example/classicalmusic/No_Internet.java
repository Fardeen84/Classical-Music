package com.example.classicalmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class No_Internet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        Button refresh,offline;
         refresh=findViewById(R.id.refresh);
         offline=findViewById(R.id.offline);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (execut())
                {
                    userchek();
                    Intent intent=new Intent(getApplicationContext(),Profile.class);
                    startActivity(intent);
                    finishAffinity();
                }else {
                    Toast.makeText(No_Internet.this, "NO Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), offline_xml.class);
                startActivity(intent);
                finishAffinity();
            }
        });
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
    void userchek()
    {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth=FirebaseAuth.getInstance();

        reference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String gmail = snapshot.child(auth.getUid()).child("Gmail").getValue().toString();
                String key, pass = snapshot.child(auth.getUid()).child("Pass").getValue().toString();

                for (DataSnapshot sn : snapshot.getChildren()) {
                    key = sn.getKey();
                    String gmail_db = snapshot.child(key).child("Gmail").getValue().toString();
                    String pass_db = snapshot.child(key).child("Pass").getValue().toString();

                    if (gmail_db.equals(gmail) && pass_db.equals(pass)) {
                        Intent intent = new Intent(getApplicationContext(), Profile.class);
                        startActivity(intent);
                        finishAffinity();
                        break;
                    } else {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}