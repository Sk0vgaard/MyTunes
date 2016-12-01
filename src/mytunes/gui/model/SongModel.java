/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.bll.FileManager;
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

    private static final String MOCK_PATH = System.getProperty("user.dir").replace('\\', '/') + "/src/mytunes/assets/mp3/";

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
        currentPlaylist = FXCollections.observableArrayList();
        playlists = FXCollections.observableArrayList();
        musicPlayer = MusicPlayer.getInstance();
        musicPlayer.setSongModel(this);
        savedSongs = new ArrayList<>();
        fileManager = new FileManager();
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
     * Creates some mockup songs
     */
    private void mockupSongs() {

        Song excited = new Song(
                "I'm So excited",
                "Pointer Sisters",
                "POP",
                "3.42");
        excited.setFileName(MOCK_PATH + "excited.mp3");

        Song beatIt = new Song(
                "Beat It",
                "Michael Jackson",
                "POP", "3.42");
        beatIt.setFileName(MOCK_PATH + "beatIt.mp3");

        Song bohemian = new Song(
                "Bohemian Rhapsody",
                "Queen",
                "POP",
                "6.06");
        bohemian.setFileName(MOCK_PATH + "bohemian.mp3");

        Song happyRock = new Song("BetterRock",
                "Lynyrd Skynyrd",
                "Rock",
                "4.30");
        happyRock.setFileName(MOCK_PATH + "alabama.mp3");

        Song baby = new Song(
                "Baby",
                "Justin Bieber",
                "POP",
                "2.5");
        baby.setFileName(MOCK_PATH + "baby.mp3");

        Song cc1 = new Song(
                "Voice of Truth",
                "Casting Crowns",
                "Christian",
                "3.25");
        cc1.setFileName(MOCK_PATH + "voiceOfTruth.mp3");

        Song cc2 = new Song(
                "What this world needs",
                "Casting Crowns",
                "Chritian",
                "3.25");
        cc2.setFileName(MOCK_PATH + "whatThisWorldNeeds.mp3");

        Song cc3 = new Song(
                "Does anybody hear her",
                "Casting Crowns",
                "Christian",
                "3.25");
        cc3.setFileName(MOCK_PATH + "doesAnybodyHearHer.mp3");

        Playlist mj = new Playlist("Michael fucking Jackson", "1", "3.42");
        Playlist cc = new Playlist("Casting Crowns", "3", "10");

        mj.getSongsInPlaylist().add(beatIt);
        cc.getSongsInPlaylist().add(cc1);
        cc.getSongsInPlaylist().add(cc2);
        cc.getSongsInPlaylist().add(cc3);

        Song testEnd = new Song(
                "TestEnd",
                "RandomGuy",
                "Not Really",
                "0.16");
        testEnd.setFileName(MOCK_PATH + "testEnd.mp3");

        Song testPiano = new Song(
                "TestPiano",
                "Random",
                "Nope",
                "0.19");
        testPiano.setFileName(MOCK_PATH + "piano.mp3");

        songs.add(testPiano);
        songs.add(excited);
        songs.add(beatIt);
        songs.add(bohemian);
        songs.add(happyRock);
        songs.add(baby);
        songs.add(cc1);
        songs.add(cc2);
        songs.add(cc3);
        songs.add(testEnd);

        playlists.add(mj);
        playlists.add(cc);
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
        ArrayList<Song> songsFromFile = musicDao.getSongsFromFile();
        if (!songsFromFile.isEmpty()) {
            songs.clear();
            songs.addAll(songsFromFile);
        }
    }

    /**
     * Load saved playlists
     *
     */
    public void loadSavedPlaylists() {
        ArrayList<Playlist> playlistsFromFile = musicDao.getPlaylistsFromFile();
        if (!playlistsFromFile.isEmpty()) {
            playlists.clear();
            playlists.addAll(playlistsFromFile);
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
}
