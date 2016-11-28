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

    public static MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    private MusicPlayer() {
    }

    /**
     * Plays or pauses the song parsed depending if the son is currently played
     * or not.
     *
     * @param fileName of the song to be played/paused.
     * @throws MediaException
     */
    public void playSong(Song song) throws MediaException {
        //Pick the song to be played and put it in the myTunesPlayer.
        Media pick = new Media("file:///" + System.getProperty("user.dir").replace('\\', '/') + "/src/mytunes/assets/mp3/" + song.getFileName());
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
