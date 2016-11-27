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
import mytunes.bll.PlaylistManager;

public class PlaylistModel {

    private static PlaylistModel instance;

    private final PlaylistManager playlistManager;

    private final ObservableList<Playlist> playlists;

    private Playlist currentPlaylist;

    private final ObservableList<String> currentPlayListAsString;

    public static PlaylistModel getInstance() {
        if (instance == null) {
            instance = new PlaylistModel();
        }
        return instance;
    }

    private PlaylistModel() {
        playlists = FXCollections.observableArrayList();
        currentPlayListAsString = FXCollections.observableArrayList();
        playlistManager = new PlaylistManager();
        currentPlaylist = new Playlist("", 0, "0");

    }

    public ObservableList<Playlist> getPlaylists() {
        return playlists;
    }

    public ObservableList<String> getCurrentPlayList() {
        return currentPlayListAsString;
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
        currentPlaylist.addSong(song);
        currentPlayListAsString.add(currentPlaylist.getSongs().size() + ". " + song.getTitle());
    }

    /**
     * Remove selected song from playlist
     *
     * @param song
     */
    public void removeFromCurrentPlaylist(String song) {
        int position = currentPlayListAsString.indexOf(song);
        currentPlayListAsString.remove(song);
        currentPlaylist.removeSong(position);
    }

    /**
     * Create new playlisy
     */
    public void createPlaylist() {

    }

    /**
     * Start the current playlist
     */
    public void playPlaylist() {
        //TODO ALH: Finish this
    }

}
