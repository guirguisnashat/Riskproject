package com.example.user.riskproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final int PLAYER_ONE_COLOR=Color.RED;
    final int PLAYER_TWO_COLOR=Color.BLUE;
    Handler handler=new Handler();
    state h=null;
     boolean firsttime=true;
    final int DRAFT=0,ATTACK=1,FORTIFY=2;
    int mode=1;
    boolean simulation;
  Context c=MainActivity.this;
    static int prog;
    static int attackers,defenders;
    static String winner="";
 pacifict pacifict=new pacifict();
 greedyagent greedyagent=new greedyagent();
 A_star a_star=new A_star();
 realtimeastar realtimeastar=new realtimeastar();
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
    int[] players={
       PLAYER_ONE_COLOR,PLAYER_TWO_COLOR
    };
    player player1=new player(PLAYER_ONE_COLOR);
    player player2=new player(PLAYER_TWO_COLOR);
    int play= Color.RED;
    ImageView image;
    TextView texas;
    TextView kansas;
    TextView oklahoma;
    TextView newmexico;
    TextView colorado;
    TextView arizona;
    TextView utah;
    TextView nevada;
    TextView california;
    TextView idaho;
    TextView oregon;
    TextView washington;
    TextView montana;
    TextView wyoming;
    TextView northdakota;
    TextView southdakota;
    TextView nebraska;
    TextView minnesota;
    TextView lowa;
    TextView missouri;
    TextView wisconsin;
    TextView illinois;
    TextView arkansas;
    TextView louisiana;
    TextView indiana;
    TextView michigan;
    TextView ohio;
    TextView kentucky;
    TextView tennessee;
    TextView mississippi;
    TextView alabama;
    TextView georgia;
    TextView florida;
    TextView southcarolina;
    TextView northcarolina;
    TextView virginia;
    TextView westvirginia;
    TextView pennsylvania;
    TextView newyork;
    TextView maine;
    TextView draft;
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        simulation=getIntent().getBooleanExtra("sim",false);
        setContentView(R.layout.activity_main);
        init();
        preparenodes();
        draft=findViewById(R.id.draft);
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

simulation();
    }

    public void realtimeastardraft(){
        if(realtimeastar.enable){
            TextView k=null;
            if (play == PLAYER_TWO_COLOR){
                k= realtimeastar.draft(player2);
                int j=Integer.parseInt(k.getText().toString());
                k.setText(Integer.toString(j+calcdraftvalue()));
                selectplayer();
                draft.setBackgroundColor(play);
                changemode();
            }

        }

    }
    public void realtimeastarattack(){
        if(realtimeastar.enable) {
            player d = player1;
            player f = player2;
            //node[] h=greedyagent.attack(d,f,distancesamerica);
            //Toast.makeText(this,player2.toString(),Toast.LENGTH_LONG).show();
            attacker = realtimeastar.A_star(realtimeastar.attack(d, f, distancesamerica), distancesamerica)[1];
            defender = realtimeastar.A_star(realtimeastar.attack(d, f, distancesamerica), distancesamerica)[0];
            Toast.makeText(this, attacker.getId() + "   " + defender.getId(), Toast.LENGTH_LONG).show();
            if (defender.getPlayer() == Color.BLACK) {
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


                final customdialogue cd = new customdialogue(MainActivity.this);
                customdialogue.max = Integer.parseInt(attacker.getMe().getText().toString()) - 1;
                cd.show();
                cd.ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        defender.setPlayer(play);
                        defender.getMe().setBackgroundColor(play);
                        defender.getMe().setText(Integer.toString(prog));
                        attacker.getMe().setText(Integer.toString(customdialogue.max + 1 - prog));
                        if (play == player1.getColor()) {
                            player1.setMyterritories(defender);
                        }
                        if (play == player2.getColor()) {
                            player2.setMyterritories(defender);
                        }
                        checkwin();
                        changemode();
                        selectplayer();
                        attacker = null;
                        defender = null;
                        cd.cancel();
                    }
                });
            } else {
                makedialoge(attacker.getId(), defender.getId());
            }
        }
    }
    public void A_stardraft(){
    if(A_star.enable){
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

    }
    public void A_starattack(){
    if(A_star.enable) {
        player d = player1;
        player f = player2;
        //node[] h=greedyagent.attack(d,f,distancesamerica);
        //Toast.makeText(this,player2.toString(),Toast.LENGTH_LONG).show();
        attacker = a_star.A_star(a_star.attack(d, f, distancesamerica), distancesamerica)[1];
        defender = a_star.A_star(a_star.attack(d, f, distancesamerica), distancesamerica)[0];
        Toast.makeText(this, attacker.getId() + "   " + defender.getId(), Toast.LENGTH_LONG).show();
        if (defender.getPlayer() == Color.BLACK) {
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


            final customdialogue cd = new customdialogue(MainActivity.this);
            customdialogue.max = Integer.parseInt(attacker.getMe().getText().toString()) - 1;
            cd.show();
            cd.ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    defender.setPlayer(play);
                    defender.getMe().setBackgroundColor(play);
                    defender.getMe().setText(Integer.toString(prog));
                    attacker.getMe().setText(Integer.toString(customdialogue.max + 1 - prog));
                    if (play == player1.getColor()) {
                        player1.setMyterritories(defender);
                    }
                    if (play == player2.getColor()) {
                        player2.setMyterritories(defender);
                    }
                    checkwin();
                    changemode();
                    selectplayer();
                    attacker = null;
                    defender = null;
                    cd.cancel();
                }
            });
        } else {
            makedialoge(attacker.getId(), defender.getId());
        }
    }
    }
    public void greedydraft(){
       if(greedyagent.enable) {
           TextView k = null;
           if (play == PLAYER_TWO_COLOR) {
               k = greedyagent.draft(player2);
               int j = Integer.parseInt(k.getText().toString());
               k.setText(Integer.toString(j + calcdraftvalue()));
               selectplayer();
               draft.setBackgroundColor(play);
               changemode();
           }
       }
    }
    public void greedyattack(){

    if(greedyagent.enable){
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


            final customdialogue cd=new customdialogue(MainActivity.this);
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

    }
    public void aggressiveattack(){
if(aggressive.enable){
    attacker=aggressive.attack(player1,player2)[0];
    defender=aggressive.attack(player1,player2)[1];
    Toast.makeText(this,attacker+"   "+defender,Toast.LENGTH_LONG).show();
    if(attacker==null&&defender==null){
        for(int i=0;i<player2.getMyterritories().size();i++){
            for(int j=0;j<player2.getMyterritories().get(i).getNodestoattack().size();j++){
                if(map.get(player2.getMyterritories().get(i).getNodestoattack().get(j)).getPlayer()!=play){

                    if(player2.getMyterritories().get(i).getMe().getText().toString().equalsIgnoreCase("1")){

                    }else{
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


        final customdialogue cd=new customdialogue(MainActivity.this);
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


    }
    public void pacifictdraft(){
    if(com.example.user.riskproject.pacifict.enable){
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

    }
    public void pacifictattack(){
if(com.example.user.riskproject.pacifict.enable){
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
                    if(player2.getMyterritories().get(i).getMe().getText().toString().equalsIgnoreCase("1")){

                    }else{
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
        final customdialogue cd=new customdialogue(MainActivity.this);
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
        //defender.setPlayer(PLAYER_ONE_COLOR);
        //attacker.setPlayer(play);
        makedialoge(attacker.getId(),defender.getId());
    }
}


    }
    public void aggressivedraft(){
    if(com.example.user.riskproject.aggressive.enable){
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

    }
    public void passiveagentfunc (){
   if(passiveagent.enable){
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

    private void init(){
     image=findViewById(R.id.imageView);
     image.setOnClickListener(this);
     texas=findViewById(R.id.texas);
     makenode("texas",texas);
     kansas=findViewById(R.id.kansas);
     makenode("kansas",kansas);
     oklahoma=findViewById(R.id.oklahoma);
     makenode("oklahoma",oklahoma);
     newmexico=findViewById(R.id.newmexico);
     makenode("newmexico",newmexico);
     colorado=findViewById(R.id.colorado);
     makenode("colorado",colorado);
     arizona=findViewById(R.id.arizona);
     makenode("arizona",arizona);
     utah=findViewById(R.id.utah);
     makenode("utah",utah);
     nevada=findViewById(R.id.nevada);
     makenode("nevada",nevada);
     california=findViewById(R.id.california);
     makenode("california",california);
     idaho=findViewById(R.id.idaho);
     makenode("idaho",idaho);
     oregon=findViewById(R.id.oregon);
     makenode("oregon",oregon);
     washington=findViewById(R.id.washington);
     makenode("washington",washington);
     montana=findViewById(R.id.montana);
     makenode("montana",montana);
     wyoming=findViewById(R.id.wyoming);
     makenode("wyoming",wyoming);
     northdakota=findViewById(R.id.northdakota);
     makenode("northdakota",northdakota);
     southdakota=findViewById(R.id.southdakota);
     makenode("southdakota",southdakota);
     nebraska=findViewById(R.id.nebraska);
     makenode("nebraska",nebraska);
     minnesota=findViewById(R.id.minnesota);
     makenode("minnesota",minnesota);
     lowa=findViewById(R.id.lowa);
     makenode("lowa",lowa);
     missouri=findViewById(R.id.missouri);
     makenode("missouri",missouri);
        wisconsin=findViewById(R.id.wisconsin);
        makenode("wisconsin",wisconsin);
        illinois=findViewById(R.id.illinois);
        makenode("illinois",illinois);
        arkansas=findViewById(R.id.arkansas);
        makenode("arkansas",arkansas);
        louisiana=findViewById(R.id.louisiana);
        makenode("louisiana",louisiana);
        michigan=findViewById(R.id.michigan);
        makenode("michigan",michigan);
        indiana=findViewById(R.id.indiana);
        makenode("indiana",indiana);
        ohio=findViewById(R.id.ohio);
        makenode("ohio",ohio);
        kentucky=findViewById(R.id.kentucky);
        makenode("kentucky",kentucky);
        tennessee=findViewById(R.id.tennessee);
        makenode("tennessee",tennessee);
        mississippi=findViewById(R.id.mississippi);
        makenode("mississippi",mississippi);
        alabama=findViewById(R.id.alabama);
        makenode("alabama",alabama);
        georgia=findViewById(R.id.georgia);
        makenode("georgia",georgia);
        florida=findViewById(R.id.florida);
        makenode("florida",florida);
        virginia=findViewById(R.id.virginia);
        makenode("virginia",virginia);
        westvirginia=findViewById(R.id.westvirginia);
        makenode("westvirginia",westvirginia);
        northcarolina=findViewById(R.id.northcarolina);
        makenode("northcarolina",northcarolina);
        southcarolina=findViewById(R.id.southcarolina);
        makenode("southcarolina",southcarolina);
        pennsylvania=findViewById(R.id.pennsylvnia);
        makenode("pennsylvania",pennsylvania);
        newyork=findViewById(R.id.newyork);
        makenode("newyork",newyork);
        maine=findViewById(R.id.maine);
        makenode("maine",maine);

        for(int i=0;i<nodes.length;i++){
          if(nodes[i]==null)
              break;
          nodes[i].getMe().setOnClickListener(this);

        }


    }
    private void makenode(String id,TextView state){
        node node=new node(id,state);
        map.put(id,node);
        nodes[pointer]=node;
        pointer++;
    }
    private void preparenodes(){
     ArrayList<String> attacks=new ArrayList<String>();
     attacks.add("oklahoma");
     attacks.add("arkansas");
     attacks.add("louisiana");
     attacks.add("newmexico");
     putattacks("texas",attacks);
     attacks.clear();

       attacks.add("texas");
       attacks.add("missouri");
       attacks.add("arkansas");
       attacks.add("kansas");
       attacks.add("newmexico");
       attacks.add("colorado");
        putattacks("oklahoma",attacks);
        attacks.clear();

        attacks.add("colorado");
        attacks.add("oklahoma");
        attacks.add("nebraska");
        attacks.add("missouri");
        putattacks("kansas",attacks);
        attacks.clear();

        attacks.add("texas");
        attacks.add("oklahoma");
        attacks.add("colorado");
        attacks.add("arizona");
        putattacks("newmexico",attacks);
        attacks.clear();

        attacks.add("oklahoma");
        attacks.add("kansas");
        attacks.add("nebraska");
        attacks.add("newmexico");
        attacks.add("utah");
        attacks.add("wyoming");
        putattacks("colorado",attacks);
        attacks.clear();

        attacks.add("newmexico");
        attacks.add("utah");
        attacks.add("california");
        attacks.add("nevada");
        putattacks("arizona",attacks);
        attacks.clear();

        attacks.add("arizona");
        attacks.add("colorado");
        attacks.add("wyoming");
        attacks.add("nevada");
        attacks.add("idaho");
        putattacks("utah",attacks);
        attacks.clear();

        attacks.add("arizona");
        attacks.add("utah");
        attacks.add("idaho");
        attacks.add("california");
        attacks.add("oregon");
        putattacks("nevada",attacks);
        attacks.clear();

        attacks.add("oregon");
        attacks.add("nevada");
        attacks.add("arizona");
        putattacks("california",attacks);
        attacks.clear();

        attacks.add("oregon");
        attacks.add("nevada");
        attacks.add("utah");
        attacks.add("washington");
        attacks.add("montana");
        attacks.add("wyoming");
        putattacks("idaho",attacks);
        attacks.clear();

        attacks.add("washington");
        attacks.add("idaho");
        attacks.add("nevada");
        attacks.add("california");
        putattacks("oregon",attacks);
        attacks.clear();

        attacks.add("oregon");
        attacks.add("idaho");
        putattacks("washington",attacks);
        attacks.clear();

        attacks.add("idaho");
        attacks.add("wyoming");
        attacks.add("northdakota");
        attacks.add("southdakota");
        putattacks("montana",attacks);
        attacks.clear();

        attacks.add("idaho");
        attacks.add("utah");
        attacks.add("montana");
        attacks.add("northdakota");
        attacks.add("southdakota");
        attacks.add("colorado");
        attacks.add("nebraska");
        putattacks("wyoming",attacks);
        attacks.clear();

        attacks.add("montana");
        attacks.add("southdakota");
        attacks.add("minnesota");
        putattacks("northdakota",attacks);
        attacks.clear();

        attacks.add("montana");
        attacks.add("northdakota");
        attacks.add("nebraska");
        attacks.add("lowa");
        attacks.add("wyoming");
        attacks.add("kansas");
        attacks.add("colorado");
        putattacks("southdakota",attacks);
        attacks.clear();

        attacks.add("lowa");
        attacks.add("southdakota");
        attacks.add("northdakota");
        attacks.add("wisconsin");
        putattacks("minnesota",attacks);
        attacks.clear();

        attacks.add("minnesota");
        attacks.add("southdakota");
        attacks.add("nebraska");
        attacks.add("wisconsin");
        attacks.add("illinois");
        attacks.add("missouri");
        putattacks("lowa",attacks);
        attacks.clear();

        attacks.add("southdakota");
        attacks.add("kansas");
        attacks.add("lowa");
        attacks.add("wyoming");
        attacks.add("colorado");
        attacks.add("missouri");
        putattacks("nebraska",attacks);
        attacks.clear();

        attacks.add("lowa");
        attacks.add("nebraska");
        attacks.add("kansas");
        attacks.add("oklahoma");
        attacks.add("arkansas");
        attacks.add("tennessee");
        attacks.add("kentucky");
        attacks.add("illinois");
        putattacks("missouri",attacks);
        attacks.clear();

        attacks.add("missouri");
        attacks.add("oklahoma");
        attacks.add("texas");
        attacks.add("louisiana");
        attacks.add("mississippi");
        attacks.add("tennessee");
        putattacks("arkansas",attacks);
        attacks.clear();

        attacks.add("mississippi");
        attacks.add("arkansas");
        attacks.add("texas");
        putattacks("louisiana",attacks);
        attacks.clear();

        attacks.add("louisiana");
        attacks.add("arkansas");
        attacks.add("tennessee");
        attacks.add("alabama");
        putattacks("mississippi",attacks);
        attacks.clear();

        attacks.add("georgia");
        attacks.add("florida");
        attacks.add("tennessee");
        attacks.add("mississippi");
        putattacks("alabama",attacks);
        attacks.clear();

        attacks.add("florida");
        attacks.add("alabama");
        attacks.add("tennessee");
        attacks.add("northcarolina");
        attacks.add("southcarolina");
        putattacks("georgia",attacks);
        attacks.clear();

        attacks.add("georgia");
        attacks.add("alabama");
        putattacks("florida",attacks);
        attacks.clear();

        attacks.add("northcarolina");
        attacks.add("georgia");
        putattacks("southcarolina",attacks);
        attacks.clear();

        attacks.add("virginia");
        attacks.add("southcarolina");
        attacks.add("georgia");
        attacks.add("tennessee");
        putattacks("northcarolina",attacks);
        attacks.clear();

        attacks.add("northcarolina");
        attacks.add("pennsylvania");
        attacks.add("westvirginia");
        attacks.add("kentucky");
        attacks.add("tennessee");
        putattacks("virginia",attacks);
        attacks.clear();


        attacks.add("pennsylvania");
        attacks.add("virginia");
        attacks.add("kentucky");
        attacks.add("ohio");
        putattacks("westvirginia",attacks);
        attacks.clear();

        attacks.add("newyork");
        attacks.add("virginia");
        attacks.add("westvirginia");
        attacks.add("ohio");
        putattacks("pennsylvania",attacks);
        attacks.clear();

        attacks.add("pennsylvania");
        attacks.add("maine");
        putattacks("newyork",attacks);
        attacks.clear();

        attacks.add("newyork");
        putattacks("maine",attacks);
        attacks.clear();

        attacks.add("wisconsin");
        attacks.add("indiana");
        attacks.add("ohio");
        putattacks("michigan",attacks);
        attacks.clear();

        attacks.add("michigan");
        attacks.add("indiana");
        attacks.add("kentucky");
        attacks.add("westvirginia");
        attacks.add("pennsylvania");
        putattacks("ohio",attacks);
        attacks.clear();

        attacks.add("ohio");
        attacks.add("michigan");
        attacks.add("kentucky");
        attacks.add("illinois");
        putattacks("indiana",attacks);
        attacks.clear();

        attacks.add("indiana");
        attacks.add("wisconsin");
        attacks.add("lowa");
        attacks.add("kentucky");
        attacks.add("missouri");
        putattacks("illinois",attacks);
        attacks.clear();

        attacks.add("michigan");
        attacks.add("minnesota");
        attacks.add("lowa");
        attacks.add("illinois");
        putattacks("wisconsin",attacks);
        attacks.clear();

        attacks.add("tennessee");
        attacks.add("ohio");
        attacks.add("indiana");
        attacks.add("illinois");
        attacks.add("westvirginia");
        attacks.add("virginia");
        attacks.add("missouri");
        putattacks("kentucky",attacks);
        attacks.clear();

        attacks.add("kentucky");
        attacks.add("missouri");
        attacks.add("arkansas");
        attacks.add("mississippi");
        attacks.add("alabama");
        attacks.add("georgia");
        attacks.add("northcarolina");
        attacks.add("virginia");
        putattacks("tennessee",attacks);
        attacks.clear();
}

    private void putattacks(String me, ArrayList<String> attacks){
        node here=map.get(me);
        int j=0;
        while(!attacks.isEmpty()){
            here.addtoNodestoattack(attacks.remove(j));

        }
    }

    @Override
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
                      final customdialogue cd=new customdialogue(MainActivity.this);
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

                             Toast.makeText(MainActivity.this,nodes[k].getId()+" is ours",Toast.LENGTH_SHORT).show();
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
                                      }else if(realtimeastar.enable){
                                         realtimeastardraft();
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
                      Toast.makeText(MainActivity.this,"this territory is occupied by the enemy",Toast.LENGTH_SHORT).show();
                  }


              }
              else if(mode==ATTACK){
               if(attack) {
                   if(nodes[i].getPlayer()==Color.BLACK){
                       Toast.makeText(MainActivity.this,"This territory has no troops",Toast.LENGTH_SHORT).show();
                   }else if(nodes[i].getPlayer()==play){
                       attacker = nodes[i];
                       Toast.makeText(MainActivity.this, attacker.getId() + " attacks", Toast.LENGTH_LONG).show();
                       //attacker.getMe().setTextColor(Color.RED);
                       attack=false;
                   }else{
                       Toast.makeText(MainActivity.this,"This territory is occupied by your enemy",Toast.LENGTH_SHORT).show();
                   }

               }else{
                   if(nodes[i].getPlayer()==play){
                       Toast.makeText(MainActivity.this,"You cannot attack yourself",Toast.LENGTH_SHORT).show();
                   }else{
                       if(attacker.getNodestoattack().contains(nodes[i].getId())){

                           setDefender(nodes[i]);
                           if(defender.getPlayer()==Color.BLACK){
                               final customdialogue cd=new customdialogue(MainActivity.this);
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
                           Toast.makeText(MainActivity.this,"this state is too far from the attacker territory",Toast.LENGTH_LONG).show();
                       }

                   }

               }
              }
            }
        }
    }
    private void makedialoge(String attackee, final String defendee){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure that "+attackee+" will attack "+defendee+" ?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final Attackdialogue at=new Attackdialogue(MainActivity.this);
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

                                Toast.makeText(MainActivity.this,at.winner,Toast.LENGTH_LONG).show();
                                if(at.winner.equalsIgnoreCase("Attacker")){
                                    MainActivity.attackers=at.attackers;
                                    MainActivity.winner=at.winner;
                                    final customdialogue cd=new customdialogue(MainActivity.this);
                                    customdialogue.max=attackers;
                                    cd.show();
                                    cd.ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(defender==null){
                                                Toast.makeText(MainActivity.this,"null",Toast.LENGTH_SHORT).show();
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
                                    MainActivity.winner=at.winner;
                                    MainActivity.defenders=at.defenders;
                                    MainActivity.attackers=at.attackersbase;
                                    defenders=defenders+attackers;
                                    Toast.makeText(MainActivity.this,Integer.toString(defenders),Toast.LENGTH_SHORT).show();
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
        }else if(realtimeastar.enable){
            realtimeastarattack();
        }


    }


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
        test=random.nextInt(39)+0;
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
        Intent i=new Intent(MainActivity.this,Main.class);
        Log.e("guirguis","blue wins");
        return  false;

    }
    if(blue==0){
        Intent i=new Intent(MainActivity.this,Main.class);
        Log.e("guirguis","red wins");
        return false;
    }
    return true;
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

    public void simulation (){

    if (simulation){
        Toast.makeText(MainActivity.this,"simulation",Toast.LENGTH_LONG).show();
    //    while(checkwin()){



        SystemClock.sleep(1500);
        passiveagentattack();
            Log.e("guirguis","passiveattack");
                    SystemClock.sleep(1500);
                    aggressiveattack();
            Log.e("guirguis","aggressive");
                    SystemClock.sleep(1500);
                    pacifictattack();
            Log.e("guirguis","pacifictattack");
                    SystemClock.sleep(1500);
                    greedyattack();
            Log.e("guirguis","greedyattack");
                    SystemClock.sleep(1500);
Toast.makeText(MainActivity.this,"Draft",Toast.LENGTH_LONG).show();
                    A_starattack();
            Log.e("guirguis","astarattack");
                    SystemClock.sleep(1500);
                    passiveagentfunc();
            Log.e("guirguis","passivedraft");
                    SystemClock.sleep(1500);
                    aggressivedraft();
            Log.e("guirguis","aggressivedraft");
                    SystemClock.sleep(1500);
                    pacifictdraft();
            Log.e("guirguis","pacifictdraft");
                    SystemClock.sleep(1500);
                    greedydraft();
            Log.e("guirguis","greedydraft");

        SystemClock.sleep(1500);
        A_stardraft();
            Log.e("guirguis","astardraft");
    //}

        }

    }
    }


