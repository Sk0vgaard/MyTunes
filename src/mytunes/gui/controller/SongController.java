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
import javafx.fxml.FXMLLoader;
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
    public TextField txtTitle;
    @FXML
    public TextField txtArtist;
    @FXML
    public TextField txtDuration;
    @FXML
    public ComboBox<String> comboCategory;

    private final ObservableList<String> categories;

    private final SongModel songModel = SongModel.getInstance();
    @FXML
    private TextField txtPath;
    @FXML
    private Button btnSaveSong;
    @FXML
    private Button btnAddCategory;
    @FXML
    private Button btnChooseSong;
    @FXML
    private TextField txtNewCategory;

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
    private void handleSaveSong() {
        if (txtTitle.getText().isEmpty()) {
            Song newSong = new Song(
                    txtTitle.getText(),
                    txtArtist.getText(),
                    comboCategory.getSelectionModel().getSelectedItem(),
                    txtDuration.getText(),
                    txtPath.getText());
            songModel.saveSong(newSong);
        } else {
            //TODO ALH: Fix this shit!
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/MyTunesController.fxml"));
            MyTunesController myTunesController = loader.getController();
            Song selectedSong = myTunesController.tableSongs.getSelectionModel().getSelectedItem();
            songModel.saveSong(selectedSong);

        }

        Stage modalStage = (Stage) btnSaveSong.getScene().getWindow();
        modalStage.close();

    }

    /**
     * Adds the category to the ObservableList
     */
    @FXML
    private void handleAddCategory() {
        categories.add(txtNewCategory.getText());
        txtNewCategory.setText("");
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
