/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import java.io.Serializable;
import java.text.DecimalFormat;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mytunes.bll.IDCreator;
import mytunes.bll.MathManager;

public class Song implements Serializable {

    private int id = 0;
    private String title;
    private String artist;
    private String genre;
    private String duration;
    private String fileName;
    
    private MathManager mathManager;

    public Song(String title, String artist, String genre, String duration) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        id = IDCreator.createSongId();
        mathManager = MathManager.getInstance();        
    }

    /**
     * Gets the title of the song.
     *
     * @return
     */
    public StringProperty getTitle() {
        StringProperty propertyTitle = new SimpleStringProperty(title);
        return propertyTitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * Gets the artist of the song.
     *
     * @return
     */
    public StringProperty getArtist() {
        StringProperty propertyArtist = new SimpleStringProperty(artist);
        return propertyArtist;
    }

    /**
     * Gets the genre of the song.
     *
     * @return
     */
    public StringProperty getGenre() {
        StringProperty propertyGenre = new SimpleStringProperty(genre);
        return propertyGenre;
    }

    /**
     * Gets the duration of the song.
     *
     * @return
     */
    public StringProperty getDuration() {
        StringProperty propertyDuration = new SimpleStringProperty(duration);
        return propertyDuration;
    }

    /**
     * Sets the fileName of the song.
     *
     * @param path
     */
    public void setFileName(String path) {
        this.fileName = path;
    }

    /**
     * Gets the fileName of the song.
     *
     * @return
     */
    public StringProperty getFileName() {
        StringProperty propertyFilename = new SimpleStringProperty(fileName);
        return propertyFilename;
    }

    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }

}
