/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mytunes.bll.IDCreator;

public class Song {

    private final int id;
    private final StringProperty title = new SimpleStringProperty();
    private String artist;
    private String category;
    private String duration;
    private String path;

    public Song(String title, String artist, String category, String duration, String path) {
        setTitle(title);
        this.artist = artist;
        this.category = category;
        this.duration = duration;
        this.path = path;
        id = IDCreator.createID();
    }

    /**
     *
     * @return title
     */
    public StringProperty getTitle() {
        return title;
    }

    /**
     *
     * @return artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     *
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     * @return duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     *
     * @return path to Song
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets the id of the Song
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     *
     * @param artist
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     *
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *
     * @param duration
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     *
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }
}
