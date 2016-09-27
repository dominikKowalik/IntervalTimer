package com.dominik.intervaltimer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainPrimActivity extends AppCompatActivity {

    TextView textViewWork, textViewRest, textViewWarmUp, textViewSets;
    DataBaseAdapter dbAdapter;
    ImageButton restAdd,restRemove,workAdd,workRemove,warmUpAdd,warmUpRemove,setsAdd,setsRemove;
    final Context context = this;
    Handler mHandler;
    FloatingActionButton fab;
    Interval interval;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_prim);

        textViewRest = (TextView) findViewById(R.id.textViewRest);
        textViewWarmUp = (TextView) findViewById(R.id.textViewWarmUp);
        textViewWork = (TextView) findViewById(R.id.textViewWork);
        textViewSets = (TextView) findViewById(R.id.textViewSets);

        restAdd = (ImageButton) findViewById(R.id.restAdd);
        restRemove = (ImageButton) findViewById(R.id.restRemove);
        workAdd = (ImageButton) findViewById(R.id.workAdd);
        workRemove = (ImageButton) findViewById(R.id.workRemove);
        warmUpAdd = (ImageButton) findViewById(R.id.warmUpAdd);
        warmUpRemove = (ImageButton) findViewById(R.id.warmUpRemove);
        setsAdd = (ImageButton) findViewById(R.id.setsAdd);
        setsRemove = (ImageButton) findViewById(R.id.setsRemove);

        fab = (FloatingActionButton) findViewById(R.id.FAB_main);

        dbAdapter = new DataBaseAdapter(context);

        interval = new Interval();
        interval.init();

        setsRemove.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Runnable mAction = new Runnable() {
                    @Override
                    public void run() {
                        changeAndDisplay(interval,this,textViewSets,"setsd");
                    }
                };
                return changeTime(mAction,event);
            }
        });


       setsAdd.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               Runnable mAction = new Runnable() {
                   @Override
                   public void run() {
                       changeAndDisplay(interval,this,textViewSets,"sets");
                   }
               };
               return changeTime(mAction,event);
           }
       });



        warmUpAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Runnable mAction = new Runnable() {
                    @Override
                    public void run() {
                        changeAndDisplay(interval,this,textViewWarmUp,"warm");
                    }
                };
                return changeTime(mAction,event);
            }
        });




        warmUpRemove.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Runnable mAction = new Runnable() {
                    @Override
                    public void run() {
                        changeAndDisplay(interval,this,textViewWarmUp,"warmd");
                    }
                };
                return changeTime(mAction,event);
            }
        });



        workRemove.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Runnable mAction = new Runnable() {
                    @Override
                    public void run() {
                        changeAndDisplay(interval,this,textViewWork,"workd");
                    }
                };
                return changeTime(mAction,event);
            }
        });

        workAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Runnable mAction = new Runnable() {
                    @Override
                    public void run() {
                        changeAndDisplay(interval,this,textViewWork,"work");
                    }
                };
                return changeTime(mAction,event);

            }
        });

        restAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Runnable mAction = new Runnable() {
                    @Override
                    public void run() {
                        changeAndDisplay(interval,this,textViewRest,"off");
                    }
                };

                return changeTime(mAction,event);
            }
        });

        restRemove.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Runnable mAction = new Runnable() {
                    @Override
                    public void run() {
                        changeAndDisplay(interval,this,textViewRest,"offd");
                    }
                };
                return changeTime(mAction,event);
            }
        });

      textViewRest.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              workAdd.setVisibility(View.INVISIBLE);
              workRemove.setVisibility(View.INVISIBLE);
              warmUpAdd.setVisibility(View.INVISIBLE);
              warmUpRemove.setVisibility(View.INVISIBLE);
              setsRemove.setVisibility(View.INVISIBLE);
              setsAdd.setVisibility(View.INVISIBLE);

              restRemove.setVisibility(View.VISIBLE);
              restAdd.setVisibility(View.VISIBLE);
          }
      });


        textViewWork.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                warmUpAdd.setVisibility(View.INVISIBLE);
                warmUpRemove.setVisibility(View.INVISIBLE);
                restRemove.setVisibility(View.INVISIBLE);
                restAdd.setVisibility(View.INVISIBLE);
                setsRemove.setVisibility(View.INVISIBLE);
                setsAdd.setVisibility(View.INVISIBLE);
                workAdd.setVisibility(View.VISIBLE);
                workRemove.setVisibility(View.VISIBLE);
            }
        });

        textViewSets.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                warmUpAdd.setVisibility(View.INVISIBLE);
                warmUpRemove.setVisibility(View.INVISIBLE);
                restRemove.setVisibility(View.INVISIBLE);
                restAdd.setVisibility(View.INVISIBLE);
                workAdd.setVisibility(View.INVISIBLE);
                workRemove.setVisibility(View.INVISIBLE);

                setsRemove.setVisibility(View.VISIBLE);
                setsAdd.setVisibility(View.VISIBLE);
            }
        });

        textViewWarmUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                restRemove.setVisibility(View.INVISIBLE);
                restAdd.setVisibility(View.INVISIBLE);
                workAdd.setVisibility(View.INVISIBLE);
                workRemove.setVisibility(View.INVISIBLE);
                setsRemove.setVisibility(View.INVISIBLE);
                setsAdd.setVisibility(View.INVISIBLE);
                warmUpAdd.setVisibility(View.VISIBLE);
                warmUpRemove.setVisibility(View.VISIBLE);
            }
        });

        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                interval.calculateTrainingTime();
                Intent intent = new Intent(context, WhileWorkoutActivity.class);
                intent.putExtra("CURRENT_INTERVAL",interval);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.action_save_training){
            dbAdapter.open();

            if(dbAdapter.insert(interval)) {
                showText("Zapisano",this);
            } else {
                showText("Nie Zapisano",this);
            }
        }else if(id == R.id.action_show_trainings){

            Interval [] intervals = dbAdapter.getTrainings();

            Intent intent = new Intent(context, SavedTrainingsActivity.class);
            intent.putExtra("TRAININGS", intervals);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    public static void showText(String text, Context context){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public boolean changeTime(Runnable mAction, MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                if(mHandler != null){return true;}

                mHandler = new Handler();
                mHandler.postDelayed(mAction,100);

                break;

            case MotionEvent.ACTION_UP:
                if (mHandler == null) return true;

                mHandler.removeCallbacks(mAction);
                mHandler = null;

                break;
        }
        return true;
    }

    public void changeAndDisplay(Interval interval, Runnable runable, TextView textView, String type){

        if(mHandler != null){

            if(type.equals("off")) {
                interval.incrementTimeOff();
                textView.setText(interval.getDurationString(interval.getTimeOff()));
            }
            else if(type.equals("work")){
                interval.incrementTimeOn();
                textView.setText(interval.getDurationString(interval.getTimeOn()));
            }
            else if(type.equals("warm")) {
                interval.incrementWarmUpTime();
                textView.setText(interval.getDurationString(interval.getWarmUpTime()));
            }
            else if(type.equals("sets") && interval.getSets() != 99) {
                interval.incrementSets();
                textView.setText(interval.getSets().toString());
            }
            else if(type.equals("offd") && interval.getTimeOff() != 0) {
                interval.decrementTimeOff();
                textView.setText(interval.getDurationString(interval.getTimeOff()));
            }
            else if(type.equals("workd") && interval.getTimeOn() != 0){
                interval.decrementTimeOn();
                textView.setText(interval.getDurationString(interval.getTimeOn()));
            }
            else if(type.equals("warmd") && interval.getWarmUpTime() != 0) {
                interval.decrementWarmUpTime();
                textView.setText(interval.getDurationString(interval.getWarmUpTime()));
            }
            else if(type.equals("setsd") && interval.getSets() != 0) {
                interval.decrementSets();
                textView.setText(interval.getSets().toString());
            }

            mHandler.postDelayed(runable,70);
        }
    }

}
