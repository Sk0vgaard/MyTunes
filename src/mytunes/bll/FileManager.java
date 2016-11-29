/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.stage.FileChooser;

public class FileManager {

    private String path;

    public void openFile() throws FileNotFoundException, IOException {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter mp3Filter = new FileChooser.ExtensionFilter("MP3 (*.mp3)", "*.mp3");
        fc.setSelectedExtensionFilter(mp3Filter);
        File song = fc.showOpenDialog(null);
        path = song.getAbsolutePath();
        path = path.replace("\\", "/");
        FileInputStream fis = new FileInputStream(song);
        int size = (int) song.length();
        fis.skip(size - 128);
        byte[] last128 = new byte[128];
        fis.read(last128);
        String id3 = new String(last128);
        String tag = id3.substring(0, 3);
        String title = id3.substring(3, 30);
        String artist = id3.substring(30, 60);
        String album = id3.substring(60, 90);
        String year = id3.substring(90, 98);
        String genre = id3.substring(128, 128);
        if (tag.equals("TAG")) {
            System.out.println("Title: " + title);
            System.out.println("Artist: " + artist);
            System.out.println("Album: " + album);
            System.out.println("Year: " + year);
            System.out.println("Genre: " + genre);

        }

    }

    public String getPath() {
        return path;
    }

}
