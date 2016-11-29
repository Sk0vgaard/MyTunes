/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.File;
import javafx.stage.FileChooser;

public class FileManager {

    private String path;

    public void openFile() {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter mp3Filter = new FileChooser.ExtensionFilter("MP3 (*.mp3)", "*.mp3");
        fc.setSelectedExtensionFilter(mp3Filter);
        File file = fc.showOpenDialog(null);
        path = file.getAbsolutePath();
        path = path.replace("\\", "/");
    }

    public String getPath() {
        return path;
    }

}
