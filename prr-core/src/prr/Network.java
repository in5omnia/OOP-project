package prr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.io.IOException;
import java.util.*;

import prr.app.exceptions.DuplicateClientKeyException;
import prr.app.exceptions.DuplicateTerminalKeyException;
import prr.app.exceptions.InvalidTerminalKeyException;
import prr.clients.Client;
import prr.exceptions.ImportFileException;
import prr.exceptions.UnrecognizedEntryException;
import prr.terminals.Basic;
import prr.terminals.Fancy;
import prr.terminals.Terminal;
import prr.terminals.states.*;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

    /* We should probably do this with ClientID class or sth FIXME  */
    private Map<String, Client> _clients = new TreeMap<>();

    /* We should probably do this with TerminalID class or sth FIXME  */
    private Map<String, Terminal> _terminals = new TreeMap<>();
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
    void importFile(String filename) throws UnrecognizedEntryException, IOException, ImportFileException /* FIXME maybe other exceptions */ {
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
            throw new ImportFileException(filename);
        }
    }

    /*
    *  Registers the entries by delegating to the correct method
    * */
    void registerEntry(String[] fields) throws DuplicateClientKeyException, UnrecognizedEntryException, DuplicateTerminalKeyException, InvalidTerminalKeyException {
        switch (fields[0]) {
            case "CLIENT" -> registerClient(fields);
            case "BASIC", "FANCY" -> registerTerminal(fields);
            case "FRIENDS" -> registerFriends(fields);
            default -> throw new UnrecognizedEntryException(fields[0]);
        }
    }



    /*
     *  Registers the Clients
     * */
    private void registerClient(String[] fields) throws DuplicateClientKeyException {
        if (_clients.get(fields[1]) != null) // FIXME does this get the ID????
            throw new DuplicateClientKeyException(fields[0]);

        Client client = new Client(fields[1], fields[2], Integer.parseInt(fields[3]));

        _clients.put(fields[0], client);
    }

    /*
     *  Registers the Terminals
     * */
    private void registerTerminal(String[] fields) throws InvalidTerminalKeyException, DuplicateTerminalKeyException, UnrecognizedEntryException {
        if (_terminals.get(fields[1]) != null)
            throw new DuplicateTerminalKeyException(fields[1]);
        /*
        FIXME We should check if the client exists before creating the terminal
        if (_clients.get(fields[2]) == null)
            throw new NoSuchClient(fields[2]);
         */
        // FIXME We should sanitize all of this input to be sure friends arent invalid terminals right?

        Collection<Terminal> friends = new LinkedList<>();  //collection is too broad imo->should be List
        for (String terminalId : fields[3].split(",")) {
            friends.add(_terminals.get(terminalId));
        }

        Client owner = _clients.get(fields[2]);
        State state = stateFromString(fields[3]);
        Terminal terminal = switch (fields[0]){
            case "BASIC" -> new Basic(owner, fields[0], state, friends);
            case "FANCY" -> new Fancy(owner, fields[0], state, friends);
            default -> throw new UnrecognizedEntryException(fields[0]);
        };
    }

    private State stateFromString(String state) throws UnrecognizedEntryException {
        return switch (state) {
            case "ON" -> new Idle();
            case "OFF" -> new Off();
            case "SILENT" -> new Silent();
            // FIXME does this happen? case "BUSY" -> new Busy();
            default -> throw new UnrecognizedEntryException(state);
        };
    }


    /*
     *  Registers the Friends to a Terminal
     * */
    private void registerFriends(String[] fields) {

    }

}

