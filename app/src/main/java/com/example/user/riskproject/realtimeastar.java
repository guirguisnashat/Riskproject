package com.example.user.riskproject;

import android.graphics.Color;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class realtimeastar {
    static boolean enable;
    public TextView draft(player p){
        TextView[] armies=new TextView[p.getMyterritories().size()];
        for(int i=0;i<armies.length;i++){
            armies[i]=p.getMyterritories().get(i).getMe();
        }

        int max=0;
        for(int i=0;i<armies.length;i++){
            if(max<Integer.parseInt(armies[i].getText().toString())){
                max=Integer.parseInt(armies[i].getText().toString());
            }
        }
        TextView result = null;
        for(int i=0;i<armies.length;i++){
            if(max==Integer.parseInt(armies[i].getText().toString())){
                result=armies[i];
            }
        }
        return result;
    }

    public node[] attack(player p1, player p2, ArrayList<distance> distances){


        node[] armies=new node[p1.getMyterritories().size()];

        for(int i=0;i<armies.length;i++){
            armies[i]=p1.getMyterritories().get(i);
        }

        int max=10000000;
        for(int i=0;i<armies.length;i++){
            if(max>Integer.parseInt(armies[i].getMe().getText().toString())){
                max=Integer.parseInt(armies[i].getMe().getText().toString());
            }
        }

//        Log.e("guirgus",Integer.toString(max));
        node result = null;
        for(int i=0;i<armies.length;i++){
            if(max==Integer.parseInt(armies[i].getMe().getText().toString())){
                result=armies[i];
            }
        }

        node[] neighbors=new node[p2.getMyterritories().size()];
        for(int i=0;i<neighbors.length;i++){
            neighbors[i]=p2.getMyterritories().get(i);
        }

        // Log.e("guirguis",result.getId());

        ArrayList<distance> dist=new ArrayList<distance>();
        ArrayList<Double> onlydist=new ArrayList<Double>();





        for(int i=0;i<distances.size();i++){
            // Log.e("guirguis",distances.get(i).getFrom()+" "+result.getId());
            if(distances.get(i).getFrom().equalsIgnoreCase(result.getId())){
                //  Log.e("guirguis","done");
                dist.add(distances.get(i));
                onlydist.add(distances.get(i).getDistance());
            }
        }






        Collections.sort(onlydist);
        node attacker =null;
        // Log.e("guirguis",Integer.toString(onlydist.size()));
        for(int i=0;i<onlydist.size();i++){
            // Log.e("guirguis","done");
            for(int j=0;j<dist.size();j++){
                //   Log.e("guirguis","done");
                if(onlydist.get(i)==dist.get(j).getDistance()){
                    //     Log.e("guirguis","done");
                    for (int k=0;k<neighbors.length;k++){
                        if(dist.get(j).getTo().equalsIgnoreCase(neighbors[k].getId())){
                            if(MainActivity.map.get(dist.get(j).getTo()).getMe().getText().toString().equalsIgnoreCase("1")){

                            }else {
                                attacker = neighbors[k];
                                break;
                            }}
                    }
                    if(attacker!=null){
                        break;
                    }
                }
                if(attacker!=null){
                    break;
                }
            }
        }
        // node result=null;
        // node attacker=null;


        node[] back=new node[2];
        //  Log.e("guirguis",result.getId()+"  "+attacker.getId());
        back[0]=result;
        back[1]=attacker;
        return back;
    }

    public node[] A_star(node[] nodes,ArrayList<distance> distances){
        node start=nodes[1];
        node target=nodes[0];
        node attacker=null;
        node defender=null;
        node[] nodes1=new node[3];

//Log.e("guirguis",start.getId()+"       "+target.getId());
        if(start.getNodestoattack().contains(target.getId())){
            attacker=start;
            defender=target;
            nodes1[1]=attacker;
            nodes[0]=defender;
            return nodes;
        }




        for(int i=0;i<start.getNodestoattack().size();i++){
            if(MainActivity.map.get(start.getfromNodestoattack(i)).getPlayer()== Color.RED
                    &&
                    Integer.parseInt(MainActivity.map.get(start.getfromNodestoattack(i)).getMe().getText().toString())==Integer.parseInt(target.getMe().getText().toString())){
                attacker=start;
                defender=MainActivity.map.get(attacker.getfromNodestoattack(i));
                nodes1[1]=attacker;
                nodes1[0]=defender;
                return nodes1;
            }

        }

        HashMap<Double,node> neighborsheuristic=new HashMap<Double, node>();
        node choose=null;
        distance dist;
        for(int i=0;i<start.getNodestoattack().size();i++){
            for(int j=0;j<distances.size();j++){
                choose=MainActivity.map.get(start.getfromNodestoattack(i));

                //    Log.e("guirguis", String.valueOf(distances.get(j).getFrom().equalsIgnoreCase(choose.getId())));

                if(distances.get(j).getTo().equalsIgnoreCase(target.getId()) && distances.get(j).getFrom().equalsIgnoreCase(choose.getId()) && choose.getPlayer()!=Color.BLUE){
                    neighborsheuristic.put(distances.get(j).getDistance()+ distances.get(j).getdistancefromstrings(distances.get(j).getFrom(),start.getId(),distances),choose);
                }
            }
        }
        //  Log.e("guirguis",start.getId()+"       "+target.getId());
        Object[] keys=neighborsheuristic.keySet().toArray();

        double min=1000000000000000000.0;


        for(int i=0;i<neighborsheuristic.size();i++){
            if(min > (Double) keys[i]){
                min= (Double) keys[i];
            }

        }

//node j=null;
//node r=null;
        //node[] nodes1=new node[3];
        try {
            nodes1[1] = start;
        }catch (Exception c){
            // Log.e("guirguis",c.getMessage());
        }
        nodes1[0]=neighborsheuristic.get(min);
        //  nodes1[1]=j;
        // nodes1[0]=r;
        return nodes1;
    }
}
