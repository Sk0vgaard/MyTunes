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
import mytunes.gui.model.PlaylistModel;
import mytunes.gui.model.SongModel;

/**
 *
 * @author gta1
 */
public class MyTunesController implements Initializable {

    @FXML
    private TableView<Playlist> tablePlaylists;
    @FXML
    private ListView<String> listPlaylist;
    @FXML
    public TableView<Song> tableSongs;
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
    private Button btnSearch;
    @FXML
    private Button btnAddSong;
    @FXML
    private Button btnEditSong;
    @FXML
    private Button btnDeleteSong;
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
    private final PlaylistModel playListModel = PlaylistModel.getInstance();
    private Stage primStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clmSongTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        clmSongArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        clmSongCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        clmSongDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        tableSongs.setItems(songModel.getSongs());

        listPlaylist.setItems(playListModel.getCurrentPlayList());

        clmPlaylistName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmPlaylistSongsAmount.setCellValueFactory(new PropertyValueFactory<>("amountOfSongs"));
        clmPlaylistTotalDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        tablePlaylists.setItems(playListModel.getPlaylists());

    }

    /**
     * Adds a new song to the model
     */
    @FXML
    private void handleAddSong() throws IOException {
        primStage = (Stage) btnAddSong.getScene().getWindow();
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

    /**
     * Edit the selected song
     */
    @FXML
    private void handleEditSong() throws IOException {
        primStage = (Stage) btnAddSong.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/Song.fxml"));
        Parent root = loader.load();

        SongController songController = loader.getController();
        Song songToEdit = tableSongs.getSelectionModel().getSelectedItem();
        songController.txtTitle.setText(songToEdit.getTitle());
        songController.txtArtist.setText(songToEdit.getArtist());
        songController.txtDuration.setText(songToEdit.getDuration());
        songController.setCurrentSong(songToEdit);

        Stage stageSongView = new Stage();
        stageSongView.setScene(new Scene(root));
        stageSongView.initModality(Modality.WINDOW_MODAL);
        stageSongView.initOwner(primStage);
        stageSongView.show();
    }

    /**
     * Sends the song to the myTunesPlayer
     */
    @FXML
    private void handlePlaySong() {
        if (tableSongs.getSelectionModel().getSelectedItem() == null) {
            playListModel.playPlaylist();
        } else {
            Song selectedSong = tableSongs.getSelectionModel().getSelectedItem();
            songModel.playSelectedSong(selectedSong);
        }
    }

    /**
     * Stop the playing of songs
     */
    @FXML
    private void handleStopPlayer() {
        songModel.stopMediaPlayer();
    }

    /**
     * Adds selected song to playlist
     */
    @FXML
    private void handleAddSongToPlaylist() {
        playListModel.addToCurrentPlaylist(tableSongs.getSelectionModel().getSelectedItem());
    }

    /**
     * Moves the selected song up in the playlist
     *
     * @param event
     */
    @FXML
    private void handleMoveSongUp(ActionEvent event) {
        playListModel.moveSongUp(listPlaylist.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleMoveSongDown(ActionEvent event) {
    }

    @FXML
    private void handleRemoveSongFromList(ActionEvent event) {
        playListModel.removeFromCurrentPlaylist(listPlaylist.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleNewPlaylist(ActionEvent event) throws IOException {
        primStage = (Stage) btnAddSong.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/Playlist.fxml"));
        Parent root = loader.load();

        Stage stagePlaylistView = new Stage();
        stagePlaylistView.setScene(new Scene(root));
        stagePlaylistView.initModality(Modality.WINDOW_MODAL);
        stagePlaylistView.initOwner(primStage);
        stagePlaylistView.show();
    }

    @FXML
    private void handleEditPlaylist(ActionEvent event) throws IOException {
        primStage = (Stage) btnAddSong.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/Playlist.fxml"));
        Parent root = loader.load();

        PlaylistController playlistController = loader.getController();
        Playlist selectedPlaylist = tablePlaylists.getSelectionModel().getSelectedItem();
        playlistController.setPlaylistName(selectedPlaylist.getName());
        playlistController.setSelectedPlaylist(selectedPlaylist);

        Stage stagePlaylistView = new Stage();
        stagePlaylistView.setScene(new Scene(root));
        stagePlaylistView.initModality(Modality.WINDOW_MODAL);
        stagePlaylistView.initOwner(primStage);
        stagePlaylistView.show();
    }

    @FXML
    private void handleDeletePlaylist(ActionEvent event) {
        playListModel.deletePlaylist(tablePlaylists.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleSelectedPlaylist() {
        playListModel.showSelectedPlayList(tablePlaylists.getSelectionModel().getSelectedItem());
    }
}
