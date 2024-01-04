package com.example.classicalmusic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;


public class Profile extends AppCompatActivity {
    int ke=123;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageView img,profile_btn=findViewById(R.id.profile);



        RecyclerView recyclerView=findViewById(R.id.recycal);
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();

        ArrayList<song_list_model> list=new ArrayList<>();


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);



      //  img=dialog.findViewById(R.id.image);
//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(Profile.this, "Profile", Toast.LENGTH_SHORT).show();
//            }
//        });

        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dilog(v);
            }
        });



       if (execut())
       {
           reference.child("Song").addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   String key,name,sing,pic,song_acc;

                   for(DataSnapshot sn:snapshot.getChildren())
                   {
                       key=sn.getKey();
                       name=snapshot.child(key).child("name").getValue().toString();
                       sing=snapshot.child(key).child("sin").getValue().toString();
                       pic=snapshot.child(key).child("img").getValue().toString();
                       song_acc=snapshot.child(key).child("acc").getValue().toString();

                       list.add(new song_list_model(pic,name,sing,song_acc));


                   }
                   AD ob=new AD(list);
                   recyclerView.setAdapter(ob);

               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });


       }
       else {
           Intent intent=new Intent(getApplicationContext(),Profile.class);
           startActivity(intent);
           finishAffinity();
       }



    }
    void dilog(View v)
    {
        TextView name,gmail;
        MaterialButton offline_button;
        ImageView imageView;

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth=FirebaseAuth.getInstance();

        View view=LayoutInflater.from(v.getContext()).inflate(R.layout.profil_delog,null);

        name=view.findViewById(R.id.user_name);
        gmail=view.findViewById(R.id.user_gmail);
        offline_button=view.findViewById(R.id.offline);
        imageView=view.findViewById(R.id.image123);


        Dialog dialog=new Dialog(v.getContext());
        dialog.setContentView(view);
        dialog.setCancelable(true);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        offline_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view.getContext(), offline_xml.class);
                view.getContext().startActivity(intent);
            }
        });

        offline_button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(view.getContext(), "Fardeen Khan", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        String key;

        key=auth.getUid();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String user_name,user_gmail;
                user_name=snapshot.child("User").child(key).child("Name").getValue().toString();
                user_gmail=snapshot.child("User").child(key).child("Gmail").getValue().toString();
                name.setText(user_name);
                gmail.setText(user_gmail);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
               intent.setAction( Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"set Imag"),ke);
            }
        });


    }

    void back_delog(View v)
    {

        View view=LayoutInflater.from(v.getContext()).inflate(R.layout.exit_delog,null);

        CardView yse,cancel;
        cancel=view.findViewById(R.id.cancel);
        yse=view.findViewById(R.id.yes);

        Dialog dialog=new Dialog(v.getContext());
        dialog.setContentView(view);

        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        yse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
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


    public void onBackPressed() {

        Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();
        back_delog(getCurrentFocus());
    }


@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth=FirebaseAuth.getInstance();
    StorageReference storage=FirebaseStorage.getInstance().getReference();



        if (resultCode==RESULT_OK)
        {
            Log.d("dax123",data.toString());
            if (requestCode==ke)

                Log.d("dax123",data.toString());
            {
                if (data!=null)
                {
                    Log.d("Daxcp","this3");
                   uri=data.getData();

                   Log.d("dax123",uri.toString());

                   storage.child("User Image").child(auth.getUid()).child("image").putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                           Toast.makeText(Profile.this, "Hello", Toast.LENGTH_SHORT).show();


                       }
                   });




                }
            }
        }
   ;

    }


}