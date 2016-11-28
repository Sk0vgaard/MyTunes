/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

public class Song {

    private String title;
    private String artist;
    private String genre;
    private String duration;
    private String filePath;

    public Song(String title, String artist, String genre, String duration) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
    }
    
    /**
     * Gets the title of the song.
     * @return 
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Gets the artist of the song.
     * @return 
     */
    public String getArtist() {
        return artist;
    }
    
    /**
     * Gets the genre of the song.
     * @return 
     */
    public String getGenre() {
        return genre;
    }
    
    /**
     * Gets the duration of the song.
     * @return 
     */
    public String getDuration() {
        return duration;
    }
    
    /**
     * Sets the filePath of the song.
     * @param path 
     */
    public void setFilePath(String path) {
        this.filePath = path;
    }
    
    /**
     * Gets the filePath of the song.
     * @return 
     */
    public String getFilePatch()
    {
        return filePath;
    }

}
