package com.example.user.riskproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class choosingplayers extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    boolean simulation;
    private String[] myDataset={
            "Passive Agent","Aggressive Agent","Pacifist Agent","Greedy Agent","A* Agent","Real-Time A* Agent",
            "Minmax Agent"
    };
    private Button go;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosingplayers);

        simulation=getIntent().getBooleanExtra("sim",false);
        mRecyclerView=findViewById(R.id.recyle);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset,this);
        mRecyclerView.setAdapter(mAdapter);
        go=findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(choosingplayers.this,choosingmap.class);
                i.putExtra("sim",simulation);
                startActivity(i);
            }
        });
    }
}
