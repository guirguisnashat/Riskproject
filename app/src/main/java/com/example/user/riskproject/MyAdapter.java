package com.example.user.riskproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
private String[] mdataset;
    private Context context;

    public MyAdapter(String[] mdataset, Context context){
        this.mdataset=mdataset;
        this.context=context;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public MyViewHolder(View d) {
            super(d);
            mTextView  = (TextView)d.findViewById(R.id.textrecycle);
          mTextView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(mTextView.getText().toString().equalsIgnoreCase("Passive Agent")){
                      if(!passiveagent.enable){
                          passiveagent.enable=true;
                          Toast.makeText(context,"passive is enabled",Toast.LENGTH_LONG).show();
                      }else{
                          passiveagent.enable=false;
                          Toast.makeText(context,"passive is disabled",Toast.LENGTH_LONG).show();
                      }

                  }else if(mTextView.getText().toString().equalsIgnoreCase("Aggressive Agent")){
                      if(!aggressive.enable){
                          aggressive.enable=true;
                          Toast.makeText(context,"aggressive is enabled",Toast.LENGTH_LONG).show();
                      }else{
                          aggressive.enable=false;
                          Toast.makeText(context,"aggressive is disabled",Toast.LENGTH_LONG).show();
                      }
                  }else if(mTextView.getText().toString().equalsIgnoreCase("Pacifist Agent")){
                      if(!pacifict.enable){
                          pacifict.enable=true;
                          Toast.makeText(context,"pacifist is enabled",Toast.LENGTH_LONG).show();
                      }else{
                          pacifict.enable=false;
                          Toast.makeText(context,"pacifist is disabled",Toast.LENGTH_LONG).show();
                      }
                  }else if(mTextView.getText().toString().equalsIgnoreCase("Greedy Agent")){
                      if(!greedyagent.enable){
                          greedyagent.enable=true;
                          Toast.makeText(context,"Greedy is enabled",Toast.LENGTH_LONG).show();
                      }else{
                          greedyagent.enable=false;
                          Toast.makeText(context,"Greedy is disabled",Toast.LENGTH_LONG).show();
                      }
                  }else if(mTextView.getText().toString().equalsIgnoreCase("A* Agent")){
                      if(!A_star.enable){
                          A_star.enable=true;
                          Toast.makeText(context,"A_star is enabled",Toast.LENGTH_LONG).show();
                      }else{
                          A_star.enable=false;
                          Toast.makeText(context,"A_star is disabled",Toast.LENGTH_LONG).show();
                      }
                  }else if(mTextView.getText().toString().equalsIgnoreCase("Real-Time A*Agent")){
                      if(!realtimeastar.enable){
                          realtimeastar.enable=true;
                          Toast.makeText(context,"Real time A* Agent is enabled",Toast.LENGTH_LONG).show();
                      }else{
                          realtimeastar.enable=false;
                          Toast.makeText(context,"Real time A* Agent is disabled",Toast.LENGTH_LONG).show();
                      }
                  }
                  // enabling the rest of agents

              }
          });

    }
        }



    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View d= inflater.inflate(R.layout.my_text_view,parent,false);
        MyViewHolder vh = new MyViewHolder(d);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.mTextView.setText(mdataset[position]);

    }

    @Override
    public int getItemCount() {
        return mdataset.length;
    }
}
