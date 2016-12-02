/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
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

    private MyTunesController mtController;

    private final ObservableList<Song> songs;
    private final ObservableList<Song> currentPlaylist;
    private final ObservableList<Playlist> playlists;

    private final ArrayList<Song> savedSongs;

    private final MusicPlayer musicPlayer;

    private final FileManager fileManager;

    private final MusicDAO musicDao = MusicDAO.getInstance();
    private final MathManager mathManager = MathManager.getInstance();

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
        musicPlayer.setSongModel(this);
        savedSongs = new ArrayList<>();
        fileManager = new FileManager();
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
     * @return 
     */
    public ArrayList<Song> getSongsAsAraryList()
    {
        ArrayList<Song> list = new ArrayList<>();
        for (Song song : songs)
        {
            list.add(song);
        }
        return list;
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
        for (Song song : playlist) {
            currentPlaylist.add(song);
        }
    }

    /**
     * Returns the songs in the observableList as ArrayList.
     *
     * @return
     */
    public ArrayList<Song> getCurrentPlaylistAsArrayList() {
        ArrayList<Song> playlist = new ArrayList<>();
        for (Song song : currentPlaylist) {
            playlist.add(song);
        }
        return playlist;
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
            if (savedSong.getTitle().get().toLowerCase().contains(searchString)) {
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
     * Sets the mtController so the SongModel has a reference to the
     * MyTunesController.
     *
     * @param mtController
     */
    public void setMyTunesController(MyTunesController mtController) {
        this.mtController = mtController;
    }

    /**
     * Gets the myTunesController.
     *
     * @return
     */
    public MyTunesController getMyTunesController() {
        return mtController;
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
     * Mute
     */
    public void mute() {
        musicPlayer.setVolume(0);
    }

    /**
     * Unmute
     *
     * @param lastValue
     */
    public void unmute(double lastValue) {
        musicPlayer.setVolume(lastValue);
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
     * @param song
     */
    public void addSong(Song song) {
        songs.add(song);
        saveSongs();
    }

    /**
     * Save the songs
     */
    private void saveSongs() {
        ArrayList<Song> songsToSave = new ArrayList<>(songs);
        musicDao.writeSongs(songsToSave);
    }

    /**
     * Save playlists
     */
    private void savePlaylists() {
        ArrayList<Playlist> playlistsToSave = new ArrayList<>(playlists);
        musicDao.writePlaylists(playlistsToSave);
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
            System.out.println("Sheit");
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
            System.out.println("Sheit");
        }
    }

    /**
     * Removes song
     *
     * @param songToDelete
     */
    public void deleteSong(Song songToDelete) {
        songs.remove(songToDelete);
        saveSongs();

    }

    /**
     * Removes playlist
     *
     * @param playlistToDelete
     */
    public void deletePlaylist(Playlist playlistToDelete) {
        playlists.remove(playlistToDelete);
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

    public void removeSongsFromCurrentPlaylist(ArrayList<Song> songsToDelete) {
        for (Song song : songsToDelete) {
            currentPlaylist.remove(song);
        }
    }

    /**
     * Gets the total duration of all songs as double.
     *
     * @return
     */
    public double getTotalDurationAllSongs() {
        double totalduration = 0;
//        for (Song song : songs) {
//            //Replace all "," with ".". If not, the program chrashes.
//            String durationString = song.getDuration().get().replace(",", ".");
//            totalduration += Double.parseDouble(durationString);
//        }
        totalduration = mathManager.totalDuration(getSongsAsAraryList());
        return totalduration;
    }

    /**
     * Updates the music player
     */
    public void updateSliderTime() {
        MusicPlayer.getPlayer().currentTimeProperty().addListener((Observable ov) -> {
            Duration time = MusicPlayer.getPlayer().getCurrentTime();
            Duration total = MusicPlayer.getPlayer().getTotalDuration();

            if (!mtController.sliderMusic.isValueChanging()
                    && total.greaterThan(Duration.ZERO)) {

                mtController.sliderMusic.setValue(time.toMillis() / total.toMillis() * 100);
            }
            String timeAsString = String.format("%.2f", time.toMinutes()).replaceAll(",", ".");

            mtController.lblTime.setText(timeAsString);
        });

        mtController.sliderMusic.valueChangingProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable ov) {
                MusicPlayer.getPlayer().seek(MusicPlayer.getPlayer().getTotalDuration()
                        .multiply(mtController.sliderMusic.getValue() / 100.0));
            }
        });
    }
}
