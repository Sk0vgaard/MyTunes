/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.gui.model.PlaylistModel;
import mytunes.gui.model.SongModel;

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
    private TableColumn<Playlist, String> clmPlaylistSongsAmount;
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
    @FXML
    private ImageView speaker;
    @FXML
    private Label lblTotalSongs;
    @FXML
    private Label lblTotalDuration;
    @FXML
    private ProgressBar sliderMusic;
    @FXML
    private Label lblTime;
    @FXML
    private Label lblPlaylistDuration;
    @FXML
    private Label lblPlaylistSongs;
    @FXML
    private Pane anchorPaneColor;
    @FXML
    private ImageView btnAddPlaylist;
    @FXML
    private ToggleGroup themeGroup;

    private final Image play = new Image(getClass().getResourceAsStream("/mytunes/assets/icons/play.png"));
    private final Image pause = new Image(getClass().getResourceAsStream("/mytunes/assets/icons/pause.png"));
    private final Image normal = new Image(getClass().getResourceAsStream("/mytunes/assets/icons/speaker.png"));
    private final Image mute = new Image(getClass().getResourceAsStream("/mytunes/assets/icons/muted.png"));
    private static final String IDLE_TEXT = "Enjoy your music!";
    private static final String IS_PLAYING = " is playing";
    private static final String IS_PAUSED = " is paused";

    private Song selectedSong;

    private Stage primStage;

    private double lastVolume;

    private SongModel songModel;
    private PlaylistModel playlistModel;

    private TableView.TableViewSelectionModel<Song> selectedView;
    private TableView.TableViewSelectionModel<Song> playingView;

    private static MyTunesController instance;

    public static MyTunesController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        instance = this;

        songModel = SongModel.getInstance();
        playlistModel = PlaylistModel.getInstance();

        btnPlay.setImage(play);

        speaker.setImage(normal);

        lblTime.setText("");

        initializeTables();

        //Set a heartily welcome message
        lblIsPlaying.setText(IDLE_TEXT);

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
        songModel.loadSavedSongs();
        tableSongs.setItems(songModel.getSongs());
        updateSongTotals();
        updateCurrentPlaylistTotals();

        //Add song to current playlist
        clmCurrentPlaylistTrack.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(tableCurrentPlaylist.getItems().indexOf(column.getValue())).asString());
        clmCurrentPlaylistTitle.setCellValueFactory(i -> i.getValue().getTitle());
        tableCurrentPlaylist.setItems(playlistModel.getCurrentPlaylist());

        //add playlists from the model and show them in the tablePlaylists
        clmPlaylistName.setCellValueFactory(i -> i.getValue().getName());
        clmPlaylistSongsAmount.setCellValueFactory(i -> i.getValue().getSongs());
        clmPlaylistTotalDuration.setCellValueFactory(i -> i.getValue().getDuration());
        playlistModel.loadSavedPlaylists();
        tablePlaylists.setItems(playlistModel.getPlaylists());
        if (tablePlaylists.getItems().size() > 0) {
            tablePlaylists.getSelectionModel().selectFirst();
            handleSelectPlaylist(null);
        }
    }

    /**
     * Gets the music progress bar
     *
     * @return
     */
    public ProgressBar getMusicSlider() {
        return sliderMusic;
    }

    /**
     * Gets the label music time
     *
     * @return
     */
    public Label getTimeLabel() {
        return lblTime;
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
                assignSongValuesToModal(loader, songToEdit);
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
    private void assignSongValuesToModal(FXMLLoader loader, Song songToEdit) {
        NewEditSongController songController = loader.getController();
        songController.setTxtTitle(songToEdit.getTitle().get());
        songController.setTxtArtist(songToEdit.getArtist().get());
        songController.setTxtDuration(songToEdit.getDuration().get());
        songController.setComboGenre(songToEdit.getGenre().get());
        songController.setPath(songToEdit.getFileName().get());
        songController.setCurrentSong(songToEdit);
    }

    private void assignPlaylistValuesToModal(FXMLLoader loader, Playlist playlistToEdit) {
        NewEditPlaylistController playlistController = loader.getController();
        playlistController.setTxtName(playlistToEdit.getName().get());
        playlistController.setCurrentPlaylist(playlistToEdit);
    }

    /**
     * Gets the current selected song and calls the play method of the
     * MusicPlaer. Changes the text of the play button appropriately.
     *
     * @param event
     */
    @FXML
    private void handlePlayButton() {
        selectedSong = selectedView.getSelectedItem();
        //If a song is selected and we're not currently playing anything fire up the selected song and change play button to pause
        if (btnPlay.getImage() == play) {
            if (selectedSong != null) {
                playSong(selectedSong);

            }
            //If user clicks the pause button we pause the MusicPlayer
        } else {
            songModel.pausePlaying();
            btnPlay.setImage(play);
            lblIsPlaying.setText(songModel.getCurrentSongPlaying().getTitle().get() + IS_PAUSED);
            checkVolume();
        }
    }

    private void checkVolume() {
        if (speaker.getImage().equals(mute)) {
            songModel.switchVolume(0.0);
        } else {
            songModel.switchVolume(sliderVolume.getValue() / 100.0);
        }
    }

    /**
     * Play a song on a double click
     */
    @FXML
    private void handleDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            songModel.stopPlaying();
            selectedSong = selectedView.getSelectedItem();
            playSong(selectedSong);
            checkVolume();
        }
    }

    /**
     * Asks the model to play the selected song
     *
     * @param selectedSong
     */
    private void playSong(Song selectedSong) {
        songModel.playSelectedSong(selectedSong);
        playingView = selectedView;
        btnPlay.setImage(pause);
        lblIsPlaying.setText(selectedSong.getTitle().get() + IS_PLAYING);
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
        if (txtSearch.getText().equals("")) {
            handleClearSearch();
        } else {
            String search = txtSearch.getText();
            if (!search.equals("")) {
                songModel.searchSong(search);
            }
        }
    }

    /**
     * Searches for songs on enter
     */
    @FXML
    private void handleEnterSearch(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            handleSearch();
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
     * Selects the next song in the playing view and plays it. Updates the
     * label.
     */
    public void playNextSong() {
        Song nextSong = selectNextSong(true); //sends true value to skip forward
        playSong(nextSong);
    }

    /**
     * Select the next song from current view
     *
     * @return
     */
    private Song selectNextSong(boolean goForward) {
        songModel.stopPlaying();
        Song currentSong = songModel.getCurrentSongPlaying();
        playingView.select(currentSong);
        if (tableCurrentPlaylist.getSelectionModel().getFocusedIndex() == playlistModel.getCurrentPlaylist().size()) {
            if (goForward) {
                playingView.selectNext();
            } else {
                playingView.selectPrevious();
            }
        } else {
            playingView.selectFirst();
        }
        
        Song nextSong = selectedView.getSelectedItem();
        return nextSong;
    }
    
    /**
     * Finds which view the current played song is from. Then plays the next
     * song in that view.
     */
    @FXML
    private void handleSkipForwardButton() {
        Song nextSong = selectNextSong(true); //sends true value to skip forward
        playSong(nextSong);
    }

    /**
     * Finds which view the current played song is from. Then plays the previous
     * song in that view.
     */
    @FXML
    private void handleSkipBackwardButton() {
        Song nextSong = selectNextSong(false); //sends false value to go to previous
        playSong(nextSong);
    }

    /**
     * Gets the selected song and prompts user to delete
     *
     * @param event
     */
    @FXML
    private void handleSongDeleteButton(MouseEvent event) {
        try {

            ObservableList<Song> songsToDelete = tableSongs.getSelectionModel().getSelectedItems();
            Alert alert;
            //Show popup window and await user confirmation. If user clicks "OK" then we remove the song
            if (songsToDelete.size() > 1) {
                alert = removeManyItems();
            } else {
                alert = songRemoveDialog(songsToDelete.get(0));
            }

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                songModel.deleteSong(songsToDelete);
                updateSongTotals();
            }
        } catch (NullPointerException npe) {
            System.out.println("Wrong delete button");
        }

    }

    /**
     * Replays the current song
     */
    @FXML
    private void handleReplay() {
        songModel.replaySong();
    }

    /**
     * Adds a song (or more) to the current playlist
     *
     * @param event
     */
    @FXML
    private void handleSongToPlaylist(MouseEvent event) throws IOException {
        if (tablePlaylists.getSelectionModel().getSelectedItem() != null && tableSongs.getSelectionModel().getSelectedItem() != null) {
            ObservableList<Song> songsToAddToPlaylist = tableSongs.getSelectionModel().getSelectedItems();
            for (int i = 0; i < songsToAddToPlaylist.size(); i++) {
                playlistModel.addSongToPlaylist(songsToAddToPlaylist.get(i));
            }
            playlistModel.savePlaylists();
            updateInfo();
        } //If there is no playlist created, pop a dialog and create a new playlist.
        else if (tablePlaylists.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No existing playlists!");
            alert.setHeaderText("There is no playlist to add to.");
            alert.setContentText("Create a new playlist to continue.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                btnAddPlaylist.fireEvent(event);
            }
        }
    }

    /**
     * Prompts user to remove song from current playlist
     *
     * @param event
     */
    @FXML
    private void handleRemoveSongFromPlaylistButton(MouseEvent event) {
        try {
            int idPlaylist = tablePlaylists.getSelectionModel().getSelectedItem().getId();
            ObservableList<Song> songsToRemoveFromPlaylist = tableCurrentPlaylist.getSelectionModel().getSelectedItems();
            Alert alert;
            //Show popup window and await user confirmation. If user clicks "OK" then we remove the song
            if (songsToRemoveFromPlaylist.size() > 1) {
                alert = removeManyItems();
            } else {
                alert = songRemoveDialog(songsToRemoveFromPlaylist.get(0));
            }

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                playlistModel.deleteFromPlaylist(idPlaylist, songsToRemoveFromPlaylist);
                playlistModel.savePlaylists();
                updateInfo();
            }
        } catch (NullPointerException npe) {
            System.out.println("Wrong delete buttom");
        }
    }

    /**
     * Opens a dialog for remove confirmation
     */
    private Alert removeManyItems() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to remove all these elements?");
        alert.setContentText("Press 'OK' to remove.");
        return alert;
    }

    /**
     * Opens a dialog for remove confirmation
     *
     * @param songToRemove
     * @return
     */
    private Alert songRemoveDialog(Song songToRemove) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to remove the song: " + "\n\n" + songToRemove.getTitle().get());
        alert.setContentText("Press 'OK' to remove.");
        return alert;
    }

    /**
     * Opens a dialog for remove confirmation
     *
     * @param playlistToRemove
     * @return
     */
    private Alert playlistRemoveDialog(Playlist playlistToRemove) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to remove the playlist: " + "\n\n" + playlistToRemove.getName().get());
        alert.setContentText("Press 'OK' to remove.");
        return alert;
    }

    /**
     * Add or edit a playlist depending on user choice
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void handlePlaylistTableButton(MouseEvent event) throws IOException {
        //Assign the clicked image to a local variable
        ImageView selectedImage = (ImageView) event.getSource();

        //Grab hold of the curret stage
        primStage = (Stage) txtSearch.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/NewEditPlaylistView.fxml"));
        Parent root = loader.load();

        Stage editStage = new Stage();
        editStage.setScene(new Scene(root));

        //Create new modal window from the FXMLLoader
        editStage.initModality(Modality.WINDOW_MODAL);
        editStage.initOwner(primStage);

        //If user wants to add a new playlist just load the empty modal
        if (selectedImage.getId().equals("createPlaylist")) {
            editStage.showAndWait();
            tablePlaylists.getSelectionModel().selectLast();
            //If user wants to edit a song load up modal with info
        } else if (selectedImage.getId().equals("editPlaylist")) {
            Playlist playlistToEdit = tablePlaylists.getSelectionModel().getSelectedItem();
            if (playlistToEdit != null) {
                assignPlaylistValuesToModal(loader, playlistToEdit);
                editStage.show();
            }
        }
    }

    /**
     * Deletes playlist
     *
     * @param event
     */
    @FXML
    private void handleRemovePlaylist(MouseEvent event) {
        ObservableList<Playlist> playlistsToDelete = tablePlaylists.getSelectionModel().getSelectedItems();
        Alert alert;
        if (playlistsToDelete.size() > 1) {
            alert = removeManyItems();
        } else {
            alert = playlistRemoveDialog(playlistsToDelete.get(0));
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            playlistModel.deletePlaylist(playlistsToDelete);
        }
    }

    /**
     * Moves the selected song one up when clicked.
     *
     * @param event
     */
    @FXML
    private void handleMoveSongUpButton(MouseEvent event) {
        int selectedIndex = tableCurrentPlaylist.getSelectionModel().getSelectedIndex();
        ArrayList<Song> currentPlaylist = playlistModel.getCurrentPlaylistAsArrayList();
        if (selectedIndex - 1 >= 0) {
            Collections.swap(currentPlaylist, selectedIndex, selectedIndex - 1);
            playlistModel.updateCurrentPlaylist(currentPlaylist);
            tableCurrentPlaylist.getSelectionModel().select(selectedIndex - 1);
        }
    }

    /**
     * Moves the selected song one down when clicked.
     *
     * @param event
     */
    @FXML
    private void handleMoveSongDownButton(MouseEvent event) {
        int selectedIndex = tableCurrentPlaylist.getSelectionModel().getSelectedIndex();
        ArrayList<Song> currentPlaylist = playlistModel.getCurrentPlaylistAsArrayList();
        if (selectedIndex + 1 < currentPlaylist.size() && selectedView == tableCurrentPlaylist.getSelectionModel()) {
            Collections.swap(currentPlaylist, selectedIndex, selectedIndex + 1);
            playlistModel.updateCurrentPlaylist(currentPlaylist);
            tableCurrentPlaylist.getSelectionModel().select(selectedIndex + 1);
        }
    }

    /**
     * Handle music volume
     */
    @FXML
    private void handleMusicVolume() {
        sliderVolume.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (sliderVolume.isValueChanging()) {
                    songModel.switchVolume(sliderVolume.getValue() / 100.0);
                    if (speaker.getImage().equals(mute)) {
                        speaker.setImage(normal);
                    }
                } else if (sliderVolume.isPressed()) {
                    songModel.switchVolume(sliderVolume.getValue() / 100.0);
                    if (speaker.getImage().equals(mute)) {
                        speaker.setImage(normal);
                    }
                }
            }

        });
    }

    /**
     * Mute or unmutes the music player
     *
     * @param event
     */
    @FXML
    private void handleMute(MouseEvent event) {
        if (speaker.getImage().equals(normal)) {
            lastVolume = sliderVolume.getValue();
            songModel.switchVolume(0);
            sliderVolume.setValue(0);
            speaker.setImage(mute);
        } else {
            sliderVolume.setValue(lastVolume);
            speaker.setImage(normal);
            checkVolume();
        }
    }

    /**
     * Shuffles the current playlist
     *
     * @param event
     */
    @FXML
    private void handleShufflePlaylist(MouseEvent event) {
        playlistModel.shuffleCurrentPlaylist();
    }

    /**
     * Shuffles all the songs
     */
    @FXML
    private void handleShuffleSongs(MouseEvent event) {
        songModel.shuffleSongs();
    }

    /**
     * When a playlist is selected, show it's song in the currentPlaylist view.
     *
     * @param event
     */
    @FXML
    private void handleSelectPlaylist(MouseEvent event) throws NullPointerException {
        try {
            int playlistId = tablePlaylists.getSelectionModel().getSelectedItem().getId();
            playlistModel.setPlaylistID(playlistId);
            ArrayList<Song> list = tablePlaylists.getSelectionModel().getSelectedItem().getSongsInPlaylist();
            playlistModel.updateCurrentPlaylist(list);

            updateCurrentPlaylistTotals();
        } catch (Exception e) {

            System.out.println("Selection error " + e);
        }
    }

    /**
     * Select more than one song
     */
    @FXML
    private void handleMultiSelect(KeyEvent event) {
        if (event.isControlDown() | event.isShiftDown()) {
            selectedView.setSelectionMode(SelectionMode.MULTIPLE);
            tablePlaylists.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } else {
            selectedView.setSelectionMode(SelectionMode.SINGLE);
            tablePlaylists.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        }
    }

    /**
     * Updates the totalSong and totalDuration labels for the songs view.
     */
    public void updateSongTotals() {
        lblTotalSongs.setText(songModel.getSongs().size() + "");
        double duration = songModel.getTotalDurationAllSongs();
        String durationString = String.format("%.2f", duration);
        durationString = durationString.replace(",", ":");
        lblTotalDuration.setText(durationString);
    }

    /**
     * Updates the totalSong and totalDuration for the currentPlaylist view.
     */
    public void updateCurrentPlaylistTotals() {
        lblPlaylistSongs.setText(playlistModel.getCurrentPlaylist().size() + "");
        double duration = playlistModel.getDurationOfPlaylist();
        String durationString = String.format("%.2f", duration);
        durationString = durationString.replace(",", ":");
        lblPlaylistDuration.setText(durationString);
    }

    /**
     * Gets the coordinate of the mouseclick
     *
     * @param event
     */
    @FXML
    private void handleSeek(MouseEvent event) {
        if (songModel.isMusicPlayerPlaying()) {
            //Get the complete bounds of the progress bar
            Bounds b1 = sliderMusic.getLayoutBounds();
            //get the x-coordinate of the mouse click
            double mouseX = event.getX();
            double percent = (((b1.getMinX() + mouseX) * 100) / b1.getMaxX());
            double finalValue = percent / 100;
            //Update progress bar and send value to music player
            sliderMusic.setProgress(finalValue);
            songModel.setNewTime(finalValue);
        }
    }

    /**
     * Refreshes the table on changes
     */
    public void refreshTable() {
        for (int i = 0; i < tableSongs.getColumns().size(); i++) {
            ((TableColumn) (tableSongs.getColumns().get(i))).setVisible(false);
            ((TableColumn) (tableSongs.getColumns().get(i))).setVisible(true);
        }
        for (int i = 0; i < tableCurrentPlaylist.getColumns().size(); i++) {
            ((TableColumn) (tableCurrentPlaylist.getColumns().get(i))).setVisible(false);
            ((TableColumn) (tableCurrentPlaylist.getColumns().get(i))).setVisible(true);
        }
        for (int i = 0; i < tablePlaylists.getColumns().size(); i++) {
            ((TableColumn) (tablePlaylists.getColumns().get(i))).setVisible(false);
            ((TableColumn) (tablePlaylists.getColumns().get(i))).setVisible(true);
        }
    }

    /**
     * Parse the selected file to the filemanager
     *
     * @param event
     */
    @FXML
    private void handleDrag(DragEvent event) {
        Dragboard db = event.getDragboard();
        songModel.addFilesFromDrag(db.getFiles());
    }

    /**
     * Returns to standard theme
     *
     * @param event
     */
    @FXML
    private void switchToDefault(MouseEvent event) {
        anchorPaneColor.setStyle("-fx-table-cell-border-color: #f092b0");
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(0);
        colorAdjust.setSaturation(0);
        tableCurrentPlaylist.setEffect(colorAdjust);
        tablePlaylists.setEffect(colorAdjust);
        tableSongs.setEffect(colorAdjust);
    }

    /**
     * Switches to pink theme
     *
     * @param event
     */
    @FXML
    private void switchToPink(MouseEvent event) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(-0.3);
        colorAdjust.setSaturation(0.1);
        anchorPaneColor.setStyle("-fx-background-color: #f092b0");
        tableCurrentPlaylist.setEffect(colorAdjust);
        tablePlaylists.setEffect(colorAdjust);
        tableSongs.setEffect(colorAdjust);
    }

    /**
     * Switches to a more manly blue theme
     *
     * @param event
     */
    @FXML
    private void switchToBlue(MouseEvent event) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(-0.8);
        colorAdjust.setSaturation(0.1);
        anchorPaneColor.setStyle("-fx-background-color: #39add1");
        tableCurrentPlaylist.setEffect(colorAdjust);
        tablePlaylists.setEffect(colorAdjust);
        tableSongs.setEffect(colorAdjust);
    }

    /**
     * Updates all info
     */
    public void updateInfo() {
        updateCurrentPlaylistTotals();
        updateSongTotals();
        refreshTable();
    }

    /**
     * Posts current playlist to Twitter
     *
     * @param event
     * @throws IOException
     * @throws URISyntaxException
     */
    @FXML
    private void handleTwitter(MouseEvent event) throws IOException, URISyntaxException {
        System.out.println("Tweeting!");
        String playlist = playlistModel.getCurrentPlaylistAsString();
        String tweetText = "I listen to these artists: " + playlist;
        String encodedURL = URLEncoder.encode(tweetText, "UTF-8");
        String finalURL = "http://twitter.com/home?status=" + encodedURL;

        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI(finalURL));
        } catch (URISyntaxException ex) {
            System.out.println("W00T? " + ex);
        }
    }
}
