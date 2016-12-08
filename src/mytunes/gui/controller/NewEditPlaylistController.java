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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mytunes.be.Playlist;
import mytunes.gui.model.PlaylistModel;

/**
 * FXML Controller class
 *
 * @author mathi
 */
public class NewEditPlaylistController implements Initializable {

    @FXML
    private TextField txtName;

    private Stage stage;

    private Playlist currentPlaylist = new Playlist("", "", "");
    private final PlaylistModel playlistModel = PlaylistModel.getInstance();
    private final MyTunesController mtController = MyTunesController.getInstance();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     *
     * @return txtName
     */
    public TextField getTxtName() {
        return txtName;
    }

    /**
     * Sets txtName
     *
     * @param txtName
     */
    public void setTxtName(String txtName) {
        this.txtName.setText(txtName);
    }

    /**
     * Sets currentPlaylist
     *
     * @param currentPlaylist
     */
    public void setCurrentPlaylist(Playlist currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {

        stage = (Stage) txtName.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void handleSaveButton(ActionEvent event) {
        if (!currentPlaylist.getName().get().equals("")) {
            currentPlaylist.setName(txtName.getText());
        } else {
            currentPlaylist.setName(txtName.getText());
            playlistModel.addPlaylist(currentPlaylist);
            mtController.getPlaylistTable().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            mtController.getPlaylistTable().getSelectionModel().selectLast();
            mtController.handleSelectPlaylist(null);

        }
        stage = (Stage) txtName.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

}
