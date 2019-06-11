package com.example.user.riskproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Egypt extends AppCompatActivity implements View.OnClickListener {
    final int PLAYER_ONE_COLOR=Color.RED;
    final int PLAYER_TWO_COLOR=Color.BLUE;
    state h=null;
    boolean firsttime=true;
    final int DRAFT=0,ATTACK=1,FORTIFY=2;
    int mode=1;
    Context c=Egypt.this;
    static int prog;
    static int attackers,defenders;
    static String winner="";
    pacifict pacifict=new pacifict();
    greedyagent greedyagent=new greedyagent();
    A_star a_star=new A_star();
    ArrayList<distance> distancesamerica=new ArrayList<distance>();
    public int getAttackers() {
        return attackers;
    }

    public void setAttackers(int attackers) {
        this.attackers = attackers;
    }

    int draftvalue;
    passiveagent pa = new passiveagent();
    aggressive aggressive=new aggressive();

    player player1=new player(PLAYER_ONE_COLOR);
    player player2=new player(PLAYER_TWO_COLOR);
    int play= Color.RED;
    node attacker=null,defender=null;

    boolean myturn=true;
    static HashMap<String,node> map=new HashMap<String,node>();
    node[] nodes=new node[40];
    Boolean[] booleans=new Boolean[40];
    boolean attack=true;
    int pointer=0;
    private void setDefender(node defender){
        this.defender=defender;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egypt);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
        preparenodes();
      draft=findViewById(R.id.drafteg);
          draft.setText(Integer.toString(draftvalue));
        draft.setBackgroundColor(play);
        if(mode==ATTACK){
            draft.setVisibility(View.INVISIBLE);
        }

        random();
       selectplayer();
        random();
        selectplayer();

        distancesamerica=getdistances(read());

    }

    ImageView image;
    TextView wadigedid;
    TextView elmenia;
    TextView elfayoum;
    TextView giza;
    TextView matrouh;
    TextView aswan;
    TextView qena;
    TextView sohag;
    TextView assiut;
    TextView baniswef;
    TextView cairo;
    TextView suez;
    TextView delta;
    TextView behera;
    TextView alex;
    TextView redsea;
    TextView northsinai;
    TextView southsinai;
    TextView smaelia;
    TextView draft;

    public void A_stardraft(){
        TextView k=null;
        if (play == PLAYER_TWO_COLOR){
            k= a_star.draft(player2);
            int j=Integer.parseInt(k.getText().toString());
            k.setText(Integer.toString(j+calcdraftvalue()));
            selectplayer();
            draft.setBackgroundColor(play);
            changemode();
        }

    }
    public void A_starattack(){
        player d=player1;
        player f=player2;
        //node[] h=greedyagent.attack(d,f,distancesamerica);
        //Toast.makeText(this,player2.toString(),Toast.LENGTH_LONG).show();
        attacker=a_star.A_star(a_star.attack(d,f,distancesamerica),distancesamerica)[1];
        defender=a_star.A_star(a_star.attack(d,f,distancesamerica),distancesamerica)[0];
        Toast.makeText(this,attacker.getId()+"   "+defender.getId(),Toast.LENGTH_LONG).show();
        if(defender.getPlayer()==Color.BLACK){
          /*  for(int i=0;i<player2.getMyterritories().size();i++){
                for(int j=0;j<player2.getMyterritories().get(i).getNodestoattack().size();j++){
                    if(map.get(player2.getMyterritories().get(i).getNodestoattack().get(j)).getPlayer()!=play){


                        attacker = player2.getMyterritories().get(i);
                        break;


                    }
                }
                if(attacker!=null){
                    break;
                }
            }
            // attacker=player2.getMyterritories().get(0);
            for(int i=0;i<attacker.getNodestoattack().size();i++){
                if(map.get(attacker.getNodestoattack().get(i)).getPlayer()==Color.BLACK){

                    defender=map.get(attacker.getNodestoattack().get(i));
                    break;
                }
            }*/


            final customdialogue cd=new customdialogue(Egypt.this);
            customdialogue.max=Integer.parseInt(attacker.getMe().getText().toString())-1;
            cd.show();
            cd.ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    defender.setPlayer(play);
                    defender.getMe().setBackgroundColor(play);
                    defender.getMe().setText(Integer.toString(prog));
                    attacker.getMe().setText(Integer.toString(customdialogue.max+1-prog));
                    if(play==player1.getColor()){
                        player1.setMyterritories(defender);
                    }
                    if(play==player2.getColor()){
                        player2.setMyterritories(defender);
                    }
                    checkwin();
                    changemode();
                    selectplayer();
                    attacker=null;
                    defender=null;
                    cd.cancel();
                }
            });
        }else{
            makedialoge(attacker.getId(),defender.getId());
        }

    }
    public void greedydraft(){
        TextView k=null;
        if (play == PLAYER_TWO_COLOR){
            k= greedyagent.draft(player2);
            int j=Integer.parseInt(k.getText().toString());
            k.setText(Integer.toString(j+calcdraftvalue()));
            selectplayer();
            draft.setBackgroundColor(play);
            changemode();
        }

    }
    public void greedyattack(){
        player d=player1;
        player f=player2;
        //node[] h=greedyagent.attack(d,f,distancesamerica);
        //Toast.makeText(this,player2.toString(),Toast.LENGTH_LONG).show();
        attacker=greedyagent.greedy(greedyagent.attack(d,f,distancesamerica),distancesamerica)[1];
        defender=greedyagent.greedy(greedyagent.attack(d,f,distancesamerica),distancesamerica)[0];
        Toast.makeText(this,attacker.getId()+"   "+defender.getId(),Toast.LENGTH_LONG).show();
        if(defender.getPlayer()==Color.BLACK){
          /*  for(int i=0;i<player2.getMyterritories().size();i++){
                for(int j=0;j<player2.getMyterritories().get(i).getNodestoattack().size();j++){
                    if(map.get(player2.getMyterritories().get(i).getNodestoattack().get(j)).getPlayer()!=play){


                        attacker = player2.getMyterritories().get(i);
                        break;


                    }
                }
                if(attacker!=null){
                    break;
                }
            }
            // attacker=player2.getMyterritories().get(0);
            for(int i=0;i<attacker.getNodestoattack().size();i++){
                if(map.get(attacker.getNodestoattack().get(i)).getPlayer()==Color.BLACK){

                    defender=map.get(attacker.getNodestoattack().get(i));
                    break;
                }
            }*/


            final customdialogue cd=new customdialogue(Egypt.this);
            customdialogue.max=Integer.parseInt(attacker.getMe().getText().toString())-1;
            cd.show();
            cd.ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    defender.setPlayer(play);
                    defender.getMe().setBackgroundColor(play);
                    defender.getMe().setText(Integer.toString(prog));
                    attacker.getMe().setText(Integer.toString(customdialogue.max+1-prog));
                    if(play==player1.getColor()){
                        player1.setMyterritories(defender);
                    }
                    if(play==player2.getColor()){
                        player2.setMyterritories(defender);
                    }
                    checkwin();
                    changemode();
                    selectplayer();
                    attacker=null;
                    defender=null;
                    cd.cancel();
                }
            });
        }else{
            makedialoge(attacker.getId(),defender.getId());
        }

    }
    public void aggressiveattack(){

        attacker=aggressive.attack(player1,player2)[0];
        defender=aggressive.attack(player1,player2)[1];
        Toast.makeText(this,attacker+"   "+defender,Toast.LENGTH_LONG).show();
        if(attacker==null&&defender==null){
            for(int i=0;i<player2.getMyterritories().size();i++){
                for(int j=0;j<player2.getMyterritories().get(i).getNodestoattack().size();j++){
                    if(map.get(player2.getMyterritories().get(i).getNodestoattack().get(j)).getPlayer()!=play){


                        attacker = player2.getMyterritories().get(i);
                        break;


                    }
                }
                if(attacker!=null){
                    break;
                }
            }
            // attacker=player2.getMyterritories().get(0);
            for(int i=0;i<attacker.getNodestoattack().size();i++){
                if(map.get(attacker.getNodestoattack().get(i)).getPlayer()==Color.BLACK){

                    defender=map.get(attacker.getNodestoattack().get(i));
                    break;
                }
            }


            final customdialogue cd=new customdialogue(Egypt.this);
            customdialogue.max=Integer.parseInt(attacker.getMe().getText().toString())-1;
            cd.show();
            cd.ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    defender.setPlayer(play);
                    defender.getMe().setBackgroundColor(play);
                    defender.getMe().setText(Integer.toString(prog));
                    attacker.getMe().setText(Integer.toString(customdialogue.max+1-prog));
                    if(play==player1.getColor()){
                        player1.setMyterritories(defender);
                    }
                    if(play==player2.getColor()){
                        player2.setMyterritories(defender);
                    }
                    checkwin();
                    changemode();
                    selectplayer();
                    attacker=null;
                    defender=null;
                    cd.cancel();
                }
            });
        }else{
            makedialoge(attacker.getId(),defender.getId());
        }

    }
    public void pacifictdraft(){
        TextView k=null;
        if (play == PLAYER_TWO_COLOR){
            k= pacifict.draft(player2);
            int j=Integer.parseInt(k.getText().toString());
            k.setText(Integer.toString(j+calcdraftvalue()));
            selectplayer();
            draft.setBackgroundColor(play);
            changemode();
        }
    }
    public void pacifictattack(){

        TextView att=pacifict.attack(player1,player2)[0];

        TextView def=pacifict.attack(player1,player2)[1];
        Toast.makeText(this,att+"   "+def,Toast.LENGTH_LONG).show();
        if (att != null && def != null) {
            Toast.makeText(this,att+"   "+def,Toast.LENGTH_LONG).show();
            defender = new node("hk", def);
            attacker=new node("f",att);
        }

        Toast toast=new Toast(this);
        toast.setGravity(20,100,100);
        // toast.makeText(this,attacker+"          "+defender,Toast.LENGTH_LONG).show();

        //Toast.makeText(this,defender.toString(),Toast.LENGTH_LONG).show();
        if(attacker==null||defender==null){

            for(int i=0;i<player2.getMyterritories().size();i++){
                for(int j=0;j<player2.getMyterritories().get(i).getNodestoattack().size();j++){
                    if(map.get(player2.getMyterritories().get(i).getNodestoattack().get(j)).getPlayer()==Color.BLACK){
                        if(Integer.parseInt(map.get(player2.getMyterritories().get(i).getNodestoattack().get(j)).getMe().getText().toString())<1){
                            attacker = player2.getMyterritories().get(i);
                            break;
                        }
                    }
                }
                if(attacker!=null){
                    break;
                }
            }
            // attacker=player2.getMyterritories().get(0);
            for(int i=0;i<attacker.getNodestoattack().size();i++){
                if(map.get(attacker.getNodestoattack().get(i)).getPlayer()==Color.BLACK){

                    defender=map.get(attacker.getNodestoattack().get(i));
                    break;
                }
            }


            //Toast.makeText(this,"done",Toast.LENGTH_LONG).show();
            final customdialogue cd=new customdialogue(Egypt.this);
            customdialogue.max=Integer.parseInt(attacker.getMe().getText().toString())-1;
            cd.show();
            cd.ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    defender.setPlayer(play);
                    defender.getMe().setBackgroundColor(play);
                    defender.getMe().setText(Integer.toString(prog));
                    attacker.getMe().setText(Integer.toString(customdialogue.max+1-prog));
                    if(play==player1.getColor()){
                        player1.setMyterritories(defender);
                    }
                    if(play==player2.getColor()){
                        player2.setMyterritories(defender);
                    }
                    checkwin();
                    changemode();
                    selectplayer();
                    attacker=null;
                    defender=null;
                    cd.cancel();

                }
            });


        }else{
            defender.setPlayer(PLAYER_ONE_COLOR);
            attacker.setPlayer(play);
            makedialoge(attacker.getId(),defender.getId());
        }

    }
    public void aggressivedraft(){
        TextView k=null;
        if (play == PLAYER_TWO_COLOR){
            k= aggressive.draft(player2);
            int j=Integer.parseInt(k.getText().toString());
            k.setText(Integer.toString(j+calcdraftvalue()));
            selectplayer();
            draft.setBackgroundColor(play);
            changemode();
        }
    }
    public void passiveagentfunc (){
        TextView k=null;
        if (play == PLAYER_TWO_COLOR){
            k= pa.draft(player2);
            int j=Integer.parseInt(k.getText().toString());
            k.setText(Integer.toString(j+3));

            selectplayer();
            draft.setBackgroundColor(play);
            changemode();
        }

    }
    public void skip(){
        if(mode==ATTACK){
            if (play==PLAYER_ONE_COLOR){
                play=PLAYER_TWO_COLOR;
            }else{
                changemode();
                play=PLAYER_ONE_COLOR;
                draft.setBackgroundColor(play);
            }
        }
    }
    public void passiveagentattack (){
        skip();
    }
    public void init (){
      image=findViewById(R.id.egyptmap);
        image.setOnClickListener(this);


        wadigedid=findViewById(R.id.wadigedid);
        makenode("wadigedid",wadigedid);

        elmenia=findViewById(R.id.elmenia);
        makenode("elmenia",elmenia);

        elfayoum=findViewById(R.id.elfayoum);
        makenode("elfayoum",elfayoum);

        giza=findViewById(R.id.giza);
        makenode("giza",giza);

        matrouh=findViewById(R.id.matrouh);
        makenode("matrouh",matrouh);

        aswan=findViewById(R.id.aswan);
        makenode("aswan",aswan);

        qena=findViewById(R.id.qena);
        makenode("qena",qena);

        sohag=findViewById(R.id.sohag);
        makenode("sohag",sohag);

        assiut=findViewById(R.id.assiut);
        makenode("assiut",assiut);

        baniswef=findViewById(R.id.baniswef);
        makenode("baniswef",baniswef);

        cairo=findViewById(R.id.cairo);
        makenode("cairo",cairo);

        suez=findViewById(R.id.suez);
        makenode("suez",suez);

        delta=findViewById(R.id.delta);
        makenode("delta",delta);

        behera=findViewById(R.id.behera);
        makenode("behera",behera);

        alex=findViewById(R.id.alex);
        makenode("alex",alex);

        redsea=findViewById(R.id.redsea);
        makenode("redsea",redsea);

        northsinai=findViewById(R.id.northsinai);
        makenode("northsinai",northsinai);

        southsinai=findViewById(R.id.southsinai);
        makenode("southsinai",southsinai);

        smaelia=findViewById(R.id.smaelia);
        makenode("smaelia",smaelia);


        for(int i=0;i<nodes.length;i++){
            if(nodes[i]==null)
                break;
            nodes[i].getMe().setOnClickListener(this);

        }
    }


    private void preparenodes(){
        ArrayList<String> attacks=new ArrayList<String>();


        attacks.add("aswan");
        attacks.add("qena");
        attacks.add("sohag");
        attacks.add("assiut");
        attacks.add("elmenia");
        attacks.add("giza");
        attacks.add("matrouh");
        putattacks("wadigedid", attacks);//wadigedid
        attacks.clear();



        attacks.add("assiut");
        attacks.add("wadigedid");
        attacks.add("giza");
        attacks.add("baniswef");
        attacks.add("redsea");
        putattacks("elmenia", attacks); //elmenia
        attacks.clear();



        attacks.add("baniswef");
        attacks.add("cairo");
        attacks.add("giza");
        putattacks("elfayoum", attacks); //elfayoum
        attacks.clear();


        attacks.add("matrouh");
        attacks.add("wadigedid");
        attacks.add("elmenia");
        attacks.add("baniswef");
        attacks.add("fayoum");
        attacks.add("behera");
        attacks.add("delta");
        attacks.add("cairo");
        attacks.add("elmenia");
        putattacks("giza", attacks); //giza
        attacks.clear();


        attacks.add("giza");
        attacks.add("alex");
        attacks.add("behera");
        attacks.add("wadigedid");
        putattacks("matrouh", attacks); //matrouh
        attacks.clear();


        attacks.add("qena");
        attacks.add("redsea");
        attacks.add("wadigedid");
        putattacks("aswan", attacks); //aswan
        attacks.clear();


        attacks.add("sohag");
        attacks.add("redsea");
        attacks.add("aswan");
        attacks.add("wadigedid");
        putattacks("qena", attacks); //qena
        attacks.clear();


        attacks.add("redsea");
        attacks.add("qena");
        attacks.add("assiut");
        attacks.add("wadigedid");
        putattacks("sohag", attacks); //sohag
        attacks.clear();


        attacks.add("sohag");
        attacks.add("elmenia");
        attacks.add("redsea");
        attacks.add("wadigedid");
        putattacks("assiut", attacks); //assiut
        attacks.clear();


        attacks.add("redsea");
        attacks.add("elmenia");
        attacks.add("giza");
        attacks.add("fayoum");
        attacks.add("cairo");
        attacks.add("suez");
        putattacks("baniswef", attacks); //baniswef
        attacks.clear();


        attacks.add("suez");
        attacks.add("baniswef");
        attacks.add("giza");
        attacks.add("delta");
        attacks.add("smaelia");
        putattacks("cairo", attacks); //cairo
        attacks.clear();


        attacks.add("cairo");
        attacks.add("baniswef");
        attacks.add("redsea");
        attacks.add("delta");
        attacks.add("smaelia");
        attacks.add("northsinai");
        attacks.add("southsinai");
        putattacks("suez", attacks); //suez
        attacks.clear();


        attacks.add("smaelia");
        attacks.add("suez");
        attacks.add("cairo");
        attacks.add("giza");
        attacks.add("behera");
        putattacks("delta", attacks); //delta
        attacks.clear();


        attacks.add("delta");
        attacks.add("giza");
        attacks.add("matrouh");
        attacks.add("alex");
        putattacks("behera", attacks); //behera
        attacks.clear();


        attacks.add("behera");
        attacks.add("matrouh");
        putattacks("alex", attacks); //alex
        attacks.clear();


        attacks.add("aswan");
        attacks.add("qena");
        attacks.add("sohag");
        attacks.add("assiut");
        attacks.add("elmenia");
        attacks.add("baniswef");
        attacks.add("suez");
        putattacks("redsea", attacks); //redsea
        attacks.clear();



        attacks.add("smaelia");
        attacks.add("southsinai");
        attacks.add("suez");
        putattacks("northsinai", attacks); //northsinai
        attacks.clear();


        attacks.add("northsinai");
        attacks.add("suez");
        putattacks("southsinai", attacks); //southsinai
        attacks.clear();


        attacks.add("delta");
        attacks.add("northsinai");
        attacks.add("suez");
        putattacks("smaelia", attacks); //smaelia
        attacks.clear();

    }

    private void putattacks(String me, ArrayList<String> attacks){
        node here=map.get(me);
        int j=0;
        while(!attacks.isEmpty()){
            here.addtoNodestoattack(attacks.remove(j));

        }
    }
    private void makenode(String id,TextView state){
        node node=new node(id,state);
        map.put(id,node);
        nodes[pointer]=node;
        pointer++;
    }

    public void onClick(View v) {
        if(v.getId()==image.getId()){

            //Toast.makeText(MainActivity.this,"Everything is reset", Toast.LENGTH_LONG).show();
        }
        for(int i=0;i<nodes.length;i++){
            if(nodes[i]==null)
                break;
            if(v.getId()==nodes[i].getMe().getId()){
                // Toast.makeText(MainActivity.this,"done1",Toast.LENGTH_SHORT).show();
                if(mode==DRAFT){
                    //  Toast.makeText(MainActivity.this,"done",Toast.LENGTH_SHORT).show();
                    if(nodes[i].getPlayer()==Color.BLACK||nodes[i].getPlayer()==play){
                        final customdialogue cd=new customdialogue(Egypt.this);
                        customdialogue.max=draftvalue;
                        cd.show();
                        final int k=i;
                        cd.ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int prev=prog+Integer.parseInt(nodes[k].getMe().getText().toString());

                                nodes[k].getMe().setText(Integer.toString(prev));
                                draftvalue=draftvalue-prog;
                                draft.setText(Integer.toString(draftvalue));
                                customdialogue.max=draftvalue;
                                nodes[k].getMe().setBackgroundColor(play);
                                if(nodes[k].getPlayer()==Color.BLACK){
                                    if(play==player1.getColor()){
                                        // Toast.makeText(MainActivity.this,"done",Toast.LENGTH_LONG).show();
                                        player1.setMyterritories(nodes[k]);
                                    }
                                    if(play==player2.getColor()){
                                        player2.setMyterritories(nodes[k]);
                                    }
                                }
                                nodes[k].setPlayer(play);

                                Toast.makeText(Egypt.this,nodes[k].getId()+" is ours",Toast.LENGTH_SHORT).show();
                                cd.cancel();
                                prog=0;
                                if(draftvalue==0){
                                    if(!(play ==PLAYER_TWO_COLOR)){
                                        selectplayer();
                                        if(firsttime){
                                            draftvalue=calcdraftvalue();
                                            firsttime=false;
                                        }else{
                                            draftvalue=calcdraftvalue();
                                        }
                                        draft.setText(Integer.toString(draftvalue));

                                        draft.setBackgroundColor(play);
                                        if(passiveagent.enable){
                                            passiveagentfunc();
                                        }else if(aggressive.enable){
                                            aggressivedraft();
                                        }else if(pacifict.enable){
                                            pacifictdraft();
                                        }else if(greedyagent.enable){
                                            greedydraft();
                                        }else if(A_star.enable){
                                            A_stardraft();
                                        }


                                    }else {

                                        draftvalue=0;
                                        draft.setText("");
                                        draft.setBackgroundColor(getResources().getColor(R.color.background,null));
                                    }


                                }
                                //  Toast.makeText(MainActivity.this,Integer.toString(draftvalue),Toast.LENGTH_SHORT).show();

                                if(play==PLAYER_TWO_COLOR&&draftvalue==0){

                                    changemode();
                                    selectplayer();
                                    draftvalue=calcdraftvalue();
                                }
                            }
                        });


                    }else{
                        Toast.makeText(Egypt.this,"this territory is occupied by the enemy",Toast.LENGTH_SHORT).show();
                    }


                }
                else if(mode==ATTACK){
                    if(attack) {
                        if(nodes[i].getPlayer()==Color.BLACK){
                            Toast.makeText(Egypt.this,"This territory has no troops",Toast.LENGTH_SHORT).show();
                        }else if(nodes[i].getPlayer()==play){
                            attacker = nodes[i];
                            Toast.makeText(Egypt.this, attacker.getId() + " attacks", Toast.LENGTH_LONG).show();
                            //attacker.getMe().setTextColor(Color.RED);
                            attack=false;
                        }else{
                            Toast.makeText(Egypt.this,"This territory is occupied by your enemy",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        if(nodes[i].getPlayer()==play){
                            Toast.makeText(Egypt.this,"You cannot attack yourself",Toast.LENGTH_SHORT).show();
                        }else{
                            if(attacker.getNodestoattack().contains(nodes[i].getId())){

                                setDefender(nodes[i]);
                                if(defender.getPlayer()==Color.BLACK){
                                    final customdialogue cd=new customdialogue(Egypt.this);
                                    customdialogue.max=Integer.parseInt(attacker.getMe().getText().toString())-1;
                                    cd.show();
                                    cd.ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            defender.setPlayer(play);
                                            defender.getMe().setBackgroundColor(play);
                                            defender.getMe().setText(Integer.toString(prog));
                                            attacker.getMe().setText(Integer.toString(customdialogue.max+1-prog));
                                            if(play==player1.getColor()){
                                                //   Toast.makeText(MainActivity.this,"done",Toast.LENGTH_LONG).show();
                                                player1.setMyterritories(defender);
                                            }
                                            if(play==player2.getColor()){
                                                player2.setMyterritories(defender);
                                            }
                                            checkwin();
                                            attacker=null;
                                            defender=null;
                                            cd.cancel();

                                        }
                                    });


                                }else{
                                    makedialoge(attacker.getId(),defender.getId());
                                }
                                // defender.getMe().setTextColor(Color.GREEN);

                            }else{
                                Toast.makeText(Egypt.this,"this state is too far from the attacker territory",Toast.LENGTH_LONG).show();
                            }

                        }

                    }
                }
            }
        }
    }
    private void makedialoge(String attackee, final String defendee){
        AlertDialog.Builder builder = new AlertDialog.Builder(Egypt.this);
        builder.setMessage("Are you sure that "+attackee+" will attack "+defendee+" ?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final Attackdialogue at=new Attackdialogue(Egypt.this);
                        at.setColorofattacker(attacker.getPlayer());
                        at.setColorofdefender(defender.getPlayer());
                        at.setAttackers(Integer.parseInt(attacker.getMe().getText().toString())-1);
                        at.setDefenders(Integer.parseInt(defender.getMe().getText().toString()));
                        at.show();
                        at.fight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                at.attackersbase=at.attackers;
                                while(at.attackers!=0&&at.defenders!=0){
                                    at.restofattackers=at.attackers-at.valueofnerds;
                                    at.restofdefenders=at.defenders-at.valueofnerdsdef;
                                    at.values.setText("                   "+Integer.toString(at.attackers)+" -> "+Integer.toString(at.restofattackers)+"                       "+Integer.toString(at.defenders)+" -> "+Integer.toString(at.restofdefenders)+"         ");
                                    int minnerds;
                                    if(at.valueofnerds>at.valueofnerdsdef){
                                        minnerds=at.valueofnerdsdef;
                                    }else{
                                        minnerds=at.valueofnerds;
                                    }

                                    at.randomselection(minnerds);
                                    int h=at.compare(minnerds);
                                    //Toast.makeText(context,Integer.toString(h),Toast.LENGTH_SHORT).show();
                                    at.defenders-=h;
                                    at.attackers-= (minnerds-h);
                                    at.winner=at.checkwinner();
                                    at.check();
                                }

                                Toast.makeText(Egypt.this,at.winner,Toast.LENGTH_LONG).show();
                                if(at.winner.equalsIgnoreCase("Attacker")){
                                    Egypt.attackers=at.attackers;
                                    Egypt.winner=at.winner;
                                    final customdialogue cd=new customdialogue(Egypt.this);
                                    customdialogue.max=attackers;
                                    cd.show();
                                    cd.ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(defender==null){
                                                Toast.makeText(Egypt.this,"null",Toast.LENGTH_SHORT).show();
                                            }
                                            try {
                                                defender.getMe().setText(Integer.toString(prog));
                                            }catch (Exception e){
                                                e.getMessage();
                                            }
                                            defender.setPlayer(play);
                                            defender.getMe().setBackgroundColor(play);
                                            attacker.getMe().setText(Integer.toString(attackers-prog+1));
                                            if(play==player1.getColor()){
                                                player1.setMyterritories(defender);
                                                player2.getMyterritories().remove(defender);
                                            }
                                            if(play==player2.getColor()){
                                                player2.setMyterritories(defender);
                                                player1.getMyterritories().remove(defender);
                                            }
                                            cd.cancel();
                                            setDefender(null);
                                        }
                                    });
                                }else if(at.winner.equalsIgnoreCase("Defender")){
                                    Egypt.winner=at.winner;
                                    Egypt.defenders=at.defenders;
                                    Egypt.attackers=at.attackersbase;
                                    defenders=defenders+attackers;
                                    Toast.makeText(Egypt.this,Integer.toString(defenders),Toast.LENGTH_SHORT).show();
                                    defender.getMe().setText(Integer.toString(defenders));
                                    attacker.getMe().setText("1");
                                    setDefender(null);
                                }

                                //cancel();
                            }


                        });

                        at.exit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                at.cancel();
                                attacker=null;
                                defender=null;

                                checkwin();
                                skip();
                                // passiveagentattack();

                            }
                        });


                      /*  Toast.makeText(MainActivity.this,Integer.toString(defenders),Toast.LENGTH_SHORT).show();

                        if(winner.equalsIgnoreCase("Attacker")){
                            final customdialogue cd=new customdialogue(MainActivity.this);
                            customdialogue.max=attackers;
                            cd.show();

                        }else if(winner.equalsIgnoreCase("Defender")){

defenders=defenders+attackers;
Toast.makeText(MainActivity.this,Integer.toString(defenders),Toast.LENGTH_SHORT).show();
defender.getMe().setText(Integer.toString(defenders));
                        }

                        setDefender(null);*/
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {



                        defender.getMe().setTextColor(Color.WHITE);
                        setDefender(null);
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }
    public void reset(View v){
        reset();
    }
    public void reset(){
        for(int i=0;i<nodes.length;i++){
            if(nodes[i]==null)
                break;
            nodes[i].getMe().setTextColor(Color.WHITE);
        }
        attacker=null;
        defender=null;
        attack=true;
    }
    private void selectplayer(){
        if(play==PLAYER_ONE_COLOR){
            play=PLAYER_TWO_COLOR;
        }else if(play==PLAYER_TWO_COLOR){
            play=PLAYER_ONE_COLOR;
        }

    }
    private void changemode(){
        if(mode==DRAFT){
            mode=ATTACK;
        }else if(mode==ATTACK){
            mode=DRAFT;
            draftvalue=calcdraftvalue();
            draft.setText(Integer.toString(draftvalue));
            draft.setVisibility(View.VISIBLE);

        }
    }

    public void skip(View view) {

        if(mode==ATTACK){
            if (play==PLAYER_ONE_COLOR){
                play=PLAYER_TWO_COLOR;
            }else{
                changemode();
                play=PLAYER_ONE_COLOR;
                draft.setBackgroundColor(play);
            }
        }
        if(play==PLAYER_TWO_COLOR){
            if(passiveagent.enable){
                passiveagentattack();
            }else if(aggressive.enable){
                aggressiveattack();
            }else if(pacifict.enable){
                pacifictattack();
            }else if(greedyagent.enable){
                greedyattack();
            }else if(A_star.enable){
                A_starattack();
            }


        }
        draftvalue=calcdraftvalue();
        draft.setText(Integer.toString(draftvalue));

    }
    public int calcdraftvalue(){
        int count=0;
        for(int i=0;i<nodes.length;i++){
            if(nodes[i]==null)
                break;
            if(nodes[i].getPlayer()==play){
                count++;
            }
        }
        //Toast.makeText(this,Integer.toString(count),Toast.LENGTH_LONG);
        int res=count/3;
        if(res<=3){
            return 3;
        }

        return res;
    }
    private void random(){
        Random random=new Random();

        int test=0;
        draftvalue=20;
        int put;
        for(int i=0;i<booleans.length;i++){
            booleans[i]=false;
        }

        while(draftvalue!=0){
            test=random.nextInt(18)+0;
            if(booleans[test]==false){
                booleans[test]=true;
                put=random.nextInt(draftvalue)+1;
                nodes[test].setPlayer(play);
                nodes[test].getMe().setBackgroundColor(play);
                nodes[test].getMe().setText(Integer.toString(put));
                if(play==player1.getColor()){
                    player1.setMyterritories(nodes[test]);
                }
                if(play==player2.getColor()){
                    player2.setMyterritories(nodes[test]);
                }

                draftvalue=draftvalue-put;
            }else{

            }

        }
    }
    private boolean checkwin(){
        int red=0,blue=0;
        //Toast.makeText(MainActivity.this,Integer.toString(red)+"       "+Integer.toString(blue),Toast.LENGTH_LONG);

        //    Toast.makeText(MainActivity.this,Integer.toString(red)+"       "+Integer.toString(blue),Toast.LENGTH_LONG);
        red=player1.getMyterritories().size();

        //      Toast.makeText(MainActivity.this,Integer.toString(red)+"       "+Integer.toString(blue),Toast.LENGTH_LONG);
        blue=player2.getMyterritories().size();

//Toast.makeText(MainActivity.this,Integer.toString(red)+"       "+Integer.toString(blue),Toast.LENGTH_LONG).show();
        if(red==0){
            Intent i=new Intent(Egypt.this,Main.class);
            return  true;

        }
        if(blue==0){
            Intent i=new Intent(Egypt.this,Main.class);
            return true;
        }
        return false;
    }
    public ArrayList<state> read(){
        ArrayList<state> states=new ArrayList<state>();
        String f="";
        try {

            InputStream is=getAssets().open("egyptlocations.txt");
            int size=is.available();
            byte[] Buffer=new byte[size];
            is.read(Buffer);
            is.close();
            f=new String(Buffer);

            String[] g=f.split("\n");
            String tabdeel="";
            for(int i=0;i<g.length;i=i+3 ){
                tabdeel=g[i].substring(0,g[i].length()-1);
                h=new state(tabdeel,Double.parseDouble(g[i+1]),Double.parseDouble(g[i+2]));
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

}
