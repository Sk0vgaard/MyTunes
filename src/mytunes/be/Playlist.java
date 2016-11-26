/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import mytunes.bll.IDCreator;

public class Playlist {

    private final int id;
    private String name;
    private int amountOfSongs;
    private String duration;

    public Playlist(String name, int amountOfSongs, String duration) {
        this.id = IDCreator.createID();
        this.name = name;
        this.amountOfSongs = amountOfSongs;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmountOfSongs() {
        return amountOfSongs;
    }

    public void setAmountOfSongs(int amountOfSongs) {
        this.amountOfSongs = amountOfSongs;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}
