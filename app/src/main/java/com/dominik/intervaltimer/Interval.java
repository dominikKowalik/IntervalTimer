package com.dominik.intervaltimer;

import java.io.Serializable;

/**
 * Created by dominik on 2016-08-23.
 */
public class Interval implements Serializable {

    public Integer getTimeOn() {
        return timeOn;
    }

    public void setTimeOn(Integer timeOn) {
        this.timeOn = timeOn  ;
    }

    public Integer getTimeOff() {
        return timeOff ;
    }

    public void setTimeOff(Integer timeOff) {
        this.timeOff = timeOff   ;
    }

    public Integer getSets() {
        return sets ;
    }

    public void setSets(Integer sets) {
        this.sets = sets  ;
    }


public Interval(){

}

    public Interval(Integer timeOff, Integer timeOn, Integer sets, Integer warmUpTime){
        this.timeOn = timeOn ;
        this.timeOff = timeOff;
        this.sets = sets;
        this.warmUpTime = warmUpTime;

        calculateTrainingTime();

    }

     String getDurationString() {

        int hours = trainingTime / 3600;
        int minutes = (trainingTime % 3600) / 60;
        int seconds = trainingTime % 60;

        return twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds);
    }

    String getDurationString(Integer time) {
        Integer seconds = time;

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds);
    }

    public void calculateTrainingTime(){
        trainingTime = (timeOn + timeOff)*sets + warmUpTime;
    }

    public Integer calculateTrainingTimeInt(){
      return   trainingTime = (timeOn + timeOff)*sets + warmUpTime;
    }


    private String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }


    public Integer getTrainingTime() {
        return trainingTime;
    }

    public void setTrainingTime(Integer trainingTime) {
        this.trainingTime = trainingTime;
    }

    private Integer trainingTime;
    private Integer timeOn;
    private Integer timeOff;
    private Integer sets;
    private Integer id;

    public void incrementTimeOn(){timeOn++;}
    public void decrementTimeOn(){timeOn--;}

    public void incrementTimeOff(){timeOff++;}
    public void decrementTimeOff(){timeOff--;}

    public void incrementWarmUpTime(){warmUpTime++;}
    public void decrementWarmUpTime(){warmUpTime--;}

    public void incrementSets(){sets++;}
    public void decrementSets(){sets--;}

    public void init(){
        timeOn = 0;
        timeOff = 0;
        sets = 0;
        warmUpTime = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Trening:" + getDurationString(calculateTrainingTimeInt()) + System.lineSeparator() + "Rozgrzewka:" +
                getDurationString(warmUpTime) +  System.lineSeparator()  + "Wysoka aktywnosc:" + getDurationString(timeOn)
                + System.lineSeparator() + "Odpoczynek:" +
                getDurationString(timeOff) + System.lineSeparator() + "Ilosc serii:" + sets ;
    }


    public Integer getWarmUpTime() {
        return warmUpTime;
    }

    public void setWarmUpTime(Integer warmUpTime) {
        this.warmUpTime = warmUpTime;
    }

    private Integer warmUpTime;

}
