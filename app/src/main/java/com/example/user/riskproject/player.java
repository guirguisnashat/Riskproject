package com.example.user.riskproject;

import java.util.ArrayList;
import java.util.HashMap;

public class player {
    private int color;
   private ArrayList<node> myterritories=new ArrayList<node>();
    private int draftvalue=20;

    public player(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public ArrayList<node> getMyterritories() {
        return myterritories;
    }

    public void setMyterritories(node node) {
        this.myterritories.add(node);
    }

    public int getDraftvalue() {
        return draftvalue;
    }

    public void setDraftvalue(int draftvalue) {
        this.draftvalue = draftvalue;
    }

    public void calculatederaftvalue(){
        int count=myterritories.size()/3;
        if(count<3){
            draftvalue=3;
        }else{
            draftvalue=count;
        }
    }
}
