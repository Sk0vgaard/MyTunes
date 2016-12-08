/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.FileNotFoundException;
import mytunes.dal.MusicDAO;

public class IDCreator {
    
    private static MusicDAO musicDAO = MusicDAO.getInstance();

    private static int songId = 0;
    private static int playListId = 0;
    
    private int lastPlayListId;

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
     * @throws java.io.FileNotFoundException
     */
    public static int createPlaylistId() throws FileNotFoundException{
        if(musicDAO.isIdFileThere())
        {
            playListId = musicDAO.getIdFile();
        }
        playListId++;
        musicDAO.saveIdFile(playListId);
        return playListId;
    }

}
