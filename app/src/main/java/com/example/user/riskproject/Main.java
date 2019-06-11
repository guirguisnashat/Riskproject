package com.example.user.riskproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Main extends AppCompatActivity {

String l="";
    state h;
    boolean simulation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }
    public ArrayList<state> read(){
        ArrayList<state> states=new ArrayList<state>();
        String f="";
        try {

            InputStream is=getAssets().open("states.txt");
            int size=is.available();
            byte[] Buffer=new byte[size];
            is.read(Buffer);
            is.close();
            f=new String(Buffer);

            String[] g=f.split("\n");

            for(int i=0;i<g.length;i=i+3 ){
                h=new state(g[i],Double.parseDouble(g[i+1]),Double.parseDouble(g[i+2]));
                states.add(h);
            }

        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }


        return states;
    }


    public ArrayList<distance> getdistances(ArrayList<state> states){
        ArrayList<distance> distances=new ArrayList<distance>();
        Coordinate latx ;
        Coordinate lngx ;
        Point pointx ;
        Coordinate laty ;
        Coordinate lngy ;
        Point pointy ;
        double distance;
        distance distance1=null;

for(int i=0;i<states.size();i++){
    for(int j=0;j<states.size();j++){
        latx=Coordinate.fromDegrees(states.get(i).getLatitude());
        lngx=Coordinate.fromDegrees(states.get(i).getLongitude());
        laty=Coordinate.fromDegrees(states.get(j).getLatitude());
        lngy=Coordinate.fromDegrees(states.get(j).getLongitude());
        pointx=Point.at(latx,lngx);
        pointy=Point.at(laty,lngy);
        distance = EarthCalc.gcdDistance(pointx, pointy); //in meters
        distance1=new distance(states.get(i).getName(),states.get(j).getName(),distance);
        distances.add(distance1);
    }
}

return distances;

    }
    public void simmode(View view) {

        simulation=true;
        Intent i=new Intent(this,choosingplayers.class);
        i.putExtra("sim",simulation);
        startActivity(i);
    }

    public void playmode(View view) {
        Intent i=new Intent(this,choosingplayers.class);
        startActivity(i);
    }

    public void exit(View view) {
    }
}
