/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Song;

public class SongModel {
    
     private ObservableList<Song> songs;
     
     
     

    /* TODO ALH: Here we should have a List (the fish bucket!)
    This List should be "Observable", so that the visible items are automatically updated when we add to the List
    For inspiration look at the project "RateMyGame" @ https://github.com/onero/RateMyGame/blob/master/src/ratemygame
     */
    //TODO ALH: The List should be initialized as an ObservableArrayList when the model is "constructed"
    //TODO ALH: We need to be able to retrieve the List, so that we can show it in the GUI
    //TODO ALH: We need a method to add songs to the List, so that our other awesome developers can call this method and add a new song!
    /*TODO ALH FOR RASMUS: It should not be possible to instantiate more than one Object of this class.
    In order to achieve this we should use a "Singleton".
     */
<<<<<<< HEAD

    public SongModel()
    {
        songs = FXCollections.observableArrayList();
    }

    public ObservableList<Song> getSongs()
    {
        return songs;
    }
    
=======
    
    private static SongModel instance;
    
    /**
     * If SongModel has not been instantiated, make a new instance off of it and return it.
     * If there already is an instance of SongModel, return that instance.
     * @return 
     */
    public static SongModel getInstance()
    {
        if(instance == null)
        {
            instance = new SongModel();
        }
        return instance;
    }
>>>>>>> origin/alpha
}
