/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import com.sun.deploy.ui.UIFactory;
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
        int minutes = 0;
        int seconds = 0;
        for (Song song : list) {
            String[] placeHolderString = song.getDuration().get().split(":");
            minutes += Integer.parseInt(placeHolderString[0]);
            seconds += Integer.parseInt(placeHolderString[1]);
        }

        if (seconds >= A_MINUTE) {
            minutes += seconds / A_MINUTE;
            seconds = seconds % A_MINUTE;
        }
                
        double totalDuration = (double) minutes + (double) seconds / (double) A_MINUTE;
        totalDuration = convertToMinutes(totalDuration);
        String stringDuration = String.format("%.2f", totalDuration);
        
        if(totalDuration >= A_MINUTE)
        {
            double placeHolder = totalDuration / A_MINUTE;
            int place2Holder = (int) placeHolder;
            stringDuration = place2Holder + "," + stringDuration;
        }
        
        return stringDuration;
    }
}
