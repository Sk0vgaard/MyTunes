/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.bll.FileManager;
import mytunes.bll.MathManager;
import mytunes.bll.MusicPlayer;
import mytunes.dal.MusicDAO;
import mytunes.gui.controller.MyTunesController;

public class SongModel {

    private static SongModel instance;

    private int playlistID;

    private final MyTunesController mtController;

    private final ObservableList<Song> songs;
    private final ObservableList<Song> currentPlaylist;
    private final ObservableList<Playlist> playlists;

    private final ArrayList<Song> savedSongs;

    private final MusicPlayer musicPlayer;

    private FileManager fileManager;

    private final MusicDAO musicDao;
    private final MathManager mathManager;

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
        currentPlaylist = FXCollections.observableArrayList();
        playlists = FXCollections.observableArrayList();
        musicPlayer = MusicPlayer.getInstance();
        musicDao = MusicDAO.getInstance();
        mathManager = MathManager.getInstance();
        musicPlayer.setSongModel(this);
        savedSongs = new ArrayList<>();
        fileManager = new FileManager();
        mtController = MyTunesController.getInstance();
    }

    /**
     *
     * @return songs
     */
    public ObservableList<Song> getSongs() {
        return songs;
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
     * Returns all the songs in an ArrayList.
     *
     * @return
     */
    public ArrayList<Song> getSongsAsAraryList() {
        ArrayList<Song> songsAsArrayList = new ArrayList<>(songs);
        return songsAsArrayList;
    }

    /**
     * Returns playlists in the Observable list
     *
     * @return
     */
    public ArrayList<Playlist> getPlaylistsAsArrayList() {
        ArrayList<Playlist> playlistsAsArrayList = new ArrayList<>(playlists);
        return playlistsAsArrayList;
    }

    public ObservableList<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     * Sends the song to the MusicPlayer
     *
     * @param song
     */
    public void playSelectedSong(Song song) {
        //Check if the MusicPlayer is current playing at all
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
        trackTime();
    }

    /**
     * Check if music player is playing
     *
     * @return
     */
    public boolean isMusicPlayerPlaying() {
        return musicPlayer.isPlaying();
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
     * Replays the song
     */
    public void replaySong() {
        if (musicPlayer.isPlaying()) {
            musicPlayer.replaySong();
        }
    }

    /**
     * Search for song from parsed string
     *
     * @param searchString
     */
    public void searchSong(String searchString) {
        ArrayList<Song> songsFromSearch = new ArrayList<>();
        savedSongs.addAll(songs);
        songs.clear();
        for (Song savedSong : savedSongs) {
            if (savedSong.getTitle().get().toLowerCase().contains(searchString)
                    || savedSong.getGenre().get().toLowerCase().contains(searchString)) {
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

    /**
     * Find song on drive
     *
     * @return
     */
    public Song getSongFromFile() {
        return fileManager.openFile();
    }

    /**
     * Add song from drag
     *
     * @param files
     */
    public void addFilesFromDrag(List<File> files) {
        ArrayList<File> songsToAdd = fileManager.getSongFilesFromDrag(files);
        for (File file : songsToAdd) {
            fileManager = new FileManager();
            fileManager.getMetaData(file);
            addSong(fileManager.getSong());
        }
    }

    /**
     * Calls the playNextSong method from the MyTunesController.
     *
     * @throws IOException
     */
    public void playNextSong() throws IOException {
        mtController.playNextSong();
    }

    /**
     * Switch sound level on music player
     *
     * @param value
     */
    public void switchVolume(double value) {
        musicPlayer.setVolume(value);
    }

    /**
     * Shuffle the current playlist
     */
    public void shuffleCurrentPlaylist() {
        Collections.shuffle(currentPlaylist);
    }

    /**
     * Shuffles all the songs
     */
    public void shuffleSongs() {
        Collections.shuffle(songs);
    }

    /**
     * Add song to playlist
     *
     * @param song
     */
    public void addSongToPlaylist(Song song) {
        currentPlaylist.add(song);
        for (Playlist playlist : playlists) {
            if (playlist.getId() == playlistID) {
                playlist.addSong(song);
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
     * Adds the parsed song
     *
     * @param parsedSong
     */
    public void addSong(Song parsedSong) {
        boolean exists = false;
        //Loop through songs and check if new one is there
        for (Song song : songs) {
            if (song.getTitle().get().equals(parsedSong.getTitle().get())) {
                exists = true;
            }
        }
        //If another one with the same title isn't there then we add!
        if (exists == false) {
            songs.add(parsedSong);
            saveSongs();
        }
        mtController.updateInfo();
    }

    /**
     * Save the songs
     */
    private void saveSongs() {
        musicDao.writeSongs(getSongsAsAraryList());
    }

    /**
     * Save playlists
     */
    public void savePlaylists() {
        musicDao.writePlaylists(getPlaylistsAsArrayList());
    }

    /**
     * Load saved songs
     */
    public void loadSavedSongs() {
        if (musicDao.isSongsThere()) {
            ArrayList<Song> songsFromFile = musicDao.getSongsFromFile();
            if (!songsFromFile.isEmpty()) {
                songs.clear();
                songs.addAll(songsFromFile);
            }
        } else {
            System.out.println("Sheit songs.data isn't there!");
        }
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
     * Removes song
     *
     * @param songsToDelete
     */
    public void deleteSong(ObservableList<Song> songsToDelete) {
        songs.removeAll(songsToDelete);
        saveSongs();

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
     * Gets the total duration of all songs as double.
     *
     * @return
     */
    public String getTotalDurationAllSongs() {
        String totalduration;
        totalduration = mathManager.totalDuration(getSongsAsAraryList());
        return totalduration;
    }

    /**
     * Updates the music player
     */
    public void trackTime() {
        musicPlayer.trackTime();
    }

    /**
     * Sends time change to music player
     *
     * @param time
     */
    public void setNewTime(Double time) {
        musicPlayer.setNewTime(time);
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
