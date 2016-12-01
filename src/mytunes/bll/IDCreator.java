/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

public class IDCreator {

    private static int songId = 0;
    private static int playListId = 0;

    /**
     * Increases the working song id and returns the value
     *
     * @return
     */
    public static int createSongId() {
        songId++;
        return songId;
    }

    /**
     * Increases the working playlist id and returns the value
     *
     * @return
     */
    public static int createPlaylistId() {
        playListId++;
        return playListId;
    }

}
