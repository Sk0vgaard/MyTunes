/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mytunes.bll.IDCreator;

public class Playlist {

    private int id = 0;
    private final StringProperty name;
    private final StringProperty songs;
    private final StringProperty duration;

    private final ArrayList<Song> songsInPlaylist;

    public Playlist(String name, String songs, String duration) //String songs)
    {
        this.name = new SimpleStringProperty();
        this.songs = new SimpleStringProperty();
        this.duration = new SimpleStringProperty();
        songsInPlaylist = new ArrayList<>();
        id = IDCreator.createPlaylistId();

        setName(name);
        setSongs(songs);
        setDuration(duration);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setSongs(String songs) {
        this.songs.set(songs);
    }

    public void setDuration(String duration) {
        this.duration.set(duration);
    }

    public StringProperty getName() {
        return name;
    }

    public StringProperty getSongs() {
        return songs;
    }

    public StringProperty getDuration() {
        return duration;
    }

    public ArrayList<Song> getSongsInPlaylist() {
        return songsInPlaylist;
    }

    public void addSong(Song song) {
        songsInPlaylist.add(song);
    }

    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }

}
