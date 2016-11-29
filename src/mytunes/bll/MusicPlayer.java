/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import mytunes.be.Song;

public class MusicPlayer {

    private static MusicPlayer instance;

    private MediaPlayer myTunesPlayer;

    private boolean isPlaying = false;

    private Song currentSong;

    private Media pick;

    public static MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    /**
     * Plays or pauses the song parsed depending if the son is currently played
     * or not.
     *
     * @param song to be played.
     * @throws MediaException
     */
    public void playSong(Song song) throws MediaException {
        //Pick the song to be played and put it in the myTunesPlayer.
        pick = new Media("file:///" + song.getFileName().get().replace('\\', '/'));
        myTunesPlayer = new MediaPlayer(pick);
        myTunesPlayer.play();
        isPlaying = true;
        currentSong = song;
    }

    /**
     * Pauses the current song
     */
    public void pausePlaying() {
        myTunesPlayer.pause();
    }

    /**
     * Resume current song
     */
    public void resumeSong() {
        myTunesPlayer.play();
    }

    /**
     * Stops the current song
     */
    public void stopPlaying() {
        myTunesPlayer.stop();
        isPlaying = false;
    }

    /**
     * Restarts the current song
     */
    public void replaySong() {
        myTunesPlayer.stop();
        myTunesPlayer.play();
    }

    /**
     *
     * @return
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    public Song getCurrentSong() {
        return currentSong;
    }
}
