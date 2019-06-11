package com.example.user.riskproject;

import android.util.Log;
import android.widget.TextView;

public class pacifict {
    int min=1000;
    static boolean enable;
    public TextView draft(player p){
        TextView[] armies=new TextView[p.getMyterritories().size()];
        for(int i=0;i<armies.length;i++){
            armies[i]=p.getMyterritories().get(i).getMe();
        }

        min=1000;
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

    public TextView[] attack(player p, player mine){
        node[] armies=new node[p.getMyterritories().size()];
        for(int i=0;i<armies.length;i++){
            armies[i]=p.getMyterritories().get(i);
        }

        min=1000;
        for(int i=0;i<armies.length;i++){
            if(min>Integer.parseInt(armies[i].getMe().getText().toString())){
                min=Integer.parseInt(armies[i].getMe().getText().toString());
            }
        }

        Log.e("enemy",Integer.toString(armies.length));
        node result = null;
        for(int i=0;i<armies.length;i++){

            if(min==Integer.parseInt(armies[i].getMe().getText().toString())){
                result=armies[i];
            }
        }
       node[] neighbors=new node[mine.getMyterritories().size()];
        for(int i=0;i<neighbors.length;i++){

            neighbors[i]=mine.getMyterritories().get(i);

        }
        Log.e("mine",Integer.toString(neighbors.length));
        Log.e("result",result.getMe().getText().toString());
        TextView attacker=null;
        for(int i=0;i<neighbors.length;i++){

            if(neighbors[i].getNodestoattack().contains(result.getId())){
                  Log.e("done","done");
                if(neighbors[i].getMe().getText().toString().equalsIgnoreCase("1")){

                }else{
                    attacker=neighbors[i].getMe();
                }

            }
        }

        TextView[] ii=new TextView[2];
        ii[0]=attacker;
        ii[1]=result.getMe();

        return ii;
    }
}
