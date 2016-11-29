/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mytunes.be.Song;
import mytunes.gui.model.SongModel;

/**
 * FXML Controller class
 *
 * @author Rasmus
 */
public class NewEditSongController implements Initializable {

    private SongModel songModel = SongModel.getInstance();
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

    private Song currentSong = new Song("title", "artist", "genre", "duration");

    ObservableList<String> genreList = FXCollections.observableArrayList();

    public NewEditSongController() {
        //TODO
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
                "Reggae"
        ));
        comboGenre.setVisibleRowCount(6);
        comboGenre.getSelectionModel().selectFirst();
    }

    @FXML

    private void handleMoreButton(ActionEvent event) throws IOException {
        Stage primStage = (Stage) txtTitle.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/MoreGenreView.fxml"));
        Parent root = loader.load();

        Stage editStage = new Stage();
        editStage.setScene(new Scene(root));

        editStage.initModality(Modality.WINDOW_MODAL);
        editStage.initOwner(primStage);

        editStage.show();

    }

    @FXML
    private void handleChooseButton(ActionEvent event) {
        songModel.openFileDialog();
    }

    @FXML
    private void handleSaveButton(ActionEvent event) throws InvocationTargetException {
        currentSong.setTitle(txtTitle.getText());
        currentSong.setArtist(txtArtist.getText());
        currentSong.setGenre(comboGenre.getValue());
        currentSong.setDuration(txtDuration.getText());
        currentSong.setFileName(txtFile.getText());

        songModel.getSongs().add(currentSong);

        // get a handle to the stage
        Stage stage = (Stage) txtTitle.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void setTxtTitle(String newString) {
        this.txtTitle.setText(newString);
    }

    public void setTxtArtist(String newString) {
        this.txtArtist.setText(newString);
    }

    public void setTxtDuration(String newString) {
        this.txtDuration.setText(newString);
    }

    public void setTxtFile(String newString) {
        this.txtFile.setText(newString);
    }

    public void setCurrentSong(Song songToEdit) {
        currentSong = songToEdit;
    }

    public void setComboGenre(String comboGenre) {
        this.comboGenre.getSelectionModel().select(comboGenre);
    }

}
