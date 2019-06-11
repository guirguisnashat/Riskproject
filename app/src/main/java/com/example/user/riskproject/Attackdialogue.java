package com.example.user.riskproject;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Attackdialogue extends Dialog {

    private Context context;
    public String winner="";
    public Button exit;
    public int attackernum,defendernum;
    private int Colorofattacker;
    private int Colorofdefender;
    private LinearLayout attacker;
    private LinearLayout defender;
    private TextView attacker1;
    private TextView attacker2;
    private TextView attacker3;
    private TextView defender1;
    private TextView defender2;
    private TextView defender3;
    private Button decrease;
    private Button increase;
    private TextView value;
    public Button fight;
    public int valueofnerds;
    private Button decdef;
    private Button incdef;
    private TextView valuedef;
    public int valueofnerdsdef;
    public TextView values;
    public int attackers,defenders,restofattackers,restofdefenders,attackersbase;

    public int getAttackers() {
        return attackers;
    }

    public void setAttackers(int attackers) {
        this.attackers = attackers;
    }

    public int getDefenders() {
        return defenders;
    }

    public void setDefenders(int defenders) {
        this.defenders = defenders;
    }

    public int getColorofattacker() {
        return Colorofattacker;
    }

    public void setColorofattacker(int colorofattacker) {
        Colorofattacker = colorofattacker;
    }

    public int getColorofdefender() {
        return Colorofdefender;
    }

    public void setColorofdefender(int colorofdefender) {
        Colorofdefender = colorofdefender;
    }

    public Attackdialogue(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.attackdialog);
        init();
        attacker.setBackgroundColor(Colorofattacker);
        defender.setBackgroundColor(Colorofdefender);
        values.setText("                   "+Integer.toString(attackers)+"                                                         "+Integer.toString(defenders)+"           ");
        decrease.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(valueofnerds!=1){
                    valueofnerds--;
                    value.setText(Integer.toString(valueofnerds));
                    if(valueofnerds==2){
                        attacker1.setVisibility(View.VISIBLE);
                        attacker2.setVisibility(View.VISIBLE);
                        attacker3.setVisibility(View.INVISIBLE);

                    }else if(valueofnerds==1){
                        attacker1.setVisibility(View.VISIBLE);
                        attacker2.setVisibility(View.INVISIBLE);
                        attacker3.setVisibility(View.INVISIBLE);

                    }
                }else{
                    attacker1.setVisibility(View.VISIBLE);
                    attacker2.setVisibility(View.INVISIBLE);
                    attacker3.setVisibility(View.INVISIBLE);
                }
            }
        });
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valueofnerds!=3){
                        valueofnerds++;
                        value.setText(Integer.toString(valueofnerds));
                        if(valueofnerds==2){
                            attacker1.setVisibility(View.VISIBLE);
                            attacker2.setVisibility(View.VISIBLE);
                            attacker3.setVisibility(View.INVISIBLE);

                        }else if(valueofnerds==3){
                            attacker1.setVisibility(View.VISIBLE);
                            attacker2.setVisibility(View.VISIBLE);
                            attacker3.setVisibility(View.VISIBLE);

                        }
                }
            }
        });
        incdef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valueofnerdsdef!=3){
                    valueofnerdsdef++;
                    valuedef.setText(Integer.toString(valueofnerdsdef));
                    if(valueofnerdsdef ==2){
                        defender1.setVisibility(View.VISIBLE);
                        defender2.setVisibility(View.VISIBLE);
                        defender3.setVisibility(View.INVISIBLE);
                    }else if(valueofnerdsdef==3){
                        defender1.setVisibility(View.VISIBLE);
                        defender2.setVisibility(View.VISIBLE);
                        defender3.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        decdef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valueofnerdsdef!=1){
                    valueofnerdsdef--;
                    valuedef.setText(Integer.toString(valueofnerdsdef));
                    if(valueofnerdsdef==2){
                        defender1.setVisibility(View.VISIBLE);
                        defender2.setVisibility(View.VISIBLE);
                        defender3.setVisibility(View.INVISIBLE);
                    }else if(valueofnerdsdef==1){

                        defender1.setVisibility(View.VISIBLE);
                        defender2.setVisibility(View.INVISIBLE);
                        defender3.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

exit=findViewById(R.id.exit);
exit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        cancel();
    }
});


    }


    public int compare(int min){
        int attackervictory=0,at1=0,at2=0,at3=0,df1=0,df2=0,df3=0;
        if(min>=1){
            at1=Integer.parseInt(attacker1.getText().toString());
            df1=Integer.parseInt(defender1.getText().toString());
            if(at1>df1){
                attackervictory++;
            }

        }
        if(min>=2){
            at2=Integer.parseInt(attacker2.getText().toString());
            df2=Integer.parseInt(defender2.getText().toString());
            if(at2>df2){
                attackervictory++;
            }
        }
       if(min==3){
           at3=Integer.parseInt(attacker3.getText().toString());
           df3=Integer.parseInt(defender3.getText().toString());
           if(at3>df3){
               attackervictory++;
           }
       }
        return attackervictory;
    }

    public void init() {
        attacker = findViewById(R.id.attackerlayout);
        defender = findViewById(R.id.defenderlayout);
        attacker1 = findViewById(R.id.text1);
        attacker2 = findViewById(R.id.text2);
        attacker3 = findViewById(R.id.text3);
        defender1 = findViewById(R.id.text4);
        defender2 = findViewById(R.id.text5);
        defender3 = findViewById(R.id.text6);
        decrease = findViewById(R.id.decrease);
        increase = findViewById(R.id.increase);
        value = findViewById(R.id.valueofnerds);
        valueofnerds = Integer.parseInt(value.getText().toString());
        fight = findViewById(R.id.fight);
        decdef=findViewById(R.id.decreasedef);
        incdef=findViewById(R.id.increasedef);
        valuedef=findViewById(R.id.valueofnerdsdef);
        valueofnerdsdef= Integer.parseInt(valuedef.getText().toString());
        values =findViewById(R.id.values);
    }

    public void randomselection(int min){

        Random rand = new Random();
        if(min>=1){
            attackernum = rand.nextInt(6) + 1;
            defendernum = rand.nextInt(6) + 1;
            attacker1.setText(Integer.toString(attackernum));
            defender1.setText(Integer.toString(defendernum));
        }
       if(min>=2){
           attackernum = rand.nextInt(6) + 1;
           defendernum = rand.nextInt(6) + 1;
           attacker2.setText(Integer.toString(attackernum));
           defender2.setText(Integer.toString(defendernum));
       }
       if(min==3){
           attackernum = rand.nextInt(6) + 1;
           defendernum = rand.nextInt(6) + 1;
           attacker3.setText(Integer.toString(attackernum));
           defender3.setText(Integer.toString(defendernum));

       }

    }

    public String checkwinner(){
        if(attackers==0){
            return "Defender";
        }else if(defenders==0){
            return "Attacker";
        }
    return "";
    }
    public void check(){
        if(attackers<3){
            valueofnerds=attackers;
            if(valueofnerds==1){
                attacker1.setVisibility(View.VISIBLE);
                attacker2.setVisibility(View.INVISIBLE);
                attacker3.setVisibility(View.INVISIBLE);
            }else if(valueofnerds==2){
                attacker1.setVisibility(View.VISIBLE);
                attacker2.setVisibility(View.VISIBLE);
                attacker3.setVisibility(View.INVISIBLE);
            }
        }
        if(defenders<3){
            valueofnerdsdef=defenders;
            if(valueofnerdsdef==1){
                defender1.setVisibility(View.VISIBLE);
                defender2.setVisibility(View.INVISIBLE);
                defender3.setVisibility(View.INVISIBLE);
            }else if(valueofnerdsdef==2){
                defender1.setVisibility(View.VISIBLE);
                defender2.setVisibility(View.VISIBLE);
                defender3.setVisibility(View.INVISIBLE);
            }
        }
    }
    }
