package com.example.classicalmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;


public class SinUP extends AppCompatActivity {

    MaterialButton signup,calcel;
    EditText firstname,lastname,email,password,conpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sin_up);


        Paper.init(getApplicationContext());
        FirebaseAuth auth= FirebaseAuth.getInstance();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();

        signup=findViewById(R.id.creataccont);
        calcel=findViewById(R.id.cancel_button);
        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        conpassword=findViewById(R.id.conpassword);


        calcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Fname,Lname,mail,pass,conpass;
                Fname=firstname.getText().toString();
                Lname=lastname.getText().toString();
                mail=email.getText().toString();
                pass=password.getText().toString();
                conpass=conpassword.getText().toString();


                Paper.book().write(database.Fname,Fname);
                Paper.book().write(database.Lname,Lname);
                Paper.book().write(database.gmail,mail);
                Paper.book().write(database.pass,pass);

               if(!Fname.isEmpty()&&!Lname.isEmpty()&& !mail.isEmpty()&& !pass.isEmpty())
               {
                   if (pass.equals(conpass))
                   {

                       try {

                           auth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {

                               if (task.isSuccessful())
                               {
                                   reference.child("User").child(auth.getUid()).child("Name").setValue(Fname+Lname);
                                   reference.child("User").child(auth.getUid()).child("Gmail").setValue(mail);
                                   reference.child("User").child(auth.getUid()).child("Pass").setValue(pass);

                                   Intent intent =new Intent(getApplicationContext(),Profile.class);
                                   startActivity(intent);
                                   finishAffinity();
                               }
                               else {
                                   Toast.makeText(SinUP.this, "Not Creat Account", Toast.LENGTH_SHORT).show();
                               }
                               }
                           });
                           Log.d("dax", mail + pass);
                       }
                       catch(Exception e){
                           Log.d("dax", e.getMessage().toString());
                       }
                   }
                   else {
                       conpassword.setError("Conform Password is not Match");
                   }
               }
               else {
                   if(Fname.isEmpty())
                   {
                       firstname.setError("is Empty");
                   }
                   if (Lname.isEmpty())
                   {
                       lastname.setError("is Empty");
                   }
                   if (mail.isEmpty()){
                       email.setError("is Empty");
                   }
                   else
                   {
                    password.setError("is Empty");
                   }
               }

            }
        });

    }
}