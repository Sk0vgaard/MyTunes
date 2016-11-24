/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mytunes.be.Song;
import mytunes.gui.model.SongModel;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class SongController implements Initializable {

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtArtist;
    @FXML
    private TextField txtDuration;
    @FXML
    private ComboBox<String> comboCategory;

    private final ObservableList<String> categories;

    private final SongModel songModel = SongModel.getInstance();
    @FXML
    private TextField txtPath;
    @FXML
    private Button btnAddSong;

    public SongController() {
        this.categories = FXCollections.observableArrayList(
                "Classical",
                "POP",
                "Funk",
                "Rock");
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboCategory.setItems(categories);
        comboCategory.setVisibleRowCount(4);
    }

    /**
     * Adds a song to the model
     */
    @FXML
    private void handleAddSong() {
        Song newSong = new Song(
                txtTitle.getText(),
                txtArtist.getText(),
                comboCategory.getSelectionModel().getSelectedItem(),
                txtDuration.getText(),
                txtPath.getText());
        songModel.addSong(newSong);

        Stage modalStage = (Stage) btnAddSong.getScene().getWindow();
        modalStage.close();

    }

    /**
     * Gets the path to the selected file
     *
     * @param event
     */
    @FXML
    private void handlePath(ActionEvent event) {
        //TODO ALH: Open file dialog and get the path to the file on selection
    }

}
