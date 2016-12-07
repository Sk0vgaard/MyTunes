/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.bll.MathManager;
import mytunes.dal.MusicDAO;
import mytunes.gui.controller.MyTunesController;

public class PlaylistModel {

    private static PlaylistModel instance;

    private final ObservableList<Song> currentPlaylist;
    private final ObservableList<Playlist> playlists;

    private int playlistID;

    private final MathManager mathManager;
    private final MyTunesController mtController;
    private final MusicDAO musicDao;

    public static PlaylistModel getInstance() {
        if (instance == null) {
            instance = new PlaylistModel();
        }
        return instance;
    }

    private PlaylistModel() {
        currentPlaylist = FXCollections.observableArrayList();
        playlists = FXCollections.observableArrayList();
        mathManager = MathManager.getInstance();
        mtController = MyTunesController.getInstance();
        musicDao = MusicDAO.getInstance();
    }

    /**
     * Returns the currentPlaylist.
     *
     * @return
     */
    public ObservableList<Song> getCurrentPlaylist() {
        return currentPlaylist;
    }

    /**
     * Updates the currentPlaylist with the given arrayList.
     *
     * @param playlist
     */
    public void updateCurrentPlaylist(ArrayList<Song> playlist) {
        currentPlaylist.clear();
        currentPlaylist.addAll(playlist);
    }

    /**
     * Returns the songs in the observableList as ArrayList.
     *
     * @return
     */
    public ArrayList<Song> getCurrentPlaylistAsArrayList() {
        ArrayList<Song> playlistAsArrayList = new ArrayList<>(currentPlaylist);
        return playlistAsArrayList;
    }

    /**
     * Returns playlists in the Observable list as an arraylist
     *
     * @return
     */
    public ArrayList<Playlist> getPlaylistsAsArrayList() {
        ArrayList<Playlist> playlistsAsArrayList = new ArrayList<>(playlists);
        return playlistsAsArrayList;
    }

    /**
     * Get the playlists
     *
     * @return
     */
    public ObservableList<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     * Shuffle the current playlist
     */
    public void shuffleCurrentPlaylist() {
        Collections.shuffle(currentPlaylist);
    }

    /**
     * Add song to playlist
     *
     * @param newSong
     */
    public void addSongToPlaylist(Song newSong) {
        boolean hasSong = false;
        for (Song song : currentPlaylist) {
            if (song.getId() == newSong.getId()) {
                hasSong = true;
            }
        }
        if (!hasSong) {
            currentPlaylist.add(newSong);
            for (Playlist playlist : playlists) {
                if (playlist.getId() == playlistID) {
                    playlist.addSong(newSong);
                }
            }
        }
        savePlaylists();
    }

    /**
     * Add song to songs
     *
     * @param playlist
     */
    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
        savePlaylists();
    }

    /**
     * Save playlists
     */
    public void savePlaylists() {
        musicDao.writePlaylists(getPlaylistsAsArrayList());
    }

    /**
     * Load saved playlists
     *
     */
    public void loadSavedPlaylists() {
        if (musicDao.isPlaylistsThere()) {
            ArrayList<Playlist> playlistsFromFile = musicDao.getPlaylistsFromFile();
            if (!playlistsFromFile.isEmpty()) {
                playlists.clear();
                playlists.addAll(playlistsFromFile);
            }
        } else {
            System.out.println("Sheit playlist.data isn't there!");
        }
    }

    /**
     * Removes playlist
     *
     * @param playlistsToDelete
     */
    public void deletePlaylist(ObservableList<Playlist> playlistsToDelete) {
        playlists.removeAll(playlistsToDelete);
        currentPlaylist.clear();
        mtController.updateInfo();
        savePlaylists();
    }

    /**
     * Deletes parsed song from selected playlist
     *
     * @param idPlaylist
     * @param songsToRemoveFromPlaylist
     */
    public void deleteFromPlaylist(int idPlaylist, ObservableList<Song> songsToRemoveFromPlaylist) {
        for (Playlist playlist : playlists) {
            if (playlist.getId() == idPlaylist) {
                playlist.getSongsInPlaylist().removeAll(songsToRemoveFromPlaylist);
            }
        }
        currentPlaylist.removeAll(songsToRemoveFromPlaylist);
        mtController.updateInfo();
        savePlaylists();
    }

    /**
     * Sets the current playlist id
     *
     * @param playlistID
     */
    public void setPlaylistID(int playlistID) {
        this.playlistID = playlistID;
    }

    /**
     * Gets the duration of the playlist
     *
     * @return
     */
    public String getDurationOfPlaylist() {
        String duration;
        duration = mathManager.totalDuration(getCurrentPlaylistAsArrayList());
        return duration;
    }

    /**
     * Gets the current playlist as a String
     *
     * @return
     */
    public String getCurrentPlaylistAsString() {
        String currentPlaylistAsString = "";
        ArrayList<String> artists = new ArrayList<>();
        for (Song song : this.currentPlaylist) {
            if (!artists.contains(song.getArtist().get())) {
                artists.add(song.getArtist().get());
                currentPlaylistAsString += song.getArtist().get() + ". ";
            }
        }
        return currentPlaylistAsString;
    }

}
