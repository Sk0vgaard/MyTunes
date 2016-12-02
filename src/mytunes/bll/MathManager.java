/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

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
    
    public double convertToMinutes(double numberToConvert)
    {
        int minutes = (int) numberToConvert;
        double seconds = numberToConvert % 1;
        seconds = seconds * 60 / 2700;
//        seconds /= 100;
        double duration = (double) minutes + seconds;
        return duration;
    }
}
