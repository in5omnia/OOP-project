package prr.terminals;

import prr.clients.Client;
import prr.exceptions.DuplicateFriendException;
import prr.exceptions.InvalidTerminalIdException;
import prr.exceptions.NoSuchFriendException;
import prr.exceptions.OwnFriendException;

import prr.notifications.Notification;
import prr.terminals.communication.Communication;
import prr.terminals.states.Idle;
import prr.terminals.states.State;

import java.io.Serializable;

import java.util.*;


/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091753L;

    private Client _owner;

    private String _key;

    private State _state = new Idle();

    private Map<String, Terminal> _friends = new HashMap<>();

    private List<Communication> _pastCommunications = new LinkedList<>();

    private Communication _ongoingCommunication = null;

    // these store notifications to send when the terminal becomes available
    Collection<Notification> _textNotifications;

    Collection<Notification> _interactiveNotifications;

    private int _payments = 0;

    private int _debts = 0;


    public Terminal(Client owner, String key) throws InvalidTerminalIdException {
        _owner = owner;
        if (!validTerminalKey(key)) {
            throw new InvalidTerminalIdException(key);
        }
        _key = key;
    }

    public Terminal(Client owner, String key, State state) throws InvalidTerminalIdException {
        this(owner, key);
        _state = state;
    }


    public String getKey() {
        return _key;
    }

    private boolean validTerminalKey(String key) {
        return key.matches("[0-9]{6}");
    }

    public void addFriend(String friendKey, Terminal friend) throws DuplicateFriendException, OwnFriendException {

        if (friendKey.equals(_key))
            throw new OwnFriendException(friendKey);

        else if (isFriend(friendKey))
            throw new DuplicateFriendException(friendKey);

        _friends.put(friendKey, friend);
    }

    void removeFriend(String friendKey) throws NoSuchFriendException {
        if (!isFriend(friendKey))
            throw new NoSuchFriendException();
        _friends.remove(friendKey);
    }

    boolean isFriend(String terminalKey) {
        return _friends.containsKey(terminalKey);
    }

    /**
     * Checks if this terminal can end the current interactive communication.
     *
     * @return true if this terminal is busy (i.e., it has an active interactive communication) and
     * it was the originator of this communication.
     **/
    public boolean canEndCurrentCommunication() {
        return  _ongoingCommunication != null;
    }

    /**
     * Checks if this terminal can start a new communication.
     *
     * @return true if this terminal is neither off neither busy, false otherwise.
     **/
    public boolean canStartCommunication() {
        return _state.canStartCommunication() && _ongoingCommunication == null;
    }

    public State getState() {    ///hmmmm prob not necessary
        return _state;
    }

    public abstract boolean canMessage();

    public abstract boolean canVoiceCommunicate();

    public abstract boolean canVideoCommunicate();

    public void sendTextCommunication(Terminal destination) {
    }

    public void startInteractiveCommunication(Terminal destination) {
    }

    public void turnOnTerminal() {
    }

    public void turnOffTerminal() {
    }

    public void endInteractiveCommunication(int duration) {
    }

    public void performPayment(int communicationKey) {
    }

    public void showOngoingCommunication() {
    }

    public void silenceTerminal() {
    }

    private void showTerminalBalance() {
    }

    public int calculatePayments() {
        return _payments;
    }

    public int calculateDebts() {
        return _debts;
    }

    private int calculateBalance() {
        return _payments - _debts;
    }

    public boolean isUnused() {
        return _pastCommunications.isEmpty() && _ongoingCommunication == null;
    }

    private String listFriends() {
        return String.join(",", _friends.keySet());
    }

    @Override
    public String toString() {
        String friends = _friends.isEmpty() ? "" : ("|" + listFriends());
        return "|" + _key + "|" + _owner.getId() + "|" + getState() + "|" + calculatePayments() + "|"
                + calculateDebts() + friends;
    }

}

