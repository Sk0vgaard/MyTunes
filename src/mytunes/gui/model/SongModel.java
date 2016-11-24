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

    private final ObservableList<Song> songs;

    public SongModel() {
        songs = FXCollections.observableArrayList();
    }

    /**
     * Adds the parsed song to the ObservableList of songs
     *
     * @param newSong
     */
    public void addSong(Song newSong) {
        songs.add(newSong);
    }

    /**
     *
     * @return songs
     */
    public ObservableList<Song> getSongs() {
        return songs;
    }

}
