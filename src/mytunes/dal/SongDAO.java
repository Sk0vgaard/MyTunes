/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import mytunes.be.Song;

public class SongDAO {

    private static SongDAO instance;

    private final String path = System.getProperty("user.dir").replace('\\', '/') + "/src/mytunes/data/";

    private ArrayList<Song> savedSongs;

    public static SongDAO getInstance() {
        if (instance == null) {
            instance = new SongDAO();
        }
        return instance;
    }

    private SongDAO() {
        savedSongs = new ArrayList<>();
    }

    /**
     * Writes the songs to a file
     *
     * @param songs
     */
    public void writeObject(ArrayList<Song> songs) {
        //File writing
//        File songData = new File(path + "songs.data");
//        try (PrintWriter out = new PrintWriter(songData)) {
//            for (Song song : songs) {
//                out.write(song.getId() + "," + song.getTitle().get());
//                out.println();
//            }
//        } catch (FileNotFoundException fnfe) {
//            System.out.println("File not found");
//        }

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

}
