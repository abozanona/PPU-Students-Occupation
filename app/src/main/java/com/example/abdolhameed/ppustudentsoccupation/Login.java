package com.example.abdolhameed.ppustudentsoccupation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    EditText userName;
    EditText passWord;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName=(EditText)findViewById(R.id.userName);
        passWord=(EditText)findViewById(R.id.passWord);
        login=(Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userName.getText().toString();
                String password = passWord.getText().toString();

                HashMap<String ,String> data=new HashMap<String ,String>();
                data.put("action", "login");
                data.put("username", username.trim());
                data.put("password", password.trim());

                ServerHandler loginjson;
                    loginjson= new ServerHandler(Login.this, data);
                if(loginjson.getData("status").equals("false")){
                    if(loginjson.getData("error").equals("noUserFound"))
                        Toast.makeText(Login.this,"The compination of username, password does not exist", Toast.LENGTH_LONG);
                    return;
                }

                //Login successfully
                SharedPreferences sp =getSharedPreferences("rondApplication",MODE_PRIVATE);
                SharedPreferences.Editor editor =sp.edit();
                editor.putString("username", username);
                editor.putString("realname", loginjson.getData("realname"));
                editor.putString("password", password);
                editor.putString("pkey", loginjson.getData("pkey"));
                editor.putString("teamno", loginjson.getData("teamno"));
                editor.commit();

                Log.d("username", username);
                Log.d("realname", loginjson.getData("realname"));
                Log.d("password", password);
                Log.d("pkey", loginjson.getData("pkey"));
                Log.d("teamno", loginjson.getData("teamno"));

                Intent gotoMap =new Intent(Login.this, Map.class);
                startActivity(gotoMap);
            }
        });

    }

}
