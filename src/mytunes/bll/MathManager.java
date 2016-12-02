/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.util.ArrayList;
import mytunes.be.Song;

/**
 *
 * @author Rasmus
 */
public class MathManager
{
    public static MathManager instance;
    
    public static MathManager getInstance()
    {
        if(instance == null)
        {
            instance = new MathManager();
        }
        return instance;
    }
    
    /**
     * Convert a double to minutes and seconds.
     * @param numberToConvert
     * @return 
     */
    public double convertToMinutes(double numberToConvert)
    {
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
     * @param list
     * @return 
     */
    public double totalDuration(ArrayList<Song> list)
    {
        int minutes = 0;
        double seconds = 0;
        for (Song song : list)
        {
            String placeHolderString = song.getDuration().get().replace(",", ".");
            double placeHolder = Double.parseDouble(placeHolderString);
            minutes += (int) placeHolder;
            seconds += placeHolder % 1;
        }
        seconds /= 60;
        double totalDuration = (double) minutes + seconds;
        return totalDuration;
        
    }
}
