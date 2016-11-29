/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Song {

    private final StringProperty title;
    private final StringProperty artist;
    private final StringProperty genre;
    private final StringProperty duration;
    private final StringProperty fileName;

    public Song(String title, String artist, String genre, String duration) {
        this.title = new SimpleStringProperty();
        this.artist = new SimpleStringProperty();
        this.genre = new SimpleStringProperty();
        this.duration = new SimpleStringProperty();
        this.fileName = new SimpleStringProperty();
        setTitle(title);
        setArtist(artist);
        setGenre(genre);
        setDuration(duration);
    }

    /**
     * Gets the title of the song.
     *
     * @return
     */
    public StringProperty getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setArtist(String artist) {
        this.artist.set(artist);
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public void setDuration(String duration) {
        this.duration.set(duration);
    }

    /**
     * Gets the artist of the song.
     *
     * @return
     */
    public StringProperty getArtist() {
        return artist;
    }

    /**
     * Gets the genre of the song.
     *
     * @return
     */
    public StringProperty getGenre() {
        return genre;
    }

    /**
     * Gets the duration of the song.
     *
     * @return
     */
    public StringProperty getDuration() {
        return duration;
    }

    /**
     * Sets the fileName of the song.
     *
     * @param path
     */
    public void setFileName(String path) {
        this.fileName.set(path);
    }

    /**
     * Gets the fileName of the song.
     *
     * @return
     */
    public StringProperty getFileName() {
        return fileName;
    }

}
