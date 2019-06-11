package com.example.user.riskproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class choosingmap extends AppCompatActivity implements View.OnClickListener{
private ImageView america,egypt;
boolean simulation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosingmap);

        simulation=getIntent().getBooleanExtra("sim",false);
    america=findViewById(R.id.america);
    america.setOnClickListener(this);
    egypt=findViewById(R.id.egypt);
    egypt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==america.getId()){
            Intent i=new Intent(this,MainActivity.class);
            i.putExtra("sim",simulation);
            startActivity(i);
        }else if(v.getId()==egypt.getId()){
            Intent i=new Intent(this,Egypt.class);
            i.putExtra("sim",simulation);
            startActivity(i);
        }


    }


}
