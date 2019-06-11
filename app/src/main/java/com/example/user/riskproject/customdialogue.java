package com.example.user.riskproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class customdialogue extends Dialog {
    public Activity c;
    public Dialog d;
    public Button ok;
    private SeekBar seekBar;
    private ProgressBar progressBar;
    private TextView textView;
    static int max;
    int prog=0;


    public customdialogue(@NonNull Context context) {
        super(context);
        this.c= (Activity) context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogue);

seekBar=findViewById(R.id.seekbar);
ok=findViewById(R.id.ok);

textView=findViewById(R.id.count);
seekBar.setMax(max);
seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

      MainActivity.prog=progress;
      Egypt.prog=progress;
        textView.setText(Integer.toString(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
});
    }
   public void ok(View v){

   }
}
