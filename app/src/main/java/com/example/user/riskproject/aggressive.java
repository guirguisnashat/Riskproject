package com.example.user.riskproject;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

public class aggressive {
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

    public node[] attack(player p1, player p2){
        node[] armies=new node[p2.getMyterritories().size()];
        for(int i=0;i<p2.getMyterritories().size();i++){

               armies[i]=p2.getMyterritories().get(i);

        }
      /*  while(!p2.getMyterritories().isEmpty()){

            armies[o]=p2.getMyterritories().get(o);
              o++;
        }*/

        node[] neighbors=new node[p1.getMyterritories().size()];
         for(int i=0;i<p1.getMyterritories().size();i++){

               neighbors[i]=p1.getMyterritories().get(i);

        }

node temp=null;
        for(int i=0;i<armies.length;i++){
            for(int j=i;j<armies.length;j++){
                if(Integer.parseInt(armies[i].getMe().getText().toString())<Integer.parseInt(armies[j].getMe().getText().toString())){
                    temp=armies[j];
                   armies[j]=armies[i];
                   armies[i]=temp;
                }
            }
        }

        node result=null;
        node attacker=null;
       for(int i=0;i<armies.length;i++){
           for(int j=0;j<neighbors.length;j++){

               if(neighbors[j].getNodestoattack().contains(armies[i].getId())){

                 /*  if(neighbors[j].getMe().getText().toString().equalsIgnoreCase("1")){


                   }else{
                       result=neighbors[j];
                       attacker=armies[i];
                       break;
                   }*/
                   if(armies[i].getMe().getText().toString().equalsIgnoreCase("1")){

                   }else {
                       result=neighbors[j];
                       attacker = armies[i];
                       break;
                   }
               }

           }
           if(result!=null)
           break;
       }
       node[] ii=new node[2];
       ii[1]=result;
       ii[0]=attacker;


        return ii;
    }

}
