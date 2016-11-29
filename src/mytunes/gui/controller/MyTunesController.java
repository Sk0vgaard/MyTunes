/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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

    private Stage primStage;

    private final Image play = new Image(getClass().getResourceAsStream("/mytunes/assets/icons/play.png"));
    private final Image pause = new Image(getClass().getResourceAsStream("/mytunes/assets/icons/pause.png"));

    private SongModel songModel;

    private static final String IDLE_TEXT = "Enjoy your music!";
    private static final String IS_PLAYING = " is playing";
    private static final String IS_PAUSED = " is paused";

    private TableView.TableViewSelectionModel<Song> selectedView;
    private TableView.TableViewSelectionModel<Song> playingView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        songModel = SongModel.getInstance();

        btnPlay.setImage(play);

        initializeTables();

        //Set a heartily welcome message
        lblIsPlaying.setText(IDLE_TEXT);

        //updates the current active view
        selectedView = tableSongs.getSelectionModel(); //Setting the default view to tableSongs.
        playingView = tableSongs.getSelectionModel(); //Setting the default playingView.

        //Add listeners to tables
        addChangeListenersToTables();
    }

    /**
     * Assigns changelisteners to see where the user clicks
     */
    private void addChangeListenersToTables() {
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

    /**
     * Assigns information to the tables
     */
    private void initializeTables() {
        //Add songs from the model and show them in the tableSongs
        clmSongTitle.setCellValueFactory(i -> i.getValue().getTitle());
        clmSongArtist.setCellValueFactory(i -> i.getValue().getArtist());
        clmSongGenre.setCellValueFactory(i -> i.getValue().getGenre());
        clmSongDuration.setCellValueFactory(i -> i.getValue().getDuration());
        tableSongs.setItems(songModel.getSongs());

        //Add songto current playlist
        clmCurrentPlaylistTitle.setCellValueFactory(i -> i.getValue().getTitle());
        tableCurrentPlaylist.setItems(songModel.getCurrentPlaylist());
    }

    /**
     * Checks if the "add image" or the "edit image" was clicked and opens the
     * corresponding view
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleSongTableButton(MouseEvent event) throws IOException {
        //Assign the clicked image to a local variable
        ImageView selectedImage = (ImageView) event.getSource();

        //Grab hold of the curret stage
        primStage = (Stage) txtSearch.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/NewEditSongView.fxml"));
        Parent root = loader.load();

        Stage editStage = new Stage();
        editStage.setScene(new Scene(root));

        //Create new modal window from the FXMLLoader
        editStage.initModality(Modality.WINDOW_MODAL);
        editStage.initOwner(primStage);

        //If user wants to add a new song just load the empty modal
        if (selectedImage.getId().equals("add")) {
            editStage.show();
            //If user wants to edit a song load up modal with info
        } else if (selectedImage.getId().equals("editSong")) {
            Song songToEdit = tableSongs.getSelectionModel().getSelectedItem();
            if (songToEdit != null) {
                assignValuesToModal(loader, songToEdit);
                editStage.show();
            }
        }
    }

    /**
     * Fills up the modal with information from the song
     *
     * @param loader
     * @param songToEdit
     */
    private void assignValuesToModal(FXMLLoader loader, Song songToEdit) {
        NewEditSongController songController = loader.getController();
        songController.setTxtTitle(songToEdit.getTitle().get());
        songController.setTxtArtist(songToEdit.getArtist().get());
        songController.setTxtDuration(songToEdit.getDuration().get());
        songController.setComboGenre(songToEdit.getGenre().get());
        songController.setCurrentSong(songToEdit);
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
        //If a song is selected and we're not currently playing anything fire up the selected song and change play button to pause
        if (btnPlay.getImage() == play) {
            if (selectedSong != null) {
                songModel.playSelectedSong(selectedSong);
                playingView = selectedView;
                btnPlay.setImage(pause);
                lblIsPlaying.setText(selectedSong.getTitle().get() + IS_PLAYING);
            }
            //If user clicks the pause button we pause the MusicPlayer
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
     * Finds which view the current played song is from. Then plays the next
     * song in that view.
     */
    @FXML
    private void handleSkipForwardButton() {
        Song currentSong = songModel.getCurrentSongPlaying();
        songModel.stopPlaying();
        playingView.select(currentSong);
        playingView.selectNext();
        songModel.playSelectedSong(selectedView.getSelectedItem());
        btnPlay.setImage(pause);
        lblIsPlaying.setText(playingView.getSelectedItem().getTitle().get() + IS_PLAYING);
    }

    /**
     * Finds which view the current played song is from. Then plays the previous
     * song in that view.
     */
    @FXML
    private void handleSkipBackwardButton() {
        Song currentSong = songModel.getCurrentSongPlaying();
        songModel.stopPlaying();
        playingView.select(currentSong);
        playingView.selectPrevious();
        songModel.playSelectedSong(selectedView.getSelectedItem());
        btnPlay.setImage(pause);
        lblIsPlaying.setText(playingView.getSelectedItem().getTitle().get() + IS_PLAYING);
    }

    @FXML
    private void handleSongDeleteButton(MouseEvent event) {
        Song songToDelete = tableSongs.getSelectionModel().getSelectedItem();

        //Show popup window and await user confirmation. If user clicks "OK" then we remove the song
        Alert alert = removeDialog(songToDelete);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            songModel.getSongs().remove(songToDelete);
        }
    }

    @FXML
    private void handleReplay() {
        songModel.replaySong();
    }

    @FXML
    private void handleSongToPlaylist(MouseEvent event) {
        Song songToAdd = tableSongs.getSelectionModel().getSelectedItem();
        songModel.getCurrentPlaylist().add(songToAdd);

    }

    @FXML
    private void handleRemoveSongFromPlaylistButton(MouseEvent event) {
        Song songToRemoveFromPlaylist = tableCurrentPlaylist.getSelectionModel().getSelectedItem();

        //Show popup window and await user confirmation. If user clicks "OK" then we remove the song
        Alert alert = removeDialog(songToRemoveFromPlaylist);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            songModel.getCurrentPlaylist().remove(songToRemoveFromPlaylist);
        }
    }

    /**
     * Opens a dialog for remove confirmation
     *
     * @return
     */
    private Alert removeDialog(Song songToRemove) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to remove the song: " + "\n\n" + songToRemove.getTitle().get());
        alert.setContentText("Press 'OK' to remove.");
        return alert;
    }

    @FXML
    private void handleRemovePlaylist(MouseEvent event) {
    }

    @FXML
    private void handleNewPlaylist(MouseEvent event) {
    }

    @FXML
    private void handleEditPlaylist(MouseEvent event) {
    }
}
