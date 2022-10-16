package prr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import prr.app.exceptions.DuplicateClientKeyException;
import prr.clients.Client;
import prr.exceptions.ImportFileException;
import prr.exceptions.UnrecognizedEntryException;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

    /* We should probably do this with ClientID class or sth FIXME  */
    private Map<String, Client> _clients = new TreeMap<>();

    /* We should probably do this with TerminalID class or sth FIXME  */
    private Map<String, Client> _terminals = new TreeMap<>();
    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091753L;

    // FIXME define attributes
    // FIXME define contructor(s)
    // FIXME define methods

    /**
     * Read text input file and create corresponding domain entities.
     *
     * @param filename name of the text input file
     * @throws UnrecognizedEntryException if some entry is not correct
     * @throws IOException                if there is an IO erro while processing the text file
     */
    void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */ {
        //FIXME implement method
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|");
                try {
                    registerEntry(fields);
                } catch (Exception e /* FIXME */) {
                    // DAVID should not happen
                    e.printStackTrace();
                }
            }
        } catch (IOException e1) {
            throw new ImportFileException();
        }
    }

    /*
    *  Registers the entries by delegating to the correct method
    * */
    void registerEntry(String[] fields) {
        switch (fields[0]) {
            case "CLIENT" -> registerClient(fields);
            case "BASIC", "FANCY" -> registerTerminal(fields);
            case "FRIENDS" -> registerFriends(fields);
        }
    }



    /*
     *  Registers the Clients
     * */
    private void registerClient(String[] fields) throws DuplicateClientKeyException {
        if (_clients.get(fields[0]) != null)
            throw new DuplicateClientKeyException(fields[0]);

        Client client = new Client(fields[1], fields[2], Integer.parseInt(fields[3]));
    }

    /*
     *  Registers the Terminals
     * */
    private void registerTerminal(String[] fields) {

    }

    /*
     *  Registers the Friends to a Terminal
     * */
    private void registerFriends(String[] fields) {

    }

}

