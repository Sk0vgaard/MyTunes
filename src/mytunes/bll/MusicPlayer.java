/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import javafx.scene.media.Media;
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

    /**
     * Plays the parsed song
     *
     * @param songToPlay
     */
    public void play(Media songToPlay) {
        myTunesPlayer = new MediaPlayer(songToPlay);
        myTunesPlayer.play();
    }

    /**
     * Stops the selected song
     */
    public void stop() {
        myTunesPlayer.stop();
    }

}
