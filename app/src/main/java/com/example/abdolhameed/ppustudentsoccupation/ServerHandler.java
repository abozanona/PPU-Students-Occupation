package com.example.abdolhameed.ppustudentsoccupation;

import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by abdolhameed on 17/03/16.
 */
public class ServerHandler {
    private JSONObject object;
    private String link;
    private String Sjson;
    public ServerHandler(Context context, HashMap<String ,String> HMdata)  {

        String HTTPAnds ="";
        Set set = HMdata.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            HTTPAnds+=mentry.getKey() + "=" + mentry.getValue() + "&";
        }



        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll()
        .penaltyLog()
        .build());

        JSONObject json = null;
        Sjson = "";
        HttpResponse response;
        HttpClient myClient = new DefaultHttpClient();
        //////////////////////////////////////////////////////
        String ip_ad = "http://askrond.com/test.php";
        //Intent intent = (Activity)context.getIntent();

        //String id = intent.getStringExtra("user_id").toString();
        link=ip_ad + "?" + HTTPAnds;
        HttpPost myConnection = new HttpPost(link);
    //////////////////////////////////////////////////////or url on Internet

    // SetBackground("@drawable/"+ json.getStrig("pic_url"));

        try {
            response = myClient.execute(myConnection);
            Sjson = EntityUtils.toString(response.getEntity(), "UTF-8");

        } catch (ClientProtocolException e) {
    // e.printStackTrace();
            Toast.makeText(context, "Can't access the game's server, Make sure that WI-FI is on.", Toast.LENGTH_LONG).show();
            //Toast.makeText(context, "1."+e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
    // e.printStackTrace();
            Toast.makeText(context, "Can't access the game's server, Make sure that WI-FI is on.", Toast.LENGTH_LONG).show();
            //Toast.makeText(context, "2."+e.getMessage(), Toast.LENGTH_LONG).show();
        }



        //JSONObject object;

        try{
            object = new JSONObject(Sjson);
            if(this.getData("status").equals("false"))
                if(this.getData("error").equals("WrongParams"))
                    Toast.makeText(context,"Unexpected Error occured.\nError code# WrongParams", Toast.LENGTH_LONG).show();
        } catch ( JSONException e) {
            // e.printStackTrace();
            try {
                object = new JSONObject("{\"status\":\"false\", \"error\":\"errorincode\"}");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Toast.makeText(context,"Unexpected Error occured, Sorry for that.", Toast.LENGTH_LONG).show();
        }

    }
    public String getData(String Sdata) {
        try {

            return object.getString(Sdata);
        } catch (JSONException e) {
            e.printStackTrace();
            return "ERROR!";
        }
    }
    public String getLink(){
        return link;
    }
    public String getJson(){
        return Sjson;
    }
}
