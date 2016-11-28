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

    private final Playlist currentPlaylist;

    public static PlaylistModel getInstance() {
        if (instance == null) {
            instance = new PlaylistModel();
        }
        return instance;
    }

    private PlaylistModel() {
        playlists = FXCollections.observableArrayList();
        playlistManager = new PlaylistManager();
        currentPlaylist = new Playlist("", 0, "0");

    }

    public ObservableList<Playlist> getPlaylists() {
        return playlists;
    }

    public Playlist getCurrentPlayList() {
        return currentPlaylist;
    }

    /**
     * Adds the parsed to the current playlist
     *
     * @param song
     */
    public void addToCurrentPlaylist(Song song) {
        currentPlaylist.addSong(song);
    }

    /**
     * Remove selected song from playlist
     *
     * @param song
     */
    public void removeFromCurrentPlaylist(Song song) {
        int position = currentPlaylist.getSongs().indexOf(song);
        currentPlaylist.removeSong(position);
    }

    /**
     * Move song up on current playlist
     *
     * @param selectedSong
     * @param direction
     */
    public void moveSong(Song selectedSong, String direction) {
        currentPlaylist.moveSong(selectedSong, direction);
    }

    /**
     * Create new playlist
     *
     * @param name
     */
    public void createPlaylist(String name) {
        Playlist newPlayList = new Playlist(name, currentPlaylist.getSongs().size(), "");
        for (Song song : currentPlaylist.getSongs()) {
            newPlayList.addSong(song);
        }
        //TODO ALH: Set total duration
        playlists.add(newPlayList);
    }

    /**
     * Show selected playlist
     *
     * @param playlist
     */
    public void showSelectedPlayList(Playlist playlist) {
        //TODO ALH: Create this
    }

    /**
     * Delete selected playlist
     *
     * @param playlist
     */
    public void deletePlaylist(Playlist playlist) {
        playlists.remove(playlist);
    }

    /**
     * Start the current playlist
     */
    public void playPlaylist() {
        //TODO ALH: Finish this
    }

}
