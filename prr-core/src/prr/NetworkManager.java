package prr;

import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import prr.exceptions.ImportFileException;
import prr.exceptions.MissingFileAssociationException;
import prr.exceptions.UnavailableFileException;
import prr.exceptions.UnrecognizedEntryException;

//FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Manage access to network and implement load/save operations.
 */
public class NetworkManager {

    /**
     * The network itself.
     */
    private Network _network = new Network();

    String _filename;


    public Network getNetwork() {
        return _network;
    }

    /**
     * @param filename name of the file containing the serialized application's state
     *                 to load.
     * @throws UnavailableFileException if the specified file does not exist or there is
     *                                  an error while processing this file.
     */
    public void load(String filename) throws UnavailableFileException {
        //FIXME implement serialization method

        try(ObjectInputStream oos = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))){
            _network = (Network) oos.readObject();
              /*catch (ClassNotFoundException | IOException e) {
                throw new UnavailableFileException(e);
            }*/
        } catch (ClassNotFoundException | IOException e) {
            throw new UnavailableFileException(filename);
        }
        //FIXME should this go before (if load fails, filename is still stored and save wont ask for it) or here?
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
            //oos.flush();
            oos.writeObject(_network);
        }   //FIXME should I catch? bc the method supposedly throws ioexception

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
        }   //FIXME should I catch? bc the method supposedly throws ioexception
        // podia chamar save() mas estaria a verificar se filename==null outra vez
    }

    /**
     * Read text input file and create domain entities..
     *
     * @param filename name of the text input file
     * @throws ImportFileException
     */
    public void importFile(String filename) throws ImportFileException {
        try {
            _network.importFile(filename);
        } catch (IOException | UnrecognizedEntryException /* FIXME maybe other exceptions */ e) {
            throw new ImportFileException(filename, e);
        }   //FIXME other catches?
    }

}
