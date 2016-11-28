/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer {

    private static MusicPlayer instance;

    private MediaPlayer myTunesPlayer;
    private boolean isPaused;
    private String currentSong;

    public static MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }
    
    private MusicPlayer()
    {
        isPaused = false;
        currentSong = "";
    }
    
    /**
     * Plays or pauses the song parsed depending if the son is currently played or not.
     * @param fileName of the song to be played/paused.
     * @return true if the play button need to be pause. False if it needs to be play.
     * @throws MediaException 
     */
    public boolean playSong(String fileName) throws MediaException
    {
        boolean changePlayToPause;
        //Checks if it's a new song that is selected.
        if(!currentSong.equals(fileName))
        {
            //Checks if myTunesPlayer is instantiated.
            if(myTunesPlayer != null)
            {
                //If the old song is playing, stop it.
                myTunesPlayer.stop();
            }
            //Pick the song to be played and put it in the myTunesPlayer.
            Media pick = new Media("file:///" + System.getProperty("user.dir").replace('\\', '/') + "/src/mytunes/assets/mp3/" + fileName);
            myTunesPlayer = new MediaPlayer(pick);
            myTunesPlayer.play();
            isPaused = false;
            changePlayToPause = true;
            //Storing what song is currently being played.
            currentSong = fileName;
        }
        //If it's not a new song selected and it's playing. Pause it.
        else if(!isPaused)
        {
            myTunesPlayer.pause();
            isPaused = true;
            changePlayToPause = false;
        }
        //If it's not a ew song selected and it's paused. Continue playing it.
        else
        {
            myTunesPlayer.play();
            isPaused = false;
            changePlayToPause = true;
        }
        return changePlayToPause;
    }
    
    /**
     * Stops the playing the current song.
     */
    public void stopSong()
    {
        myTunesPlayer.stop();
        isPaused = true;
    }
}
