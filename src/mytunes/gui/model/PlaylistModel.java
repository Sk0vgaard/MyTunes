/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Playlist;

public class PlaylistModel {

    private final ObservableList<Playlist> playlists;

    public PlaylistModel() {
        playlists = FXCollections.observableArrayList();
    }

    /**
     * Adds the parsed playlist to the ObservableList
     *
     * @param playlist
     */
    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

}
