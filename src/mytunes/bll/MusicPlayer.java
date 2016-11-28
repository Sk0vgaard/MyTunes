/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

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

    //TODO ALH: Here we should be able to play a song parsed to a play method
    //ALH ALH: I guess it would be nice to be able to stop the song, even though it may be Bieber...
}
