package prr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.io.IOException;
import java.util.*;

import prr.exceptions.*;
import prr.clients.Client;
import prr.exceptions.DuplicateClientException;
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
    void registerEntry(String[] fields) throws DuplicateClientException, UnrecognizedEntryException,
            DuplicateTerminalException, InvalidTerminalIdException, UnknownClientException, UnknownTerminalException, DuplicateFriendException, OwnFriendException {
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
    private void registerClient(String[] fields) throws DuplicateClientException {
        if (findClient(fields[1]) != null) // FIXME does this get the ID????
            throw new DuplicateClientException(fields[1]);

        Client client = new Client(fields[1], fields[2], Integer.parseInt(fields[3]));

        _clients.put(fields[1], client);
    }

    /*
     *  Registers the Terminals
     * */
    private void registerTerminal(String[] fields) throws InvalidTerminalIdException, DuplicateTerminalException, UnrecognizedEntryException, UnknownClientException {
        if (findTerminal(fields[1]) != null)
            throw new DuplicateTerminalException(fields[1]);

        Client owner = findClient(fields[2]);
        if (owner == null)
            throw new UnknownClientException(fields[2]);

        State state = stateFromString(fields[3]);
        Terminal terminal = switch (fields[0]){
            case "BASIC" -> new Basic(owner, fields[1], state);
            case "FANCY" -> new Fancy(owner, fields[1], state);
            default -> throw new UnrecognizedEntryException(fields[0]);
        };

        addTerminalToNetwork(fields[1], terminal);
        owner.addTerminal(fields[1], terminal);
    }

    /*
     *  Registers the Friends to a Terminal
     * */
    private void registerFriends(String[] fields) throws UnknownTerminalException, DuplicateFriendException, OwnFriendException {

        Terminal terminal = findTerminal(fields[1]);
        if (terminal == null)
            throw new UnknownTerminalException(fields[1]);

        for (String terminalId : fields[2].split(",")) {
            if (findTerminal(terminalId) == null)
                throw new UnknownTerminalException(terminalId);

            terminal.addFriend(terminalId);

            //FIXME problema: interrompe o método qd os friends seguintes podem ser válidos ;
            //FIXME solucao: fazer exception receber lista de invalid keys ? - not important bc it wont happen

        }

    }

    private State stateFromString(String state) throws UnrecognizedEntryException {
        return switch (state) {
            case "ON" -> new Idle();
            case "OFF" -> new Off();
            case "SILENCE" -> new Silent();
            // FIXME does this happen? case "BUSY" -> new Busy();
            default -> throw new UnrecognizedEntryException(state);
        };
    }


    public void addTerminalToNetwork(String terminalKey, Terminal terminal) {
        _terminals.put(terminalKey, terminal);
    }

    public Terminal findTerminal(String terminalKey){
        return _terminals.get(terminalKey);
    }

    public Client findClient(String clientKey){
        return _clients.get(clientKey);
    }

}

