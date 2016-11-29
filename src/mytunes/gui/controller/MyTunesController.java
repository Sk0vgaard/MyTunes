/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

    TableView.TableViewSelectionModel<Song> selectedView;
    @FXML
    private ImageView btnAddSong11;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        songModel = SongModel.getInstance();

        btnPlay.setImage(play);

        //Add songs from the model and show them in the tableSongs
        clmSongTitle.setCellValueFactory(i -> i.getValue().getTitle());
        clmSongArtist.setCellValueFactory(i -> i.getValue().getArtist());
        clmSongGenre.setCellValueFactory(i -> i.getValue().getGenre());
        clmSongDuration.setCellValueFactory(i -> i.getValue().getDuration());
        tableSongs.setItems(songModel.getSongs());

        //Add songto current playlist
        clmCurrentPlaylistTitle.setCellValueFactory(i -> i.getValue().getTitle());
        tableCurrentPlaylist.setItems(songModel.getCurrentPlaylist());

        lblIsPlaying.setText(IDLE_TEXT);
        selectedView = tableSongs.getSelectionModel(); //Setting the default view to tableSongs.

        //Adds a listener to tableSongs.
        tableSongs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {
            @Override
            public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
                if (newValue != null) //If newValue (new selection in the model) is not null.
                {
                    //Clears the tableCurrentPlaylist selection.
                    tableCurrentPlaylist.getSelectionModel().clearSelection();
                    //Updates the selectedView to the new selected view.
                    selectedView = tableSongs.getSelectionModel();
                }
            }
        });
        //Same as above, just opposite.
        tableCurrentPlaylist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {
            @Override
            public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
                if (newValue != null) {
                    tableSongs.getSelectionModel().clearSelection();
                    selectedView = tableCurrentPlaylist.getSelectionModel();
                }
            }
        });
    }

    @FXML
    private void handleSongTableButton(MouseEvent event) throws IOException {
        System.out.println("Test");

        ImageView selectedImage = (ImageView) event.getSource();

        Stage primStage = (Stage) txtSearch.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/NewEditSongView.fxml"));
        Parent root = loader.load();

        Stage editStage = new Stage();
        editStage.setScene(new Scene(root));

        editStage.initModality(Modality.WINDOW_MODAL);
        editStage.initOwner(primStage);

        if (selectedImage.getId().equals("add")) {
            editStage.show();
        } else if (selectedImage.getId().equals("editSong")) {
            Song songToEdit = tableSongs.getSelectionModel().getSelectedItem();
            if (songToEdit != null) {
                NewEditSongController songController = loader.getController();
                songController.setTxtTitle(songToEdit.getTitle().get());
                songController.setTxtArtist(songToEdit.getArtist().get());
                songController.setTxtDuration(songToEdit.getDuration().get());
                songController.setComboGenre(songToEdit.getGenre().get());
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
    private void handlePlayButton(MouseEvent event) {

        Song selectedSong = selectedView.getSelectedItem();
        if (btnPlay.getImage() == play) {
            if (selectedSong != null) {
                songModel.playSelectedSong(selectedSong);
                btnPlay.setImage(pause);
                lblIsPlaying.setText(selectedSong.getTitle().get() + IS_PLAYING);
            }
        } else {
            songModel.pausePlaying();
            btnPlay.setImage(play);
            lblIsPlaying.setText(songModel.getCurrentSongPlaying().getTitle().get() + IS_PAUSED);
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

    /**
     * Searches for songs containing the query.
     */
    @FXML
    private void handleSearch() {
        String search = txtSearch.getText();
        if (!search.equals("")) {
            songModel.searchSong(search);
        }
    }

    /**
     * Clears the query and shows all songs.
     */
    @FXML
    private void handleClearSearch() {
        songModel.clearSearch();
        txtSearch.setText("");
    }

    /**
     * Select the next song in the currently selected tableView.
     */
    @FXML
    private void handleSkipForwardButton() {
        selectedView.selectNext();
    }

    /**
     * Select the previous song from the selected tableView.
     */
    @FXML
    private void handleSkipBackwardButton() {
        selectedView.selectPrevious();
    }

    @FXML
    private void handleSongDeleteButton(MouseEvent event) {
        Song songToDelete = tableSongs.getSelectionModel().getSelectedItem();
        songModel.getSongs().remove(songToDelete);
    }

    @FXML
    private void handleReplay() {
        songModel.replaySong();
    }
}
