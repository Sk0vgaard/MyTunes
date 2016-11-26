/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Playlist;
import mytunes.be.Song;

public class PlaylistModel {

    private static PlaylistModel instance;

    public static PlaylistModel getInstance() {
        if (instance == null) {
            instance = new PlaylistModel();
        }
        return instance;
    }

    private final ObservableList<Playlist> playlists;

    private final ObservableList<Song> currentPlayList;

    private PlaylistModel() {
        playlists = FXCollections.observableArrayList();
        currentPlayList = FXCollections.observableArrayList();
        currentPlayList.add(new Song("Test", "Test", "", "", ""));

    }

    public ObservableList<Playlist> getPlaylists() {
        return playlists;
    }

    public ObservableList<Song> getCurrentPlayList() {
        return currentPlayList;
    }

    /**
     * Adds the parsed playlist to the ObservableList
     *
     * @param playlist
     */
    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    /**
     * Adds the parsed to the current playlist
     *
     * @param song
     */
    public void addToCurrentPlaylist(Song song) {
        currentPlayList.add(song);
    }

}
