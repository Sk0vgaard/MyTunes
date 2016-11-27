/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mytunes.be.Playlist;
import mytunes.gui.model.PlaylistModel;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class PlaylistController implements Initializable {

    @FXML
    private TextField txtName;

    @FXML
    private Button btnSave;

    private final PlaylistModel playlistModel = PlaylistModel.getInstance();

    private Playlist selectedPlaylist;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * Sets the name of the playlist name field
     *
     * @param name
     */
    public void setPlaylistName(String name) {
        txtName.setText(name);
    }

    public void setSelectedPlaylist(Playlist selectedPlaylist) {
        this.selectedPlaylist = selectedPlaylist;
    }

    @FXML
    private void handleSave(ActionEvent event) {
        if (selectedPlaylist == null) {
            playlistModel.createPlaylist(txtName.getText());
        } else {
            selectedPlaylist.setName(txtName.getText());
            Playlist lastPlaylist = playlistModel.getPlaylists().get(playlistModel.getPlaylists().size() - 1);
            if (lastPlaylist.getName().equals("")) {
                playlistModel.deletePlaylist(lastPlaylist);
            } else {
                playlistModel.createPlaylist("");
            }
        }

        Stage modalStage = (Stage) btnSave.getScene().getWindow();
        modalStage.close();
    }

}
