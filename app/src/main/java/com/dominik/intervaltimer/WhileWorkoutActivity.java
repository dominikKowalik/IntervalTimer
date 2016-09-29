package com.dominik.intervaltimer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Objects;

public class WhileWorkoutActivity extends AppCompatActivity {



    TextView textViewTime, textViewRestEnd, textViewWork, textViewWarmUp, textViewSets;
    FloatingActionButton FAB;
    Interval interval;
    ProgressBar progressBar;
    boolean trainingEnded = false;
    boolean trainingPaused = false;
    private final Context context = this;
    BackgroundWork backgroundWork;
    DataBaseAdapter dbAdapter;

    @SuppressWarnings( "deprecation" )

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String messageString = (String) msg.obj;

            if(messageString instanceof String) {
                messageString = (String) msg.obj;
            }
           if(messageString.length() == 8) {
                textViewTime.setText(msg.obj.toString());
            }else if(messageString.length() == 9 ){

            }
            else if(messageString.equals("work")){
                textViewRestEnd.setVisibility(View.INVISIBLE);
                textViewWarmUp.setVisibility(View.INVISIBLE);
                textViewWork.setVisibility(View.VISIBLE);
                textViewWork.setText(getResources().getText(R.string.work_string));
            }
            else if(messageString.equals("rest")){
               textViewWarmUp.setVisibility(View.INVISIBLE);
               textViewWork.setVisibility(View.INVISIBLE);
               textViewRestEnd.setVisibility(View.VISIBLE);
               textViewRestEnd.setText(getResources().getText(R.string.rest_string));

            }else if(messageString.equals("warm")){
                textViewRestEnd.setVisibility(View.INVISIBLE);
                textViewWork.setVisibility(View.INVISIBLE);
                textViewWarmUp.setVisibility(View.VISIBLE);
                textViewWarmUp.setText(getResources().getText(R.string.warm_up_string));
           }else if(Objects.equals(messageString, "end")){
               textViewWarmUp.setVisibility(View.INVISIBLE);
               textViewWork.setVisibility(View.INVISIBLE);
               textViewRestEnd.setVisibility(View.VISIBLE);
               textViewRestEnd.setText(getResources().getText(R.string.end_string));
               FAB.setImageResource(R.drawable.ic_arrow_back_white_24dp);
               trainingEnded = true;
            }
            else if(Objects.equals(messageString, "update")){
                progressBar.setMax(msg.arg1);
                progressBar.setProgress(msg.arg2);
            }else if(Objects.equals(messageString, "pause")){
               trainingPaused = true;
               FAB.setImageResource(R.drawable.ic_play_arrow_white_24dp);
           }else{
                textViewSets.setText(messageString + "/" + interval.getSets().toString());
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        dbAdapter = new DataBaseAdapter(context);

        interval = (Interval) getIntent().getSerializableExtra("CURRENT_INTERVAL");

        backgroundWork = new BackgroundWork(this,interval,handler);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new IncomingCall(backgroundWork);

        setContentView(R.layout.while_workout_activity_material);

        textViewTime = (TextView) findViewById(R.id.textViewTime);
        textViewRestEnd = (TextView) findViewById(R.id.textViewRest);
        textViewSets = (TextView) findViewById(R.id.textViewSets);
        textViewWarmUp = (TextView) findViewById(R.id.textViewWarmUp);
        textViewWork = (TextView) findViewById(R.id.textViewWork);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        FAB = (FloatingActionButton) findViewById(R.id.fab_work);


        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(trainingEnded){
                    finish();
                }else if(!trainingPaused) {
                    backgroundWork.setRunningVarInOpposingState();
                    FAB.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                    trainingPaused = true;
                }else{
                    backgroundWork.setRunningVarInOpposingState();
                    FAB.setImageResource(R.drawable.ic_pause_white_24dp);
                    trainingPaused = false;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        backgroundWork.setRunningVarFalse();
        backgroundWork.kill();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_while_workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_show_trainings_while_workout){
            Interval [] intervals = dbAdapter.getTrainings();
            Intent intent = new Intent(context, SavedTrainingsActivity.class);
            intent.putExtra("TRAININGS", intervals);
            startActivity(intent);
        }

        finish();
        return super.onOptionsItemSelected(item);
    }
}
