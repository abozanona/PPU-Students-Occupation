package com.example.abdolhameed.ppustudentsoccupation;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by abdolhameed on 22/03/16.
 */
public class Task extends AsyncTask<String, Integer, String> {

    public Task(Context context, MainActivity activity){
        //بنسوي ميثود في الأكتفتي وبنسوي ست للعماصر جواها, وطبعا في الست بنسوي اشي معين
    }
    @Override
    protected void onPostExecute(String s) {//s is json{you must parse it}

        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... params) {
        InputStream inputStream=null;
        HttpURLConnection httpUrlConnection=null;
        ByteArrayOutputStream content=null;

        try{
            URL url=new URL(params[0]);
            httpUrlConnection=(HttpURLConnection)url.openConnection();
            if(httpUrlConnection.getResponseCode()!=HttpURLConnection.HTTP_OK)
                Log.d("Connection error", httpUrlConnection.getResponseMessage());

            inputStream=httpUrlConnection.getInputStream();
            byte[] buffer=new byte[4096];
            int count=0;
            content=new ByteArrayOutputStream();
            while((count=inputStream.read(buffer))!=-1){
                content.write(buffer,0,count);
            }



        }catch(IOException ex){
            Log.d("Error",ex.getMessage());
            return "ERROR!";
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            httpUrlConnection.disconnect();
        }
        return new String(content.toByteArray());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}

class Test{

}