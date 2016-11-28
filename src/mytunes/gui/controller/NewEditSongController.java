/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import mytunes.gui.model.SongModel;

/**
 * FXML Controller class
 *
 * @author Rasmus
 */
public class NewEditSongController implements Initializable
{
    private SongModel songModel;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtArtist;
    @FXML
    private ComboBox<String> comboGenre;
    @FXML
    private Button btnMore;
    @FXML
    private TextField txtDuration;
    @FXML
    private TextField txtFile;
    @FXML
    private Button btnChoose;
    @FXML
    private Button btnSave;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        songModel = SongModel.getInstance();
        comboGenre.setItems(FXCollections.observableArrayList(
                "Rock",
                "POP",
                "Jazz",
                "Opera",
                "Classical",
                "Dubstep",
                "Techno",
                "Country",
                "Hip Hop",
                "Soul",
                "Blues",
                "Reggie"
                
        ));
        comboGenre.setVisibleRowCount(6);
    }    

    @FXML
    private void handleMoreButton(ActionEvent event)
    {
    }

    @FXML
    private void handleChooseButton(ActionEvent event)
    {
    }

    @FXML
    private void handleSaveButton(ActionEvent event)
    {
    }
    
}
