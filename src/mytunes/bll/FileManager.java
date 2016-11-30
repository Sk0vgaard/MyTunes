/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import mytunes.be.Song;

public class FileManager {

    private String path;

    public void openFile() throws FileNotFoundException, IOException {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter mp3Filter = new FileChooser.ExtensionFilter("MP3 (*.mp3)", "*.mp3");
        fc.setSelectedExtensionFilter(mp3Filter);
        File song = fc.showOpenDialog(null);
        path = song.getAbsolutePath();
        path = path.replace("\\", "/");
        getMetaData();

    }

    /**
     * Gets the meta data from the song
     */
    private Song getMetaData() {
        Song selectedSong = new Song("", "", "", "");
        Media file = new Media("file:///" + path);
        MediaPlayer test = new MediaPlayer(file);
        test.setOnReady(new Runnable() {

            @Override
            public void run() {

                System.out.println("Duration: " + file.getDuration().toSeconds());
                System.out.println("Title " + file.getMetadata().get("title"));

                // display media's metadata
                for (Map.Entry<String, Object> entry : file.getMetadata().entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
            }
        });
        selectedSong.setTitle(file.getMetadata().get("title").toString());
        selectedSong.setArtist(file.getMetadata().get("artist").toString());
        selectedSong.setArtist(file.getMetadata().get("genre").toString());
        return selectedSong;
    }

    public String getPath() {
        return path;
    }

}
