/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mytunes.bll.IDCreator;
import mytunes.bll.MathManager;

public class Playlist implements Serializable {
    
    private MathManager mathManager = MathManager.getInstance();

    private int id = 0;
    private String name;
    private String songs;
    private String duration;

    private final ArrayList<Song> songsInPlaylist;

    public Playlist(String name, String songs, String duration) //String songs)
    {
        this.name = name;
        this.songs = songs;
        this.duration = duration;
        songsInPlaylist = new ArrayList<>();
        id = IDCreator.createPlaylistId();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSongs(String songs) {
        this.songs = songs;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public StringProperty getName() {
        StringProperty propertyName = new SimpleStringProperty(name);
        return propertyName;
    }

    public StringProperty getSongs() {
        songs = songsInPlaylist.size() + "";
        StringProperty propertySongs = new SimpleStringProperty(songs);
        return propertySongs;
    }

    public StringProperty getDuration() {
        duration = mathManager.totalDuration(songsInPlaylist) + "";
        StringProperty propertyDuration = new SimpleStringProperty(duration);
        return propertyDuration;
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
