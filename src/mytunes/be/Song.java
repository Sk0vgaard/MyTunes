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
        private double duration;
        
        
        

    /*TODO ALH: A song object should be able to hold information
    All the information needs to be assigned when a new Song object is "constructed"...
    We shoule also be able get the info
    Perhaps it could be useful to update the information at some point?*/

    public Song(String title, String artist, String genre, double duration)
    {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
    }

    public String getTitle()
    {
        return title;
    }

    public String getArtist()
    {
        return artist;
    }

    public String getGenre()
    {
        return genre;
    }

    public double getDuration()
    {
        return duration;
    }
    
    
    
    
}
