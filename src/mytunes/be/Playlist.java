/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import java.util.Collections;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.bll.IDCreator;

public class Playlist {

    private final int id;
    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty amountOfSongs = new SimpleIntegerProperty();
    private final ObservableList<Song> songs;
    private StringProperty duration = new SimpleStringProperty();

    ;

    public Playlist(String name, int amountOfSongs, String duration) {
        this.id = IDCreator.createID();
        setName(name);
        setAmountOfSongs(amountOfSongs);
        setDuration(duration);
        songs = FXCollections.observableArrayList();
    }

    public int getId() {
        return id;
    }

    public StringProperty getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public IntegerProperty getAmountOfSongs() {
        return amountOfSongs;
    }

    public void setAmountOfSongs(int amountOfSongs) {
        this.amountOfSongs.set(id);
    }

    public StringProperty getDuration() {
        return duration;
    }

    public ObservableList<Song> getSongs() {
        return songs;
    }

    public void setDuration(String duration) {
        this.duration.set(duration);
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(int position) {
        songs.remove(position);
    }

    /**
     * Removes all songs from playlist
     */
    public void removeSongs() {
        songs.clear();
    }

    /**
     * Move song in playlist
     *
     * @param song
     * @param direction
     */
    public void moveSong(Song song, String direction) {
        int index = songs.indexOf(song);
        if (direction.equals("up")) {
            Collections.swap(songs, index, index - 1);
        } else {
            Collections.swap(songs, index, index + 1);
        }
    }

}
