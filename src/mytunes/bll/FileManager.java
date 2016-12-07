/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import mytunes.be.Song;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class FileManager {

    private String path;

    private final List<File> songsFromDrag = new ArrayList();

    private final String extension = ".mp3";

    private final Song selectedSong = new Song("", "", "", "");

    public Song openFile() throws NullPointerException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));
        try {
            File song = fc.showOpenDialog(null);
            getMetaData(song);
        } catch (NullPointerException npe) {
            System.out.println("You should select an mp3 file " + npe);
        }
        return selectedSong;
    }

    /**
     * Use the jaudiotagger library to retrieve the metadata
     *
     * @param song
     */
    public void getMetaData(File song) {
        try {
            MP3File selectedFile = (MP3File) AudioFileIO.read(song);
            MP3AudioHeader header = selectedFile.getMP3AudioHeader();
            Tag tag = selectedFile.getTag();
            selectedSong.setTitle(tag.getFirst(FieldKey.TITLE));
            selectedSong.setArtist(tag.getFirst(FieldKey.ARTIST));
            selectedSong.setGenre(tag.getFirst(FieldKey.GENRE));
            selectedSong.setDuration(header.getTrackLengthAsString());
            path = song.toURI().toASCIIString();
            path = path.replace("\\", "/");
            selectedSong.setFileName(path);
        } catch (CannotReadException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TagException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ReadOnlyFileException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAudioFrameException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get song
     *
     * @return
     */
    public Song getSong() {
        return selectedSong;
    }

    /**
     * Returns the path of the song
     *
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * Returns mp3 files from parsed list
     *
     * @param files
     * @return
     */
    public ArrayList<File> getSongFilesFromDrag(List<File> files) {
        for (File file : files) {
            if (file.getName().endsWith(extension)) {
                songsFromDrag.add(file);
            }
            if (file.isDirectory()) {
                List<File> folderFiles = new ArrayList();
                for (File listFile : file.listFiles()) {
                    folderFiles.add(listFile);
                }
                getSongFilesFromDrag(folderFiles);
            }
        }
        return (ArrayList) songsFromDrag;
    }

}
