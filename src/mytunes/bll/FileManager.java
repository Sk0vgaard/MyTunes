/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    private final Song selectedSong = new Song("", "", "", "");

    public Song openFile() throws FileNotFoundException, IOException, InterruptedException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter mp3Filter = new FileChooser.ExtensionFilter("MP3 (*.mp3)", "*.mp3");
        fc.setSelectedExtensionFilter(mp3Filter);
        File song = fc.showOpenDialog(null);
        path = song.getAbsolutePath();
        path = path.replace("\\", "/");
        MP3File selectedFile = (MP3File) AudioFileIO.read(song);
        MP3AudioHeader header = selectedFile.getMP3AudioHeader();
        Tag tag = selectedFile.getTag();
        selectedSong.setTitle(tag.getFirst(FieldKey.TITLE));
        selectedSong.setArtist(tag.getFirst(FieldKey.ARTIST));
        selectedSong.setGenre(tag.getFirst(FieldKey.GENRE));
        selectedSong.setDuration(header.getTrackLengthAsString());
        selectedSong.setFileName(path);
        return selectedSong;
    }

    public String getPath() {
        return path;
    }

}
