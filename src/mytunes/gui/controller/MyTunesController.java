/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import mytunes.be.Playlist;
import mytunes.be.Song;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /*TODO ALH: We should be able to add to our Observable List in the model
    In order to do so a new modal window should pop-up  when we click the btnAddSong*/
 /*TODO ALH: In this window we should see TextFields to add information, possibly a combobox to select the category and finally a button to save the information
    When the new btnSaveSong is clicked a method should be called, preferably in a new controller for the newly created window
     */
}
