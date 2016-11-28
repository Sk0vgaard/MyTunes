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
    private final StringProperty artist = new SimpleStringProperty();
    ;
    private final StringProperty category = new SimpleStringProperty();
    ;
    private final StringProperty duration = new SimpleStringProperty();
    ;
    private final StringProperty path = new SimpleStringProperty();

    ;

    public Song(String title, String artist, String category, String duration, String path) {
        setTitle(title);
        setArtist(artist);
        setCategory(category);
        setDuration(duration);
        setPath(path);
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
    public StringProperty getArtist() {
        return artist;
    }

    /**
     *
     * @return category
     */
    public StringProperty getCategory() {
        return category;
    }

    /**
     *
     * @return duration
     */
    public StringProperty getDuration() {
        return duration;
    }

    /**
     *
     * @return path to Song
     */
    public StringProperty getPath() {
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
        this.artist.set(artist);
    }

    /**
     *
     * @param category
     */
    public void setCategory(String category) {
        this.category.set(category);
    }

    /**
     *
     * @param duration
     */
    public void setDuration(String duration) {
        this.duration.set(duration);
    }

    /**
     *
     * @param path
     */
    public void setPath(String path) {
        this.path.set(path);
    }
}
