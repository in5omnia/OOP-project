package prr;

import prr.clients.Client;
import prr.terminals.Terminal;
import prr.terminals.Basic;
import prr.terminals.Fancy;
import prr.terminals.states.State;
import prr.terminals.states.Idle;
import prr.terminals.states.Off;
import prr.terminals.states.Silent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.io.IOException;
import prr.exceptions.UnrecognizedEntryException;
import prr.exceptions.DuplicateClientException;
import prr.exceptions.UnknownClientException;
import prr.exceptions.DuplicateTerminalException;
import prr.exceptions.InvalidTerminalIdException;
import prr.exceptions.UnknownTerminalException;
import prr.exceptions.DuplicateFriendException;
import prr.exceptions.OwnFriendException;


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

    /**
     * Read text input file and create corresponding domain entities.
     *
     * @param filename name of the text input file
     * @throws UnrecognizedEntryException if some entry is not correct
     * @throws IOException                if there is an IO erro while processing the text file
     */
    void importFile(String filename) throws UnrecognizedEntryException, IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|");
                registerEntry(fields);
            }
        } catch (DuplicateClientException | DuplicateTerminalException | InvalidTerminalIdException |
               UnknownClientException | UnknownTerminalException | DuplicateFriendException | OwnFriendException e1) {
            throw new UnrecognizedEntryException(filename);
        }
    }

    /**
     *  Registers the entries by delegating to the correct method.
     *
     * @param fields name of the information to be registered in the network
     * @throws UnrecognizedEntryException if some entry is not correct
     * @throws DuplicateClientException if a client that already exists is being registered
     * @throws DuplicateTerminalException if a terminal that already exists is being registered
     * @throws InvalidTerminalIdException if the id of a terminal being registered is invalid
     * @throws UnknownClientException if a terminal is being registered to a client that doesn't exist
     * @throws UnknownTerminalException if a terminal that doesn't exist is being registered as a friend or if friends are being registered to it
     * @throws DuplicateFriendException if a terminal's friend is being registered as a friend again
     * @throws OwnFriendException if a terminal is registering as its own friend
     * */
    void registerEntry(String[] fields) throws DuplicateClientException, UnrecognizedEntryException,
            DuplicateTerminalException, InvalidTerminalIdException, UnknownClientException, UnknownTerminalException,
            DuplicateFriendException, OwnFriendException {

        switch (fields[0]) {
            case "CLIENT" -> registerClient(fields[1], fields[2], Integer.parseInt(fields[3]));
            case "BASIC", "FANCY" -> registerTerminal(fields);
            case "FRIENDS" -> registerFriends(fields);
            default -> throw new UnrecognizedEntryException(fields[0]);
        }
    }


    /**
     *  Registers the Clients
     * */
    public void registerClient(String clientId, String name, int taxId) throws DuplicateClientException {

        if (clientExists(clientId))
            throw new DuplicateClientException(clientId);

        Client client = new Client(clientId, name, taxId);
        _clients.put(clientId, client);
    }

    /**
     *  Registers the Terminals
     * */
    public void registerTerminal(String[] fields) throws InvalidTerminalIdException, DuplicateTerminalException,
            UnrecognizedEntryException, UnknownClientException {

        if (terminalExists(fields[1]))
            throw new DuplicateTerminalException(fields[1]);

        Client owner = findClient(fields[2]);
        State state;

        if (fields.length > 3)
            state = stateFromString(fields[3]);
        else
            state = new Idle();

        Terminal terminal = switch (fields[0]) {
            case "BASIC" -> new Basic(owner, fields[1], state);
            case "FANCY" -> new Fancy(owner, fields[1], state);
            default -> throw new UnrecognizedEntryException(fields[0]);
        };

        addTerminalToNetwork(fields[1], terminal);
        owner.addTerminal(fields[1], terminal);
    }


    /**
     *  Registers the Friends to a Terminal
     * */
    private void registerFriends(String[] fields) throws UnknownTerminalException, DuplicateFriendException, OwnFriendException {

        Terminal terminal = findTerminal(fields[1]);

        for (String terminalId : fields[2].split(",")) {
            if (!terminalExists(terminalId))
                throw new UnknownTerminalException(terminalId);

            terminal.addFriend(terminalId);
        }

    }

    private State stateFromString(String state) throws UnrecognizedEntryException {

        return switch (state) {
            case "ON" -> new Idle();
            case "OFF" -> new Off();
            case "SILENCE" -> new Silent();
            default -> throw new UnrecognizedEntryException(state);
        };
    }


    public void addTerminalToNetwork(String terminalKey, Terminal terminal) {
        _terminals.put(terminalKey, terminal);
    }

    public boolean terminalExists(String terminalKey) {
        return _terminals.get(terminalKey) != null;
    }

    public Terminal findTerminal(String terminalKey) throws UnknownTerminalException {
        Terminal terminal = _terminals.get(terminalKey);
        if (terminal == null)
            throw new UnknownTerminalException(terminalKey);
        return terminal;
    }

    public boolean clientExists(String clientKey) {
        return _clients.get(clientKey) != null;
    }

    public Client findClient(String clientKey) throws UnknownClientException {
        Client client = _clients.get(clientKey);
        if (client == null)
            throw new UnknownClientException(clientKey);
        return client;
    }


    public String showClient(String clientKey) throws UnknownClientException {
        Client client = findClient(clientKey);
        return client.toString() + client.showNotifications();
    }


    public String showAllClients() {
        String allClients = "";
        for (Client client : _clients.values()) {
            allClients += "\n" + client.toString();
        }
        return allClients.substring(1);
    }


    public Collection<String> showAllTerminals() {
        Collection<String> allTerminals = new LinkedList<>();
        for (Terminal terminal : _terminals.values()) {
            allTerminals.add(terminal.toString());
        }
        return allTerminals;
    }

    public String showAllUnusedTerminals() {
        String allTerminals = "";
        for (Terminal terminal : _terminals.values()) {
            if (terminal.isUnused())
                allTerminals += "\n" + terminal.toString();
        }
        return allTerminals.substring(1);   // removes the extra \n at the start
    }

}

