package com.dominik.intervaltimer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

/**
 * Created by dominik on 2016-08-21.
 */
public class BackgroundWork extends Thread {

    private Interval interval;
    private Context context;
    private static Handler handler;
    private Integer MEDIA_ID =  R.raw.bell;
    private Integer timeGoOff = 0;
    volatile static boolean running = true;



    BackgroundWork(Context context, Interval interval, Handler handler){
        this.context = context;
        this.interval = interval;
        this.handler = handler;
        this.start();
    }

    @Override
    public void run() {
        super.run();

        MediaPlayer mediaPlayer = MediaPlayer.create(context,MEDIA_ID);


         Integer timeOn = new Integer(interval.getTimeOn());
                Integer timeOff = new Integer(interval.getTimeOff());
                Integer warmUpTime = new Integer(interval.getWarmUpTime());
                Integer trainingTime = new Integer(interval.getTrainingTime());
                Integer actualSet = 1;

                Integer currentTimeOfCurrentSection = 0;
                Boolean timeOnBoolean = false, timeOffBoolean = false, warmUpBoolean = false, firstPlay = true;

                if(warmUpTime != 0){
                    currentTimeOfCurrentSection = warmUpTime;
                    sendMessageString("warm",handler, null,null);
                    warmUpBoolean = true;

                }else if(timeOn !=0){
                    currentTimeOfCurrentSection = timeOn;
                    sendMessageString("work",handler, null,null);
                    timeOnBoolean = true;

                }else  if(timeOff != 0){
                    currentTimeOfCurrentSection = timeOff;
                    sendMessageString("rest",handler, null,null);

                    timeOffBoolean = true;
                }


        sendMessageString(actualSet.toString(),handler, null,null);

    while (trainingTime > 0) {
        if (running) {
            sendMessageString(interval.getDurationString(currentTimeOfCurrentSection), handler, null, null);
            sendMessageString(interval.getDurationString(trainingTime) + " ", handler, null, null);

            if (firstPlay == true) {
                play(mediaPlayer, firstPlay);
                firstPlay = false;
            }

            sleep();

            currentTimeOfCurrentSection--;
            trainingTime--;

            if (trainingTime == 0) {
                sendMessageString("end", handler, null, null);
                sendMessageString("00:00:00", handler, null, null);
            } else if (currentTimeOfCurrentSection == 0 && warmUpBoolean == true) {
                warmUpBoolean = false;
                timeOnBoolean = true;
                currentTimeOfCurrentSection = timeOn;
                play(mediaPlayer, firstPlay);

                sendMessageString("work", handler, null, null);

            } else if (currentTimeOfCurrentSection == 0 && timeOnBoolean) {
                timeOnBoolean = false;
                timeOffBoolean = true;
                currentTimeOfCurrentSection = timeOff;
                play(mediaPlayer, firstPlay);

                sendMessageString("rest", handler, null, null);

            } else if (currentTimeOfCurrentSection == 0 && timeOffBoolean) {
                timeOffBoolean = false;
                timeOnBoolean = true;
                currentTimeOfCurrentSection = timeOn;
                actualSet++;
                play(mediaPlayer, firstPlay);

                sendMessageString(actualSet.toString(), handler, null, null);
                sendMessageString("work", handler, null, null);

            }
        }
    }
}

    public static void sendMessageString(String mes, Handler handler, Integer arg1, Integer arg2) {

        Message message = new Message();
        message.obj = mes;

      if(mes == "update"){
           message.arg1 = arg1;
           message.arg2 = arg2;
        }

        handler.sendMessage(message);
    }

    public void play(MediaPlayer mediaPlayer, Boolean firstPlay){

        if(firstPlay){
            mediaPlayer.start();
        }
        else {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
        }
    }

    Boolean firstUse = true;

    public void sleep(){
        try {

            Integer shortSleepTime = 10;

            if(firstUse){
                Thread.sleep(200);
                firstUse = false;
            }

            Thread.sleep(shortSleepTime);
            timeGoOff += shortSleepTime;

            sendMessageString("update",handler,interval.getTrainingTime() * 1000,timeGoOff);

            while( (timeGoOff % 1000) != 0){
                Thread.sleep(shortSleepTime);
                timeGoOff += shortSleepTime;

                sendMessageString("update",handler,interval.getTrainingTime() * 1000 ,timeGoOff);

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void setRunningVarInOpposingState() { running = !running; }
    public static void setRunningVarFalse(){running = false;  sendMessageString("pause",handler,null,null);}





}
