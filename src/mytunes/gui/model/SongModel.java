/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Song;
import mytunes.bll.FileManager;
import mytunes.bll.MusicPlayer;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

public class SongModel {

    private static SongModel instance;

    private final ObservableList<Song> songs;
    private final ObservableList<Song> currentPlaylist;

    private final ArrayList<Song> savedSongs;

    private final MusicPlayer musicPlayer;

    private final FileManager fileManager;

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
        musicPlayer = MusicPlayer.getInstance();
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

    public ObservableList<Song> getCurrentPlaylist() {
        return currentPlaylist;
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

        Song happyRock = new Song("HappyRock",
                "SomeArtist",
                "Rock",
                "1.45");
        happyRock.setFileName(MOCK_PATH + "happyrock.mp3");

        Song baby = new Song(
                "Baby",
                "Justin Bieber",
                "POP",
                "2.5");
        baby.setFileName(MOCK_PATH + "baby.mp3");

        songs.add(excited);
        songs.add(beatIt);
        songs.add(bohemian);
        songs.add(happyRock);
        songs.add(baby);

        currentPlaylist.add(beatIt);
        currentPlaylist.add(bohemian);
        currentPlaylist.add(happyRock);
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
        Song retrievedSong = null;
        try {
            retrievedSong = fileManager.openFile();
        } catch (IOException ex) {
            Logger.getLogger(SongModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(SongModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CannotReadException ex) {
            Logger.getLogger(SongModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TagException ex) {
            Logger.getLogger(SongModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ReadOnlyFileException ex) {
            Logger.getLogger(SongModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAudioFrameException ex) {
            Logger.getLogger(SongModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retrievedSong;
    }
}
