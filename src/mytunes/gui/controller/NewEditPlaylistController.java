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
import mytunes.gui.model.SongModel;

/**
 * FXML Controller class
 *
 * @author mathi
 */
public class NewEditPlaylistController implements Initializable
{

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    @FXML
    private TextField txtName;

    private Playlist currentPlaylist = new Playlist("", "", "");

    private SongModel songModel = SongModel.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    public TextField getTxtName()
    {
        return txtName;
    }

    public void setTxtName(String txtName)
    {
        this.txtName.setText(txtName);

    }

    public void setCurrentPlaylist(Playlist currentPlaylist)
    {
        this.currentPlaylist = currentPlaylist;
    }

    @FXML
    private void handleCalcelButton(ActionEvent event)
    {
        // get a handle to the stage
        Stage stage = (Stage) txtName.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void handleSaveButton(ActionEvent event)
    {
        if (!currentPlaylist.getName().get().equals(""))
        {
            currentPlaylist.setName(txtName.getText());
        } else
        {
            currentPlaylist.setName(txtName.getText());
            songModel.addPlaylist(currentPlaylist);
        }

        // get a handle to the stage
        Stage stage = (Stage) txtName.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

}
