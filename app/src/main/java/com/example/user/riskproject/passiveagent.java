package com.example.user.riskproject;

import android.widget.TextView;

import java.util.ArrayList;

public class passiveagent {
static boolean enable;
    public TextView draft(player p){
        TextView[] armies=new TextView[p.getMyterritories().size()];
        for(int i=0;i<armies.length;i++){
            armies[i]=p.getMyterritories().get(i).getMe();
        }

        int min=1000;
        for(int i=0;i<armies.length;i++){
            if(min>Integer.parseInt(armies[i].getText().toString())){
                min=Integer.parseInt(armies[i].getText().toString());
            }
        }
TextView result = null;
        for(int i=0;i<armies.length;i++){
            if(min==Integer.parseInt(armies[i].getText().toString())){
                result=armies[i];
            }
        }
        return result;
    }

}
