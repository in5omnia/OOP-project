package prr;

import prr.clients.Client;
import prr.clients.ClientDebtsComparator;
import prr.exceptions.UnrecognizedEntryException;
import prr.exceptions.DuplicateClientException;
import prr.exceptions.DuplicateTerminalException;
import prr.exceptions.DuplicateFriendException;
import prr.exceptions.InvalidTerminalIdException;
import prr.exceptions.UnknownClientException;
import prr.exceptions.UnknownTerminalException;
import prr.exceptions.OwnFriendException;
import prr.exceptions.AlreadyOffNotificationException;
import prr.exceptions.AlreadyOnNotificationException;


import prr.terminals.Terminal;
import prr.terminals.Basic;
import prr.terminals.Fancy;

import prr.terminals.communication.Communication;
import prr.terminals.states.State;
import prr.terminals.states.Off;
import prr.terminals.states.Idle;
import prr.terminals.states.Silent;

import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.BufferedReader;

import java.util.Map;
import java.util.TreeMap;
import java.util.Collection;
import java.util.LinkedList;



/**
 * Class Network implements a network.
 */
public class Network implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091753L;

    /**
     * Treemap that stores clients using their ID's as keys
     */
    private Map<String, Client> _clients = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * Treemap that stores terminals using their ID's as keys
     */
    private Map<String, Terminal> _terminals = new TreeMap<>();

    /* List of communications */
    private Collection<Communication> _communications = new LinkedList<>();

    /**
     * CommunicationId number
     */
    private int _communicationId = 1;

    /**
     * Read text input file and create corresponding domain entities.
     *
     * @param filename name of the text input file
     * @throws UnrecognizedEntryException if some entry is not correct
     * @throws IOException                if there is an IO error while processing the text file
     */
    void importFile(String filename) throws UnrecognizedEntryException, IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|");
                registerEntry(fields);
            }
        } catch (DuplicateClientException | DuplicateTerminalException | InvalidTerminalIdException |
                 UnknownClientException | UnknownTerminalException | DuplicateFriendException | OwnFriendException e) {
            throw new UnrecognizedEntryException(filename);
        }
    }

    /**
     * Registers the entries by delegating to the correct method.
     *
     * @param fields name of the information to be registered in the network
     * @throws UnrecognizedEntryException if some entry is not correct
     * @throws DuplicateClientException   if a client that already exists is being registered
     * @throws DuplicateTerminalException if a terminal that already exists is being registered
     * @throws InvalidTerminalIdException if the id of a terminal being registered is invalid
     * @throws UnknownClientException     if a terminal is being registered to a client that doesn't exist
     * @throws UnknownTerminalException   if a terminal that doesn't exist is being registered as a friend or if friends are being registered to it
     * @throws DuplicateFriendException   if a terminal's friend is being registered as a friend again
     * @throws OwnFriendException         if a terminal is registering as its own friend
     */
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
     * Registers the Clients
     *
     * @param clientId - the ID the client will be registered with
     * @param name     - the name the client will be registered with
     * @param taxId    - the taxId the client will be registered with
     * @throws DuplicateClientException - if the clientId being registered already exists
     */
    public void registerClient(String clientId, String name, int taxId) throws DuplicateClientException {

        if (clientExists(clientId))
            throw new DuplicateClientException(clientId);

        Client client = new Client(clientId, name, taxId);
        _clients.put(clientId, client);
    }

    /**
     * Registers the Terminals
     *
     * @param fields - information about the terminal being registered (type, terminalID, clientID and state[optional,
     *               when registry happens through the Terminal Management Menu])
     * @throws InvalidTerminalIdException - if the given terminalID is invalid
     * @throws DuplicateTerminalException - if the given terminalID already exists
     * @throws UnknownClientException     - if the terminal is being registered to a client that doesn't exist
     * @throws UnrecognizedEntryException - if the state or type of terminal given is invalid
     */
    public void registerTerminal(String[] fields) throws InvalidTerminalIdException, DuplicateTerminalException,
            UnrecognizedEntryException, UnknownClientException {

        if (terminalExists(fields[1]))
            throw new DuplicateTerminalException(fields[1]);

        Client owner = findClient(fields[2]);

        Terminal terminal = switch (fields[0]) {
            case "BASIC" -> new Basic(owner, fields[1]);
            case "FANCY" -> new Fancy(owner, fields[1]);
            default -> throw new UnrecognizedEntryException(fields[0]);
        };

        if (fields.length > 3)
            terminal.setState(stateFromString(fields[3], terminal));
        else
            terminal.setState(new Idle(terminal));

        addTerminalToNetwork(fields[1], terminal);
        owner.addTerminal(fields[1], terminal);
    }


    /**
     * Registers the Friends to a Terminal
     *
     * @param fields information about a terminal and the terminals being registered as its friends
     * @throws UnknownTerminalException if one of the terminals given doesn't exist
     * @throws DuplicateFriendException if a terminal being registered as friend to another is already friends with it
     * @throws OwnFriendException       if the terminal is being registered as its own friend
     */
    public void registerFriends(String[] fields) throws UnknownTerminalException, DuplicateFriendException, OwnFriendException {

        Terminal terminal = findTerminal(fields[1]);

        for (String terminalId : fields[2].split(",")) {
            terminal.addFriend(terminalId, findTerminal(terminalId));
        }

    }

    /**
     * Receives a string naming a state and returns the corresponding State
     *
     * @param state string that represents the state
     * @param terminal terminal that the state will be associated with
     * @return the new State that corresponds to the given string
     * @throws UnrecognizedEntryException if the given state is invalid (Busy is invalid here because a terminal
     *                                    can't be created already busy)
     */
    private State stateFromString(String state,Terminal terminal) throws UnrecognizedEntryException {

        return switch (state) {
            case "ON" -> new Idle(terminal);
            case "OFF" -> new Off(terminal);
            case "SILENCE" -> new Silent(terminal);
            default -> throw new UnrecognizedEntryException(state);
        };
    }


    /**
     * Adds a terminal to the network.
     *
     * @param terminalKey the terminal's ID
     * @param terminal   the terminal to be added
     */
    private void addTerminalToNetwork(String terminalKey, Terminal terminal) {
        _terminals.put(terminalKey, terminal);
    }

    /**
     * Checks if a terminal with the given ID exists in the network.
     *
     * @param terminalKey the terminal's ID
     * @return true is the terminal exists and false otherwise
     */
    public boolean terminalExists(String terminalKey) {
        return _terminals.get(terminalKey) != null;
    }   //FIXME public?

    /**
     * Finds a terminal with the given ID in the network.
     *
     * @param terminalKey the terminal's ID
     * @return the terminal with the given key
     * @throws UnknownTerminalException if there isn't a terminal with the given key in the network
     */
    public Terminal findTerminal(String terminalKey) throws UnknownTerminalException {
        Terminal terminal = _terminals.get(terminalKey);
        if (terminal == null)
            throw new UnknownTerminalException(terminalKey);
        return terminal;
    }

    /**
     * Checks if a client with the given ID exists in network.
     *
     * @param clientKey client's ID
     * @return true if the client exists and false otherwise
     */
    private boolean clientExists(String clientKey) {
        return _clients.get(clientKey) != null;
    }

    /**
     * Finds a client with the given ID in the network.
     *
     * @param clientKey client's ID
     * @return the client with the given key
     * @throws UnknownClientException if there isn't a client with the given key in the network
     */
    public Client findClient(String clientKey) throws UnknownClientException {
        Client client = _clients.get(clientKey);
        if (client == null)
            throw new UnknownClientException(clientKey);
        return client;
    }

    /**
     * Shows the client with the given ID.
     *
     * @param clientKey client's ID
     * @return a String that represents the client and his/her notifications.
     * @throws UnknownClientException if there isn't a client with the given key in the network
     */
    public String showClient(String clientKey) throws UnknownClientException {
        Client client = findClient(clientKey);
        return client.toString() + client.showNotifications();
    }


    /**
     * Shows all the clients in the network.
     *
     * @return a String that represents all the clients.
     */
    public Collection<String> showAllClients() {
        Collection<String> allClients = new LinkedList<>();
        for (Client client : _clients.values()) {
            allClients.add(client.toString());
        }
        return allClients;
    }

    /**
     * Shows all the Communications on the network.
     *
     * @return a String that represents all the Communications.
     */
    public Collection<String> showAllCommunications() {
        Collection<String> allCommunications = new LinkedList<>();

        for (Communication communication : _communications) {
            allCommunications.add(communication.toString());
        }

        return allCommunications;
    }

    /**
     * Shows all the terminals in the network.
     *
     * @return a collection of String that represent each terminal.
     */
    public Collection<String> showAllTerminals() {
        Collection<String> allTerminals = new LinkedList<>();
        for (Terminal terminal : _terminals.values()) {
            allTerminals.add(terminal.toString());
        }
        return allTerminals;
    }

    /**
     * Shows all the terminals that haven't had communications.
     *
     * @return a String that represents all the unused terminals
     */
    public Collection<String> showAllUnusedTerminals() {
        Collection<String> allTerminals = new LinkedList<>();
        for (Terminal terminal : _terminals.values()) {
            if (terminal.isUnused())
                allTerminals.add(terminal.toString());
        }
        return allTerminals;
    }

    // UNSAFE FIXME
    private long getClientPayments(Client client) {
        return client.getPayments();
    }

    // UNSAFE FIXME
    private long getClientDebts(Client client) {
        return client.getDebts();
    }

    /**
     * Gets the ammount of payments and debts from a given client.
     * @param clientKey client's ID
     * @return a String that represents all the clients with debts
     */
    public long[] retrievePaymentsAndDebts(String clientKey) throws UnknownClientException {
        Client client = findClient(clientKey);
        return new long[]{client.getPayments(), client.getDebts()};
    }


    // TODO: Exception and Documentation
    /**
     * Enables Notifications of a given client a given client.
     * @param clientKey client's ID
     * @return a String that represents all the clients with debts
     * @throws UnknownClientException if there isn't a client with the given key in the network
     * @throws AlreadyOnNotificationException if the client's Notifications were already on
     */
    public void enableClientNotifications(String clientKey) throws UnknownClientException , AlreadyOnNotificationException {
        Client client = findClient(clientKey);
        client.enableNotifications();
    }

    // TODO: Exception and Documentation
    /**
     * Enables Notifications of a given client a given client.
     * @param clientKey client's ID
     * @return a String that represents all the clients with debts
     * @throws UnknownClientException if there isn't a client with the given key in the network
     * @throws AlreadyOffNotificationException if the client's Notifications were already off
     */
    public void disableClientNotifications(String clientKey) throws UnknownClientException , AlreadyOffNotificationException {
        Client client = findClient(clientKey);
        client.disableNotifications();
    }

    // TODO: Documentation
    /**
     * Returns a communicationId and generates a new one.
     *
     * @return  a communicationId
     */
    public int retrieveCommunicationId(){  //FIXME not sure this should be public
        return _communicationId++;
    }


    /**
     * Shows all the clients that have debts.
     *
     * @return a String that represents all the clients with debts
     */
    public Collection<String> showClientsWithDebts() {

        Map<Client, String> clientswithDebts = new TreeMap<>(new ClientDebtsComparator());
        for (Client client : _clients.values()) {
            if (client.getDebts() > 0)
                clientswithDebts.put(client, client.toString());
        }
        return clientswithDebts.values();
    }



    /**
     * Shows all the clients that don't have debts.
     *
     * @return a String that represents all the clients without debts
     */
    public Collection<String> showClientsWithoutDebts() {
        Collection<String> allClients = new LinkedList<>();
        for (Client client : _clients.values()) {
            if (client.getDebts() <= 0) //FIXME ==?
                allClients.add(client.toString());
        }
        return allClients;
    }

    /**
     * Shows all the terminals with a positive balance.
     *
     * @return a String that represents all the terminals with a positive balance
     */
    public Collection<String> showTerminalsWithPositiveBalance() {
        Collection<String> allTerminals = new LinkedList<>();
        for (Terminal terminal : _terminals.values()) {
            if (terminal.calculateBalance() > 0)
                allTerminals.add(terminal.toString());
        }
        return allTerminals;
    }

    //FIXME - do we want communications inside network?

    /**
     * Adds a communication to the network.
     * @param communication communication to be added
     */
    public void addCommunication(Communication communication){
        _communications.add(communication);
    }



    public Collection<String> showCommunicationsFromClient(String clientKey) throws UnknownClientException {
        return findClient(clientKey).showClientCommunications();
    }

    public Collection<String> showCommunicationsToClient(String clientKey) throws UnknownClientException {
        if (!clientExists(clientKey))
            throw new UnknownClientException(clientKey);
        Collection<String> allCommunications = new LinkedList<>();

        for (Communication communication : _communications){
            Client destinationClient = communication.getDestination().getOwner();
            if (destinationClient.getId().equals(clientKey)){
                allCommunications.add(communication.toString());
            }
        }
        return allCommunications;
    }


    public long[] retrieveGlobalPaymentsAndDebts() {
        long globalPayments=0, globalDebts=0;
        for (Client client : _clients.values()) {
            globalPayments += client.getPayments();
            globalDebts += client.getDebts();
        }
        return new long[]{globalPayments, globalDebts};
    }

}

