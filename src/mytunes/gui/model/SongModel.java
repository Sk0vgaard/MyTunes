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
        mockupSongs();
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
     * Deletes the selected song
     *
     * @param selectedSong
     */
    public void deleteSong(Song selectedSong) {
        for (Song song : songs) {
            if (song.getTitle().equals(selectedSong.getTitle())) {
                songs.remove(song);
                break;
            }
        }
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
        Song song1 = new Song("Song One", "SomeArtist", "Classical", "7.52");
        Song song2 = new Song("Song Two", "SomeArtist", "Rock", "5.52");
        Song song3 = new Song("Song Three", "SomeArtist", "POP", "3.52");
        Song song4 = new Song("Song Four", "SomeArtist", "Funk", "2.52");

        songs.add(song1);
        songs.add(song2);
        songs.add(song3);
        songs.add(song4);
    }

}
