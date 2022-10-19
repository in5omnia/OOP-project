package prr.terminals;

import prr.exceptions.InvalidTerminalIdException;
import prr.clients.Client;
import prr.exceptions.DuplicateFriendException;
import prr.exceptions.NoSuchFriendException;
import prr.exceptions.OwnFriendException;
import prr.notifications.Notification;
import prr.terminals.communication.Communication;
import prr.terminals.states.Idle;
import prr.terminals.states.State;


import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */{

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
    private Client _owner;
    private String _key;
    private State _state =  new Idle();
    private List<String> _friends = new LinkedList<>();

    private List<Communication> _pastCommunications = new LinkedList<>();

    private Communication _ongoingCommunication = null;
    // FIXME
    Collection<Notification> _textNotif;    //instead of clients, we store notifications to send when the terminal
    Collection<Notification> _interactiveNotif;     //becomes available, which depends if it's text or interactive

    private int _payments = 0;
    private int _debts = 0;


    //TODO INFO: When communication fails, a methord checks if origin client has notifs enabled.
    // If so, a notif is created and stored in one of these collections. When there's a state switch in the terminal,
    // it checks for notifs to send and sends them if the new state allows them.



    //FIXME we should probably use a treeMap for friends or sth to ease search more expensive but faster?
    // FIXME Do we even need the pointers to the terminal
    public Terminal(Client owner, String key) throws InvalidTerminalIdException {
        _owner = owner;
        if (!validTerminalKey(key)) {
            throw new InvalidTerminalIdException(key);
        }
        _key = key;
    }

    /* FIXME
    public Terminal(Client owner, String key, Collection<Terminal> friends) {
        _owner = owner;
        _key = key;
        _friends = friends;
    }

     */


    public Terminal(Client owner, String key, State state) throws InvalidTerminalIdException {
       /* _owner = owner;
        if (!validTerminalKey(key)) {
            throw new InvalidTerminalIdException(key);
        }
        _key = key;*/
        this(owner, key);
        _state = state;

    }

    public String getKey() {
        return _key;
    }


    private boolean validTerminalKey(String key) {
        return key.matches("[0-9]{6}");   //why not "[0-9]+$"? //i added length
    }


/*
    public Terminal(String key) throws InvalidTerminalIdException{             //do we need this??
        // FIXME - verificar se são 6 digitos?

        _key = key;
    }
*/


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
        if (isFriend(friendKey))
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
            // FIXME add implementation code
        return true;
    }

    /**
     * Checks if this terminal can start a new communication.
     *
     * @return true if this terminal is neither off neither busy, false otherwise.
     **/
    public boolean canStartCommunication() {
            // FIXME add implementation code
        return true;
    }


    public State getState(){    ///hmmmm prob not necessary
        return _state;
        //FIXME did this for send...Communication
    }

    public abstract boolean canMessage();
    public abstract boolean canVoiceCommunicate();
    public abstract boolean canVideoCommunicate();



    //FIXME - ALL:

    void sendTextCommunication(Terminal destination){
        //if (destination.getState().canReceiveTextCommunication())
    }
    void startInteractiveCommunication(Terminal destination){}

    void turnOnTerminal(){}
    void turnOffTerminal(){}

    void endInteractiveCommunication(int duration){}
    void performPayment(int communicationKey){}

    void showOngoingCommunication(){}

    void silenceTerminal(){}

    void switchState(State state){

        _state = state;
    }

    int calculateBalance() {return 0;}
    void showTerminalBalance(){}

    public boolean isUnused() {
        return _pastCommunications.isEmpty() && _ongoingCommunication == null;
    }

    private String listFriends() {
        String listedFriends = "";
        for (String friend : _friends) {
            listedFriends += (",") + friend;
        }
        return listedFriends.substring(1);  //remove a vírgula inicial
    }

    public int calculatePayments() {
        return _payments;
    }
    public int calculateDebts() {
        return _debts;
    }

    @Override
    public String toString() {
        String friends = _friends.isEmpty() ? "" : ("|" + listFriends());
        return  "|" + _key + "|" + _owner.getId() + "|" + _owner.getId() + "|" + calculatePayments() +
                "|" + calculateDebts() + friends;

    }

}

