/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import mytunes.be.Playlist;
import mytunes.be.Song;

public class MusicDAO {

    private static MusicDAO instance;

    private final String path = System.getProperty("user.dir").replace('\\', '/') + "/src/mytunes/data/";

    private ArrayList<Song> savedSongs;

    public static MusicDAO getInstance() {
        if (instance == null) {
            instance = new MusicDAO();
        }
        return instance;
    }

    private MusicDAO() {
        savedSongs = new ArrayList<>();
    }

    /**
     * Writes the songs to a file
     *
     * @param songs
     */
    public void writeSongs(ArrayList<Song> songs) {
        //Object writing
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("songs.data"))) {
            oos.writeObject(songs);
            System.out.println("Done");

        } catch (Exception ex) {
            System.out.println("Error " + ex);
        }
    }

    /**
     * Opens the songs.data file and loads songs
     *
     * @return
     */
    public ArrayList<Song> getSongsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("songs.data"))) {
            savedSongs = (ArrayList<Song>) ois.readObject();
            System.out.println("Loaded songs!");
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error " + ex);
        }
        return savedSongs;
    }

    /**
     * Writes the playlists to a file
     *
     * @param playlists
     */
    public void writePlaylists(ArrayList<Playlist> playlists) {
        //File writing
        File songData = new File("playlists.data");
        try (PrintWriter out = new PrintWriter(songData)) {
            for (Playlist playlist : playlists) {
                for (Song song : playlist.getSongsInPlaylist()) {
                    out.write(playlist.getId() + "," + song.getId());
                    out.println();
                }
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found");
        }
    }

}
