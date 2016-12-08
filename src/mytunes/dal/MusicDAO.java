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
import java.util.Scanner;
import mytunes.be.Playlist;
import mytunes.be.Song;

public class MusicDAO {

    private static MusicDAO instance;

    private final String ID_FILE = "id.txt";

    private ArrayList<Song> savedSongs;
    private ArrayList<Playlist> savedPlaylists;

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
            System.out.println("Songs saved!");

        } catch (Exception ex) {
            System.out.println("Songs save Error " + ex);
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
        } catch (Error | IOException | ClassNotFoundException ex) {
            System.out.println("Songs read Error " + ex);
        }
        return savedSongs;
    }

    /**
     * Check if songs.data is there
     *
     * @return
     */
    public boolean isSongsThere() {
        File songs = new File("songs.data");
        boolean songsExists = songs.exists();
        return songsExists;
    }

    /**
     * Writes the playlists to a file
     *
     * @param playlists
     */
    public void writePlaylists(ArrayList<Playlist> playlists) {
        //File writing
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("playlists.data"))) {
            oos.writeObject(playlists);
            System.out.println("Playlists saved!");

        } catch (Exception ex) {
            System.out.println("Playlist Error " + ex);
        }
    }

    /**
     * open the playlists.data and load in playlist
     *
     * @return
     */
    public ArrayList<Playlist> getPlaylistsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("playlists.data"))) {
            savedPlaylists = (ArrayList<Playlist>) ois.readObject();
            System.out.println("Loaded playlists!");
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Songs read Error " + ex);
        }
        return savedPlaylists;
    }

    /**
     * Check if playlists.data exists
     *
     * @return
     */
    public boolean isPlaylistsThere() {
        File playlists = new File("playlists.data");
        boolean playlistsExists = playlists.exists();
        return playlistsExists;
    }

    /**
     * Saves the id to a file.
     *
     * @param id
     * @throws FileNotFoundException
     */
    public void saveIdFile(int id) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(ID_FILE);
        out.println(id);
        out.close();
    }

    /**
     * Gets the id from the idFile.
     *
     * @return
     * @throws FileNotFoundException
     */
    public int getIdFile() throws FileNotFoundException {
        int id = 0;
        File inputFile = new File(ID_FILE);
        Scanner in = new Scanner(inputFile);
        if (in.hasNextInt()) {
            id = in.nextInt();
        }
        return id;
    }

    /**
     * Checks if the file containg id's exists.
     *
     * @return
     */
    public boolean isIdFileThere() {
        File file = new File(ID_FILE);
        boolean fileExists = file.exists();
        return fileExists;
    }
}
