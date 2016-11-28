/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import mytunes.gui.model.SongModel;

/**
 * FXML Controller class
 *
 * @author Rasmus
 */
public class NewEditSongController implements Initializable
{
    private SongModel songModel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        songModel = SongModel.getInstance();
    }    
    
}
