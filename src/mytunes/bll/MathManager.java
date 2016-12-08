/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.Serializable;
import java.util.ArrayList;
import mytunes.be.Song;

/**
 *
 * @author Rasmus
 */
public class MathManager implements Serializable {
    
    private static final int A_MINUTE = 60;

    public static MathManager instance;

    public static MathManager getInstance() {
        if (instance == null) {
            instance = new MathManager();
        }
        return instance;
    }

    /**
     * Convert a double to minutes and seconds.
     *
     * @param numberToConvert
     * @return
     */
    public double convertToMinutes(double numberToConvert) {
        int minutes = (int) numberToConvert;
        double seconds = numberToConvert % 1;
        seconds *= 3600;
        seconds /= A_MINUTE;
        seconds /= 100;
        double duration = (double) minutes + seconds;
        return duration;
    }

    /**
     * Gets the totalDuration as minutes and seconds.
     *
     * @param list
     * @return
     */
    public String totalDuration(ArrayList<Song> list) {
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        //Goes through all songs and add their duration.
        for (Song song : list) {
            String[] placeHolderString = song.getDuration().get().split(":");
            if(placeHolderString.length > 2)
            {
                hours += Integer.parseInt(placeHolderString[0]);
                minutes += Integer.parseInt(placeHolderString[1]);
                seconds += Integer.parseInt(placeHolderString[2]);
            }
            else
            {
                minutes += Integer.parseInt(placeHolderString[0]);
                seconds += Integer.parseInt(placeHolderString[1]);
            }
        }
        //If seconds is above 60, divide by 60 and add that to minutes.
        if (seconds >= A_MINUTE) { 
            minutes += seconds / A_MINUTE;
            seconds = seconds % A_MINUTE;
        }
        //Combine minutes and seconds and then convert it to actual minutes and seconds.        
        double totalDuration = (double) minutes + (double) seconds / (double) A_MINUTE;
        totalDuration = convertToMinutes(totalDuration);
        //Checks if there is more than 60 minutes and add that to hours.
        if(totalDuration > A_MINUTE)
        {
            double placeholder = totalDuration / A_MINUTE;            
            hours += (int) placeholder;
            totalDuration = totalDuration % A_MINUTE;
        }
        //Make a String that holds minutes and seconds and only allowed to show to decimals.
        String stringDuration = String.format("%.2f", totalDuration);
        //Checks if minutes is less than 10 and add a zero infront of it for good looking.
        if(totalDuration < 10)
        {
            stringDuration = 0 + stringDuration;
        }
        //Checks if duration is over an hour. If so, add the hours to the duration.
        if(hours > 0)
        {            
            stringDuration = hours + "," + stringDuration;
        }
        //Replace all "," with ":" for good looking.
        stringDuration = stringDuration.replace(",", ":");
        
        return stringDuration;
    }
}
