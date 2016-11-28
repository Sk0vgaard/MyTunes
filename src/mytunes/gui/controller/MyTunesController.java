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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML
    private TableColumn<Song, String> clmSongGenre;
    @FXML
    private ImageView btnPlay;

    private final Image play = new Image(getClass().getResourceAsStream("/mytunes/assets/icons/play.png"));
    private final Image pause = new Image(getClass().getResourceAsStream("/mytunes/assets/icons/pause.png"));

    private SongModel songModel;

    private static final String IDLE_TEXT = "Enjoy your music!";
    private static final String IS_PLAYING = " is playing";
    private static final String IS_PAUSED = " is paused";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        songModel = SongModel.getInstance();

        btnPlay.setImage(play);

        //Add songs from the model and show them in the tableSongs
        clmSongTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        clmSongArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        clmSongGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        clmSongDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        tableSongs.setItems(songModel.getSongs());

        lblIsPlaying.setText(IDLE_TEXT);
    }

    @FXML
    private void handleSongTableButton(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();

        Stage primStage = (Stage) txtSearch.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/NewEditSongView.fxml"));
        Parent root = loader.load();

        Stage editStage = new Stage();
        editStage.setScene(new Scene(root));

        editStage.initModality(Modality.WINDOW_MODAL);
        editStage.initOwner(primStage);

        if (clickedButton.getText().equals("Add")) {
            editStage.show();
        } else {
            Song songToEdit = tableSongs.getSelectionModel().getSelectedItem();
            if (songToEdit != null) {
                NewEditSongController songController = loader.getController();
                songController.setTxtTitle(songToEdit.getTitle());
                songController.setTxtArtist(songToEdit.getArtist());
                songController.setTxtDuration(songToEdit.getDuration());
                songController.setComboGenre(songToEdit.getGenre());
                songController.setCurrentSong(songToEdit);
                editStage.show();
            }
        }
    }

    /**
     * Gets the current selected song and calls the play method of the
     * MusicPlaer. Changes the text of the play button appropriately.
     *
     * @param event
     */
    @FXML
    private void handlePlayButton() {
        Song selectedSong = tableSongs.getSelectionModel().getSelectedItem();
        if (btnPlay.getImage() == play) {
            if (selectedSong != null) {
                songModel.playSelectedSong(selectedSong);
                btnPlay.setImage(pause);
                lblIsPlaying.setText(selectedSong.getTitle() + IS_PLAYING);
            }
        } else {
            songModel.pausePlaying();
            btnPlay.setImage(play);
            lblIsPlaying.setText(songModel.getCurrentSongPlaying().getTitle() + IS_PAUSED);
        }
    }

    /**
     * Stop the song playing and sets the text of the play button to "Play".
     *
     * @param event
     */
    @FXML
    private void handleStopButton() {
        songModel.stopPlaying();
        btnPlay.setImage(play);
        lblIsPlaying.setText(IDLE_TEXT);
    }

    @FXML
    private void handleSearch() {
        String search = txtSearch.getText();
        if (!search.equals("")) {
            songModel.searchSong(search);
        }
    }

    @FXML
    private void handleClearSearch(ActionEvent event) {
        songModel.clearSearch();
        txtSearch.setText("");
    }
}
