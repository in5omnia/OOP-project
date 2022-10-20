package prr;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;

import prr.exceptions.ImportFileException;
import prr.exceptions.UnavailableFileException;
import prr.exceptions.UnrecognizedEntryException;
import prr.exceptions.MissingFileAssociationException;


/**
 * Manage access to network and implement load/save operations.
 */
public class NetworkManager {

    /**
     * The network itself.
     */
    private Network _network = new Network();

    private String _filename;

    public Network getNetwork() {
        return _network;
    }


    /**
     * Loads the network from a file.
     * @param filename name of the file containing the serialized application's state
     *                 to load.
     * @throws UnavailableFileException if the specified file does not exist or there is
     *                                  an error while processing this file.
     */
    public void load(String filename) throws UnavailableFileException {

        try(ObjectInputStream oos = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))){
            _network = (Network) oos.readObject();

        } catch (ClassNotFoundException | IOException e) {
            throw new UnavailableFileException(filename);
        }
        _filename = filename;

    }

    /**
     * Saves the serialized application's state into the file associated to the current network.
     *
     * @throws FileNotFoundException           if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException                     if there is some error while serializing the state of the network to disk.
     */
    public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {

        if (_filename == null)
            throw new MissingFileAssociationException();

        try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)))){
            oos.writeObject(_network);
        }

    }

    /**
     * Saves the serialized application's state into the specified file. The current network is
     * associated to this file.
     *
     * @param filename the name of the file.
     * @throws FileNotFoundException           if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException                     if there is some error while serializing the state of the network to disk.
     */
    public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {

        _filename = filename;
        try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)))){
            oos.writeObject(_network);
        }
    }

    /**
     * Read text input file and create domain entities..
     *
     * @param filename name of the text input file
     * @throws ImportFileException if an entry of the file is incorrect or there is an IO error while processing the file
     */
    public void importFile(String filename) throws ImportFileException {
        try {
            _network.importFile(filename);
        } catch (IOException | UnrecognizedEntryException e) {
            throw new ImportFileException(filename, e);
        }
    }

}
