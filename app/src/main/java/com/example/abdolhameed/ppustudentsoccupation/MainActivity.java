package com.example.abdolhameed.ppustudentsoccupation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button login;
    Button signup;
    Button test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String isMeHere=(getSharedPreferences("rondApplication",MODE_PRIVATE).getString("username","0"));
        if(!isMeHere.equals("0") && !isMeHere.equals("")){
            Intent gotoMap =new Intent(MainActivity.this, Map.class);
            startActivity(gotoMap);
            return ;
        }



        login=(Button)findViewById(R.id.login);
        signup=(Button)findViewById(R.id.signup);
        test=(Button)findViewById(R.id.test);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoLogin = new Intent(MainActivity.this, Login.class);
                startActivity(gotoLogin);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoLogin = new Intent(MainActivity.this, Signup.class);
                startActivity(gotoLogin);
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Map.class);
                startActivity(intent);
            }
        });

    }
}
