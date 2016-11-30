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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author mathi
 */
public class NewEditPlaylistController implements Initializable
{

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    @FXML
    private TextField txtName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    public TextField getTxtName()
    {
        return txtName;
    }

    public void setTxtName(String txtName)
    {
        this.txtName.setText(txtName);
    }

    @FXML
    private void handleCalcelButton(ActionEvent event)
    {
    }

    @FXML
    private void handleSaveButton(ActionEvent event)
    {
    }
    
}
