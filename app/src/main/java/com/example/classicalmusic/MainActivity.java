package com.example.classicalmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

CalendarView googlebut,facebookbtn;
    TextView signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      signin=findViewById(R.id.signin);
//        googlebut=findViewById(R.id.googlrbtn);
//        facebookbtn=findViewById(R.id.facebookbtn);
      ImageView mailbtn=findViewById(R.id.mailbtn);






              signin.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {

                      Intent intent = new Intent(getApplicationContext(), LogIn.class);
                      startActivity(intent);finish();
                  }
              });

              mailbtn.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Intent intent=new Intent(getApplicationContext(),SinUP.class);
                      startActivity(intent);
                      finish();
                  }
              });





    }
}