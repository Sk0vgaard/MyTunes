/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.gui.model.SongModel;

/**
 *
 * @author gta1
 */
public class MyTunesController implements Initializable {

    @FXML
    private TableView<Playlist> tablePlaylists;
    @FXML
    private TableView<Song> tableSongs;
    @FXML
    private Label lblIsPlaying;
    @FXML
    private TextField txtSearch;
    @FXML
    private Slider sliderVolume;
    @FXML
    private TableColumn<Playlist, String> clmPlaylistName;
    @FXML
    private TableColumn<Playlist, Integer> clmPlaylistSongsAmount;
    @FXML
    private TableColumn<Playlist, String> clmPlaylistTotalDuration;
    @FXML
    private TableColumn<Song, String> clmSongTitle;
    @FXML
    private TableColumn<Song, String> clmSongArtist;
    @FXML
    private TableColumn<Song, String> clmSongDuration;
    @FXML
    private TableView<Song> tableCurrentPlaylist;
    @FXML
    private TableColumn<Song, String> clmCurrentPlaylistTrack;
    @FXML
    private TableColumn<Song, String> clmCurrentPlaylistTitle;

    private SongModel songModel;
    @FXML
    private TableColumn<Song, String> clmSongGenre;
    @FXML
    private Button btnAddSong;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        songModel = SongModel.getInstance();
        tableSongs.setItems(songModel.getSongs());
    }

    /*TODO ALH: We should be able to add to our Observable List in the model
    In order to do so a new modal window should pop-up  when we click the btnAddSong*/
 /*TODO ALH: In this window we should see TextFields to add information, possibly a combobox to select the category and finally a button to save the information
    When the new btnSaveSong is clicked a method should be called, preferably in a new controller for the newly created window
     */
    @FXML

    private void handleAddButton(ActionEvent event) throws IOException {
        Stage primStage = (Stage) txtSearch.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/NewEditSongView.fxml"));
        Parent root = loader.load();

        Stage editStage = new Stage();
        editStage.setScene(new Scene(root));

        editStage.initModality(Modality.WINDOW_MODAL);
        editStage.initOwner(primStage);

        editStage.show();
    }

    @FXML
    private void handleEditButton(ActionEvent event) throws IOException {

        Stage primStage = (Stage) txtSearch.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/NewEditSongView.fxml"));
        Parent root = loader.load();

        Stage editStage = new Stage();
        editStage.setScene(new Scene(root));

        editStage.initModality(Modality.WINDOW_MODAL);
        editStage.initOwner(primStage);

        editStage.show();
    }

    //TODO ALH: We should have a method that reacts on the play button
    //TODO ALH: It could be cool if the result of this method was also that the lblIsPlaying was also updated...
}
