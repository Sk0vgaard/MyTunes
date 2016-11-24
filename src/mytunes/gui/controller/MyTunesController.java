/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private ListView<Song> listPlaylist;
    @FXML
    private TableView<Song> tableSongs;
    @FXML
    private Label lblIsPlaying;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnPrevious;
    @FXML
    private Button btnNext;
    @FXML
    private Slider sliderVolume;
    @FXML
    private Button btnDeletePlaylist;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnMoveSongUp;
    @FXML
    private Button btnMoveSongDown;
    @FXML
    private Button btnRemoveFromList;
    @FXML
    private Button btnAddSong;
    @FXML
    private Button btnEditSong;
    @FXML
    private Button btnDeleteSong;
    @FXML
    private Button btnAddToList;
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
    private TableColumn<Song, String> clmSongCategory;
    @FXML
    private TableColumn<Song, String> clmSongDuration;

    private final SongModel songModel = SongModel.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clmSongTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        clmSongArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        clmSongCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        clmSongDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        tableSongs.setItems(songModel.getSongs());
    }

    /**
     * Adds a new song to the model
     */
    @FXML
    private void handleAddSong() throws IOException {
        Stage primStage = (Stage) btnAddSong.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/Song.fxml"));
        Parent root = loader.load();

        Stage stageSongView = new Stage();
        stageSongView.setScene(new Scene(root));
        stageSongView.initModality(Modality.WINDOW_MODAL);
        stageSongView.initOwner(primStage);
        stageSongView.show();
    }

    /**
     * Deletes the selected song from the model
     */
    @FXML
    private void handleDeleteSong() {
        Song selectedSong = tableSongs.getSelectionModel().getSelectedItem();
        songModel.deleteSong(selectedSong);
    }
}
