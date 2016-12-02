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
        seconds /= 60;
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

        if (seconds >= 60) {
            minutes += seconds / 60;
            seconds = seconds % 60;
        }
        double totalDuration = (double) minutes + ((double) seconds / 60.0);
        totalDuration = convertToMinutes(totalDuration);
        String durationAsString = String.valueOf(totalDuration);
        return durationAsString;

    }
}
