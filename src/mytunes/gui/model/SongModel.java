/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Song;

public class SongModel {

    private static SongModel instance;

    private final ObservableList<Song> songs;

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
        Song song4 = new Song("Song", "SomeArtist", "Classical", "3.42");
        Song baby = new Song(
                "Baby",
                "Justin Bieber",
                "POP",
                "2.5");
        baby.setFileName("baby.mp3");

        songs.add(song1);
        songs.add(song2);
        songs.add(song3);
        songs.add(song4);
        songs.add(baby);
    }

    //TODO ALH: We should have a method to play the selected song
}
