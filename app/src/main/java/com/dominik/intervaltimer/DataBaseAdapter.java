package com.dominik.intervaltimer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dominik on 2016-08-27.
 */
public class DataBaseAdapter {


    public static final String dataBaseName = "interval_data";
    public static final String dataBaseTableName = "training_table";

    public static final String KEY_ID ="id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT,";
    public static final String TRAINING_TIME = "TRAININGTIME";
    public static final String TIME_ON = "TIMEON" ;
    public static final String TIME_OFF = "TIMEOFF" ;
    public static final String SETS= "SETS";
    public static final String WARM_UP_TIME = "WARMUPTIME";
    public static final String INTEGERSTRING = " INTEGER,";
    public static final String INTEGERSTRING2 = " INTEGER";


    public static final String DEBUG_TAG ="INFO";

    DataBaseHelper dbHelper;
    Context context;
    SQLiteDatabase db;

    public DataBaseAdapter(Context context){
        this.context = context;
        open();
    }

    public DataBaseAdapter open(){
        dbHelper = new DataBaseHelper(context);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
        return this;
    }

    public void close(){
        db.close();
    }

    private Cursor getCursor(){
        String[] columns = { KEY_ID, TRAINING_TIME, TIME_ON, TIME_OFF,SETS,WARM_UP_TIME };
       return db.query(dataBaseTableName, columns,null,null,null,null,null);
    }

    public Interval [] getTrainings(){
        Cursor cursor = getCursor();
        Interval[] interval = new Interval[cursor.getCount()];

        int i = 0;

        while(cursor.moveToNext()){

            interval[i] = new Interval();

            interval[i].setId(cursor.getInt(0));
            interval[i].setTrainingTime(cursor.getInt(1));
            interval[i].setTimeOn(cursor.getInt(2));
            interval[i].setTimeOff(cursor.getInt(3));
            interval[i].setSets(cursor.getInt(4));
            interval[i].setWarmUpTime(cursor.getInt(5));

            i++;
        }

        return interval;
    }

    public Interval getTraining(Integer id){
        Cursor cursor = getCursor();
        Interval interval = null;

        if(cursor.moveToPosition(id)){

            interval  = new Interval();

            interval.setId(cursor.getInt(0));
            interval.setTrainingTime(cursor.getInt(1));
            interval.setTimeOn(cursor.getInt(2));
            interval.setTimeOff(cursor.getInt(3));
            interval.setSets(cursor.getInt(4));
            interval.setWarmUpTime(cursor.getInt(5));
        }
        return interval;
    }



    public Boolean insert(Object obj) {

        Boolean ifSuccess = false;

        if (obj instanceof Interval) {
            Interval interval = (Interval) obj;
            Long returnedValue;

            returnedValue = db.insert(dataBaseTableName, null, getContentValues(interval));

            if (returnedValue != -1) {
                ifSuccess = true;
            }
        }
        return ifSuccess;
    }

    public void delete(Integer id){
        try {
            db.delete(dataBaseTableName, KEY_ID + " = ?", new String[]{id.toString()});

        }
        catch (SQLException ex){

        }
    }

    public void deleteAll(){
        db.delete(dataBaseTableName,null,null);
    }

    public void updateRecord(Object obj){

        ContentValues contentValues;
        Boolean ifSuccess = false;
        Integer id;


        if(obj instanceof Interval){
            Interval interval = (Interval) obj;
            id = interval.getId();
            contentValues = getContentValues(interval);

            String[] arguments = { id + "" };

            db.update(dataBaseTableName, contentValues ,"id=?", arguments);

        }


    }



    public ContentValues getContentValues(Interval interval){

        ContentValues contentValues = new ContentValues();

        contentValues.put(TRAINING_TIME, interval.getTrainingTime());
        contentValues.put(TIME_ON, interval.getTimeOn());
        contentValues.put(TIME_OFF, interval.getTimeOff());
        contentValues.put(SETS, interval.getSets());
        contentValues.put(WARM_UP_TIME, interval.getWarmUpTime());

        return contentValues;
    }





  private static class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DB_CREATE =
            "CREATE TABLE " + dataBaseTableName + "(" +
                    KEY_ID + " " + ID_OPTIONS +
                  TRAINING_TIME + INTEGERSTRING + TIME_ON + INTEGERSTRING +
                    TIME_OFF + INTEGERSTRING +  SETS + INTEGERSTRING + WARM_UP_TIME + INTEGERSTRING2 +
                    ");";

    private static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + dataBaseTableName;



    public DataBaseHelper(Context context) {
        super(context, dataBaseName, null,4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }


    }







}


