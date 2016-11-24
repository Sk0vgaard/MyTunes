/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

public class Song {

    private final String title;
    private final String artist;
    private final String category;
    private final String duration;
    private final String path;

    public Song(String title, String artist, String category, String duration, String path) {
        this.title = title;
        this.artist = artist;
        this.category = category;
        this.duration = duration;
        this.path = path;
    }

    /**
     *
     * @return title
     */
    public String getTitle() {
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

}
