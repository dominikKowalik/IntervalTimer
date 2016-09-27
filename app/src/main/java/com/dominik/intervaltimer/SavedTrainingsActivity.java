package com.dominik.intervaltimer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class SavedTrainingsActivity extends AppCompatActivity {


    RecyclerView mRecyclerView;
    MyAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DataBaseAdapter dbAdapter;
    String[] strings;
    Boolean visibility = false;
    FloatingActionButton FAB;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_trainings);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        FAB = (FloatingActionButton) findViewById(R.id.FAB_saved);
        mRecyclerView.setHasFixedSize(true);

        Interval [] intervals = (Interval[]) getIntent().getSerializableExtra("TRAININGS");
        strings = convertToString(intervals);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(strings, visibility, dbAdapter);
        mRecyclerView.setAdapter(mAdapter);

        dbAdapter = new DataBaseAdapter(this);

        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        MainPrimActivity.showText(getResources().getString(R.string.saved_acitvity_text), this);
    }

    public void end(){
        finish();
    }

    public String[] convertToString(Interval [] intervals){
        String[] strings1 = new String[intervals.length];

        for(int i=0; i < intervals.length; i++){
            strings1[i] = intervals[i].toString();
        }

        return strings1;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder{

             TextView mTextView;
             CardView mCardView;
             ImageButton imageButton;


            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.info_text);
                mCardView = (CardView) v.findViewById(R.id.my_recycler_view);
                imageButton = (ImageButton) v.findViewById(R.id.imageButton);


                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DataBaseAdapter dbAdapter = new DataBaseAdapter(context);

                        Interval interval = dbAdapter.getTraining(getAdapterPosition());
                        interval.calculateTrainingTime();
                        Intent intent = new Intent(context, WhileWorkoutActivity.class);
                        intent.putExtra("CURRENT_INTERVAL", interval);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }

        public String[] mDataSet;
        public Boolean ifVisible;
        public DataBaseAdapter dbAdapter;


        public MyAdapter(String[] myDataSet, Boolean ifVisible, DataBaseAdapter dbAdapter){
           mDataSet =  myDataSet;
            this.ifVisible = ifVisible;
            this.dbAdapter = dbAdapter;



        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v =   LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view_layout, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTextView.setText(mDataSet[position]);

            if(ifVisible == true){
                holder.imageButton.setVisibility(View.VISIBLE);
            }else {
                holder.imageButton.setVisibility(View.INVISIBLE);
            }





        }

        @Override
        public int getItemCount() {
            return mDataSet.length;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_saved_trainings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.action_delete){
            dbAdapter.deleteAll();
            mAdapter = new MyAdapter(strings,visibility = false, dbAdapter);
            mRecyclerView.setAdapter(mAdapter);
        }

/*
        if(id == R.id.action_delete && visibility == false){
         //   dbAdapter.deleteAll();
            strings = convertToString(dbAdapter.getTrainings());
            mAdapter = new MyAdapter(strings,visibility = true, dbAdapter);
            mRecyclerView.setAdapter(mAdapter);
        }else if(id == R.id.action_delete && visibility == true){
            strings = convertToString(dbAdapter.getTrainings());
            mAdapter = new MyAdapter(strings,visibility = false, dbAdapter);
            mRecyclerView.setAdapter(mAdapter);
        }*/
        return super.onOptionsItemSelected(item);
    }


}
