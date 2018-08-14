package com.example.abdolhameed.ppustudentsoccupation;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GameMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener ls;
    private Marker myPos;
    private MarkerOptions myPosOptions;
    private float lat=0;
    private float lng=0;
    private ArrayList<pnt> pnts=new ArrayList<pnt>();
    private float squareLength=0.0004f;

    private int myTeam;

    private final int team1Color=Color.BLUE;
    private final int team2Color=Color.RED;


    private FloatingActionButton mapOccupy;
    private RelativeLayout FloatingActionButtonsLayout;

    private void setMyTeam(int teamid){
        myTeam=teamid;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_map);

        String tno=getSharedPreferences("rondApplication",MODE_PRIVATE).getString("teamno","1");
        if(tno==null)
            tno="1";

        tno="1";
        int myTeamNo=Integer.parseInt(tno);
        setMyTeam(myTeamNo);
        new CountDownTimer(1000*60*5, 1000) {

            public void onTick(long millisUntilFinished) {
                if((millisUntilFinished/1000)%10==0){
                    HashMap<String ,String> t =new HashMap<String ,String>();

                    t.put("action", "other_team_regions");

                    ServerHandler sh=new ServerHandler(GameMap.this, t);

                    if(sh.getData("status").equals("false")){
                        Toast.makeText(GameMap.this,"Server Error occurred:/",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String coor=sh.getData("coor");
                    try {
                        JSONArray coorArr=new JSONArray(coor);
                        for (int i = 0; i < coorArr.length(); ++i) {
                            JSONObject oneCoor = coorArr.getJSONObject(i);
                            float xs = Float.parseFloat(oneCoor.getString("xs"));
                            float xn = Float.parseFloat(oneCoor.getString("xn"));
                            float ys = Float.parseFloat(oneCoor.getString("ys"));
                            float yn = Float.parseFloat(oneCoor.getString("yn"));
                            int team = Integer.parseInt(oneCoor.getString("teamid"));
                            pnt current=new pnt(xs,ys,xn,yn);
                            boolean doesIntersect=false;
                            for (int j = 0; j < pnts.size(); j++) {
                                pnt ptemp=pnts.get(j);
                                if(current.getXs()<ptemp.getXn() && current.getXn()>ptemp.getXs()
                                && current.getYs()<ptemp.getYn() &&current.getYn() >ptemp.getYs()) {
                                    doesIntersect = true;
                                    break;
                                }
                            }
                            if(doesIntersect) {
                                continue;
                            }
                            Toast.makeText(GameMap.this,xs+","+ys+"\n"+xn+","+yn,Toast.LENGTH_LONG).show();
                            pnts.add(current);

                            Polygon polygon = mMap.addPolygon(new PolygonOptions()
                                    .add(new LatLng(xs, ys), new LatLng(xs, yn), new LatLng(xn , yn), new LatLng(xn, ys), new LatLng(xs, ys))
                                    .fillColor((team==1)?team1Color:team2Color));
                        }
                    } catch (JSONException e) {
                        Log.d("ggggggggggggg", e.getMessage());
                        Toast.makeText(GameMap.this, "Some features may not be working!", Toast.LENGTH_LONG).show();
                    }
                }
            }

            public void onFinish() {
                Toast.makeText(GameMap.this, "Game Has Finished", Toast.LENGTH_LONG).show();
                HashMap<String ,String> t =new HashMap<String ,String>();

                t.put("action", "who_win");

                ServerHandler sh=new ServerHandler(GameMap.this, t);
                if(sh.getData("status").equals("false")){
                    Toast.makeText(GameMap.this,"Server Error #2 Has Occured, Sorry for that!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(sh.getData("status").equals("1"))
                    Toast.makeText(GameMap.this, "Team 1 Wins", Toast.LENGTH_LONG);
                else if(sh.getData("status").equals("-1"))
                    Toast.makeText(GameMap.this, "Team 2 Wins", Toast.LENGTH_LONG);
                else
                    Toast.makeText(GameMap.this, "There's a Tie :/", Toast.LENGTH_LONG);
            }
        }.start();

        FloatingActionButtonsLayout=(RelativeLayout)findViewById(R.id.FloatingActionButtonsLayout);
        FloatingActionButtonsLayout.bringToFront();

        mapOccupy=(FloatingActionButton)findViewById(R.id.mapOccupy);
        mapOccupy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pnt current=new pnt(lat, lng,lat+squareLength, lng+squareLength);

                boolean doesIntersect=false;
                for(int i=0;i<pnts.size();i++){
                    pnt ptemp=pnts.get(i);
                    if(current.getXs()<ptemp.getXn() && current.getXn()>ptemp.getXs()
                    && current.getYs()<ptemp.getYn() &&current.getYn() >ptemp.getYs()){
                        doesIntersect=true;
                        break;
                    }
                }

                if(doesIntersect){
                    Toast.makeText(GameMap.this, "You can't build here", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String ,String> t =new HashMap<String ,String>();
                t.put("action", "occupy_square");
                t.put("teamid", myTeam+"");
                t.put("xs", current.getXs() + "");
                t.put("ys", current.getYs() + "");
                t.put("xn", current.getXn() + "");
                t.put("yn", current.getYn() + "");

                //Toast.makeText(GameMap.this,"Lat\n"+current.getXs()+"\nLng\n"+current.getYs(), Toast.LENGTH_LONG).show();

                ServerHandler sh=new ServerHandler(GameMap.this, t);
                if(sh.getData("status").equals("false")){
                    Toast.makeText(GameMap.this,"Server Error #3 Has Occured, Sorry for that!",Toast.LENGTH_SHORT).show();
                    return;
                }

                pnts.add(new pnt(lat, lng, lat+squareLength, lng+squareLength));

                Polygon polygon = mMap.addPolygon(new PolygonOptions()
                        .add(new LatLng(lat, lng), new LatLng(lat, lng + squareLength), new LatLng(lat + squareLength, lng + squareLength), new LatLng(lat + squareLength, lng), new LatLng(lat, lng))
                        .fillColor((myTeam==1)?team1Color:team2Color));

            }
        });
        initLocationFn();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        initLocationFn();


    }


    private void initLocationFn(){
        locationManager = (LocationManager)
        getSystemService(Context.LOCATION_SERVICE);
        ls = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                if(myPosOptions!=null)
                    myPos.remove();

                lat=(float)location.getLatitude();
                lng=(float)location.getLongitude();

                myPosOptions =new MarkerOptions().position(latLng).title("Marker in Sydney");
                myPos = mMap.addMarker(myPosOptions);
                // Showing the current location in Google Map
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ls);
    }
}
