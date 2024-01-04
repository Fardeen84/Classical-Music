package com.example.classicalmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LogIn extends AppCompatActivity {

    EditText email, password;
    MaterialButton signin, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        TextView forgot = findViewById(R.id.forgotpasswrod);

        Paper.init(getApplicationContext());

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String key;
        key=auth.getUid().toString();

       Log.d("dax",key);



       signin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String ugmail, upass;
               ugmail = email.getText().toString();
               upass = password.getText().toString();

               Paper.book().write(database.gmail,ugmail);
               Paper.book().write(database.pass,upass);
               auth.signInWithEmailAndPassword(ugmail, upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {

                       if (task.isSuccessful()) {
                           Intent intent = new Intent(getApplicationContext(), Profile.class);
                           startActivity(intent);
                       } else {
                           Toast.makeText(LogIn.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                       }
                   }
               });
           }
       });
    }
}