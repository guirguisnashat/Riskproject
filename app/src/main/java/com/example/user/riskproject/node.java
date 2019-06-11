package com.example.user.riskproject;

import android.graphics.Color;
import android.widget.TextView;

import java.util.ArrayList;

public class node {
    private String id;
    private TextView me;
    private ArrayList<String> nodestoattack=new ArrayList<String>();
    private int player=Color.BLACK;



    public int getPlayer()
    {
        return player;
    }

    public void setPlayer(int player) {

        this.player = player;
        this.me.setBackgroundColor(player);

    }

    public node(String id, TextView me) {
        this.id = id;
        this.me = me;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TextView getMe() {
        return me;
    }

    public void setMe(TextView me) {
        this.me = me;
    }

    public ArrayList<String> getNodestoattack() {
        return nodestoattack;
    }

    public void setNodestoattack(ArrayList<String> nodestoattack) {
        this.nodestoattack = nodestoattack;
    }
    public String getfromNodestoattack(int i) {
        return nodestoattack.get(i);
    }

    public void addtoNodestoattack(String node) {
        this.nodestoattack.add(node);
    }
}
