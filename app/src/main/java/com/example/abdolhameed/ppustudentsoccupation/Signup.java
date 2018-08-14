package com.example.abdolhameed.ppustudentsoccupation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class Signup extends AppCompatActivity {
    EditText userName;
    EditText realName;
    EditText passWord;
    CheckBox doIAgree;
    Button teamNo;
    Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        userName=(EditText)findViewById(R.id.username);
        realName=(EditText)findViewById(R.id.realname);
        passWord=(EditText)findViewById(R.id.password);
        doIAgree=(CheckBox)findViewById(R.id.doiagree);
        signUp=(Button)findViewById(R.id.signup);
        teamNo=(Button)findViewById(R.id.teamno);
        teamNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teamNo.getText().toString().equals("Team#1"))
                    teamNo.setText("Team#2");
                else
                    teamNo.setText("Team#1");
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!doIAgree.isChecked()){
                    Toast.makeText(Signup.this,"You must agree on all the game's term", Toast.LENGTH_LONG).show();
                    return;
                }
                String username=userName.getText().toString();
                String realname=realName.getText().toString();
                String password=passWord.getText().toString();
                int teamno=(teamNo.getText().toString().equals("Team#1"))?1:2;

                HashMap<String ,String> data=new HashMap<String ,String>();
                data.put("action", "insert_user");
                data.put("username", username);
                data.put("realname", realname);
                data.put("password", password);
                data.put("teamno", teamno+"");

                ServerHandler signupjson;
                signupjson= new ServerHandler(Signup.this, data);

                if(signupjson.getData("status").equals("false")){
                    if(signupjson.getData("error").equals("ErrorInSearchingForUser"))
                        Toast.makeText(Signup.this,"An error has happend in the server", Toast.LENGTH_LONG);
                    else if(signupjson.getData("error").equals("AlreadyExistingUser"))
                        Toast.makeText(Signup.this,"There's already a user with the same username, please try another one", Toast.LENGTH_LONG);
                    else if(signupjson.getData("error").equals("ErrorInInsertion"))
                        Toast.makeText(Signup.this,"An error has happend in the server", Toast.LENGTH_LONG);
                    else
                        Toast.makeText(Signup.this,"An error occured.", Toast.LENGTH_LONG);
                    return;
                }

                //Login successfully
                SharedPreferences sp =getSharedPreferences("rondApplication",0);
                SharedPreferences.Editor editor =sp.edit();
                editor.putString("username", username);
                editor.putString("password", password);
                editor.putString("realname", signupjson.getData("realname"));
                editor.putString("pkey", signupjson.getData("pkey"));
                editor.putString("teamno", teamno+"");
                editor.commit();

                Toast.makeText(Signup.this,"You've registered succesfully", Toast.LENGTH_LONG).show();
                Intent gotoGameMap =new Intent(Signup.this, GameMap.class);
                startActivity(gotoGameMap);
            }
        });
    }
}
