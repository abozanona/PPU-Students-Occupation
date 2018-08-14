package com.example.abdolhameed.ppustudentsoccupation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by abdolhameed on 16/03/16.
 */
public class AppCompatActivity_withmenu extends AppCompatActivity {
    private String className;
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        Intent gotoSomeWhere;
        switch(id){
            case R.id.gotomap:
                if(this.className=="Map")
                    return true;
                gotoSomeWhere=new Intent(this,Map.class);
                startActivity(gotoSomeWhere);
                return true;


            case R.id.gotologout:
                SharedPreferences sp =getSharedPreferences("rondApplication",0);
                SharedPreferences.Editor editor =sp.edit();
                editor.putString("username", "");
                editor.putString("realname", "");
                editor.putString("password", "");
                editor.putString("pkey", "");
                editor.putString("teamno", "");
                editor.commit();

                Intent gotoTheBegining=new Intent(this, MainActivity.class);
                startActivity(gotoTheBegining);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void setClassName(String name){
        this.className=name;
    }
}
