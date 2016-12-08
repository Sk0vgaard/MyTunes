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
import mytunes.be.Song;
import mytunes.bll.FileManager;
import mytunes.bll.MathManager;
import mytunes.bll.MusicPlayer;
import mytunes.dal.MusicDAO;
import mytunes.gui.controller.MyTunesController;

public class SongModel {

    private static SongModel instance;

    private final MyTunesController mtController;

    private final ObservableList<Song> songs;

    private final ObservableList<Song> savedSongs;

    private final ObservableList<Song> songsFromSearch;

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
        songsFromSearch = FXCollections.observableArrayList();
        musicPlayer = MusicPlayer.getInstance();
        musicDao = MusicDAO.getInstance();
        mathManager = MathManager.getInstance();
        musicPlayer.setSongModel(this);
        savedSongs = FXCollections.observableArrayList();
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
     * Returns all the songs in an ArrayList.
     *
     * @return
     */
    public ArrayList<Song> getSongsAsAraryList() {
        ArrayList<Song> songsAsArrayList = new ArrayList<>(songs);
        return songsAsArrayList;
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
        boolean hasSong = false;
        savedSongs.addAll(songs);
        for (Song savedSong : savedSongs) {
            if (savedSong.getTitle().get().toLowerCase().contains(searchString)
                    || savedSong.getGenre().get().toLowerCase().contains(searchString)) {
                for (Song song : songsFromSearch) {
                    if (song.getTitle().get().equals(savedSong.getTitle().get())) {
                        hasSong = true;
                    }
                }
                if (!hasSong) {
                    songsFromSearch.add(savedSong);
                }
            }
        }
        songs.clear();
        songs.addAll(songsFromSearch);
    }

    /**
     * Reset search
     */
    public void clearSearch() {
        loadSavedSongs();
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
     * Shuffles all the songs
     */
    public void shuffleSongs() {
        Collections.shuffle(songs);
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
     * Removes song
     *
     * @param songsToDelete
     */
    public void deleteSong(ObservableList<Song> songsToDelete) {
        songs.removeAll(songsToDelete);
        saveSongs();

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
}
