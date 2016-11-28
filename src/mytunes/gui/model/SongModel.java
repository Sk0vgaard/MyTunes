/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Song;
import mytunes.bll.MusicPlayer;

public class SongModel {

    private static SongModel instance;

    private final ObservableList<Song> songs;

    private ArrayList<Song> savedSongs;

    private final MusicPlayer musicPlayer;

    //TODO ALH: We need to be able to retrieve the List, so that we can show it in the GUI
    //TODO ALH: We need a method to add songs to the List, so that our other awesome developers can call this method and add a new song!
    /**
     * If SongModel has not been instantiated, make a new instance off of it and
     * return it. If there already is an instance of SongModel, return that
     * instance.
     *
     * @return
     */
    public static SongModel getInstance() {
        if (instance == null) {
            instance = new SongModel();
        }
        return instance;
    }

    private SongModel() {
        songs = FXCollections.observableArrayList();
        musicPlayer = MusicPlayer.getInstance();
        savedSongs = new ArrayList<>();
        mockupSongs();
    }

    /**
     *
     * @return songs
     */
    public ObservableList<Song> getSongs() {
        return songs;
    }

    /**
     * Creates some mockup songs
     */
    private void mockupSongs() {
        Song song1 = new Song("Song", "SomeArtist", "Classical", "3.42");
        Song song2 = new Song("Song", "SomeArtist", "Classical", "3.42");
        Song song3 = new Song("Song", "SomeArtist", "Classical", "3.42");
        Song happyRock = new Song("HappyRock",
                "SomeArtist",
                "Rock",
                "1.45");
        Song baby = new Song(
                "Baby",
                "Justin Bieber",
                "POP",
                "2.5");
        happyRock.setFileName("happyrock.mp3");
        baby.setFileName("baby.mp3");

        songs.add(song1);
        songs.add(song2);
        songs.add(song3);
        songs.add(happyRock);
        songs.add(baby);
    }

    /**
     * Sends the song to the MusicPlayer
     *
     * @param song
     */
    public void playSelectedSong(Song song) {
        //Check if the MusicPlayer is currentplay at all
        if (musicPlayer.isPlaying()) {
            //If it is playing, then check if it is the same song we want to resume
            if (musicPlayer.getCurrentSong().equals(song)) {
                musicPlayer.resumeSong();
                //If not then play the newly parsed song
            } else {
                musicPlayer.playSong(song);
            }
            //If not just play the newly parsed song
        } else {
            musicPlayer.playSong(song);
        }
    }

    /**
     * Get current song playing
     *
     * @return
     */
    public Song getCurrentSongPlaying() {
        return musicPlayer.getCurrentSong();
    }

    /**
     * Pauses playing of current song in MusicPlayer
     */
    public void pausePlaying() {
        musicPlayer.pausePlaying();
    }

    /**
     * Stops playing the current song in MusicPlayer
     */
    public void stopPlaying() {
        if (musicPlayer.isPlaying()) {
            musicPlayer.stopPlaying();
        }
    }

    /**
     * Search for song
     *
     * @param searchString
     */
    public void searchSong(String searchString) {
        ArrayList<Song> songsFromSearch = new ArrayList<>();
        savedSongs.addAll(songs);
        songs.clear();
        for (Song savedSong : savedSongs) {
            if (savedSong.getTitle().toLowerCase().contains(searchString)) {
                songsFromSearch.add(savedSong);
            }
        }
        songs.addAll(songsFromSearch);
    }

    /**
     * Reset search
     */
    public void clearSearch() {
        if (!savedSongs.isEmpty()) {
            songs.clear();
            songs.addAll(savedSongs);
            savedSongs.clear();
        }
    }
}
