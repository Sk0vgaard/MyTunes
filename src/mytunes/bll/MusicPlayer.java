/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.IOException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer {

    private static MusicPlayer instance;

    private MediaPlayer myTunesPlayer;

    public static MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }
    
    public void playSong(String fileName) throws MediaException
    {
        Media pick = new Media("file:///" + System.getProperty("user.dir").replace('\\', '/') + "/src/mytunes/assets/mp3/" + fileName);
        myTunesPlayer = new MediaPlayer(pick);
        myTunesPlayer.play();        
    }
    
    public void stopSong()
    {
        myTunesPlayer.stop();
    }
    //ALH ALH: I guess it would be nice to be able to stop the song, even though it may be Bieber...
}
