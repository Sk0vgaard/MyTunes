/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

public class PlaylistManager {

    private static final String IS_PLAYING_STRING = " is currently playing";

    /**
     * Get the string for what is currently playing
     *
     * @param title
     * @return
     */
    public String getCurrentlyPlayingAsString(String title) {
        return title + IS_PLAYING_STRING;
    }

}
