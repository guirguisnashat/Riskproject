package com.example.user.riskproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class customdialogueforchecked extends Dialog {
    private Context activity;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] myDataset={
      "Passive Agent","Aggressive Agent","Pacifist Agent","Greedy Agent","A* Agent","Real-Time A* Agent",
"Minmax Agent"
    };
    private  Button go;
    public customdialogueforchecked(@NonNull Context context) {
        super(context);
        this.activity=  context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.checkeddialogue);
mRecyclerView=findViewById(R.id.recyle);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset,activity);
        mRecyclerView.setAdapter(mAdapter);
go=findViewById(R.id.go);
go.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(activity,choosingmap.class);
        activity.startActivity(i);
    }
});
    }
}
