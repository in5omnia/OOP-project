package prr.terminals;

import prr.clients.Client;
import prr.notifications.Notification;
import prr.terminals.communication.Communication;
import prr.terminals.states.State;
import prr.terminals.states.Idle;
import prr.exceptions.InvalidTerminalIdException;
import prr.exceptions.DuplicateFriendException;
import prr.exceptions.NoSuchFriendException;
import prr.exceptions.OwnFriendException;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
    private Client _owner;
    private String _key;
    private State _state =  new Idle();
    private List<String> _friends = new LinkedList<>();

    private List<Communication> _pastCommunications = new LinkedList<>();

    private Communication _ongoingCommunication = null;

    // these store notifications to send when the terminal becomes available
    Collection<Notification> _textNotif;
    Collection<Notification> _interactiveNotif;

    private int _payments = 0;
    private int _debts = 0;

    // FIXME Do we even need the pointers to the terminal


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

    public Client getOwner(){
        return _owner;
    }


    private boolean validTerminalKey(String key) {
        return key.matches("[0-9]{6}");
    }


    public void addFriend(String friendKey) throws DuplicateFriendException, OwnFriendException {

        if (friendKey.equals(_key))
            throw new OwnFriendException(friendKey);

        else if (isFriend(friendKey))
            throw new DuplicateFriendException(friendKey);

        _friends.add(friendKey);
    }

    void addFriends(List<String> friends) throws DuplicateFriendException, OwnFriendException {
        for (String friendKey : friends) {
            addFriend(friendKey);
        }
    }

    void removeFriend(String friendKey) throws NoSuchFriendException {    // should these add/remove functions throw exceptions?
        if (!isFriend(friendKey))
            throw new NoSuchFriendException();
        _friends.remove(friendKey);

        // Can't add itself
    }

    boolean isFriend(String terminalKey) {
        return _friends.contains(terminalKey);
    }

        /**
         * Checks if this terminal can end the current interactive communication.
         *
         * @return true if this terminal is busy (i.e., it has an active interactive communication) and
         *          it was the originator of this communication.
         **/
    public boolean canEndCurrentCommunication() {
        return _ongoingCommunication != null;
    }

    /**
     * Checks if this terminal can start a new communication.
     *
     * @return true if this terminal is neither off neither busy, false otherwise.
     **/
    public boolean canStartCommunication() {
        return _state.canStartCommunication();
        //return _ongoingCommunication == null;
    }


    public State getState(){    ///hmmmm prob not necessary
        return _state;

    }

    public abstract boolean canMessage();
    public abstract boolean canVoiceCommunicate();
    public abstract boolean canVideoCommunicate();



    //FIXME - ALL:

    void sendTextCommunication(Terminal destination){
        //TODO send text
        canStartCommunication();
        _owner.textCommunication();
        _owner.checkForLevelUpdates_Text();
    }

    void turnOnTerminal(){}
    void turnOffTerminal(){}

    public void endInteractiveCommunication(int duration){
        //TODO end communication
        _owner.checkForLevelUpdates_Video();    //may want to do this only for videos in the future
    }
    void performPayment(int communicationKey){
        //perform payment TODO
        _owner.balanceOver500();
    }

    void showOngoingCommunication(){}

    void silenceTerminal(){}
    private void showTerminalBalance(){}


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

        String listedFriends = "";
        for (String friend : _friends) {
            listedFriends += (",") + friend;
        }
        return listedFriends.substring(1);  // removes the extra comma at the start
    }

    @Override
    public String toString() {
        String friends = _friends.isEmpty() ? "" : ("|" + listFriends());
        return  "|" + _key + "|" + _owner.getId() + "|" + getState() + "|" + calculatePayments() + "|"
                + calculateDebts() + friends;
    }

}

