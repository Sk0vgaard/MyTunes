/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mathi
 */
public class MoreGenreViewController implements Initializable
{

    
    @FXML
    private TextField txtNewGenre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    @FXML
    private void btnHandleCancel(ActionEvent event)
    {
        // get a handle to the stage
        Stage stage = (Stage) txtNewGenre.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void btnHandleOk(ActionEvent event)
    {
        NewEditSongController.getGenreList().add(txtNewGenre.getText());    
        
        // get a handle to the stage
        Stage stage = (Stage) txtNewGenre.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

}
