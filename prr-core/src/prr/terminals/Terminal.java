package prr.terminals;

import prr.app.exceptions.InvalidTerminalKeyException;
import prr.clients.Client;
import prr.notifications.Notification;
import prr.terminals.communication.Communication;
import prr.terminals.states.Idle;
import prr.terminals.states.State;


import java.io.Serializable;
import java.util.Collection;

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


    //FIXME we should probably use a treeMap for friends or sth to ease search more expensive but faster?
    // FIXME Do we even need the pointers to the terminal
    public Terminal(Client owner, String key) throws InvalidTerminalKeyException {
        _owner = owner;
        if (!validTerminalKey(key)) {
            throw new InvalidTerminalKeyException(key);
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

    public Terminal(Client owner, String key, State state, Collection<Terminal> friends) throws InvalidTerminalKeyException {
        _owner = owner;
        if (!validTerminalKey(key)) {
            throw new InvalidTerminalKeyException(key);
        }
        _key = key;
        _state = state;
        _friends = friends;
    }

    private boolean validTerminalKey(String key) {
        return key.matches("[0-9]+");
    }

    private Collection<Terminal> _friends;

    private Collection<Communication> _pastCommunications;

    private Communication _ongoingCommunication = null;

    // FIXME
    Collection<Notification> _textNotif;    //instead of clients, we store notifications to send when the terminal
    Collection<Notification> _interactiveNotif;     //becomes available, which depends if it's text or interactive

    //TODO INFO: When communication fails, a methord checks if origin client has notifs enabled.
    // If so, a notif is created and stored in one of these collections. When there's a state switch in the terminal,
    // it checks for notifs to send and sends them if the new state allows them.

    // Friends List

    // FIXME define attributes
    // FIXME define contructor(s)
    // FIXME define methods

    public Terminal(String key) {
        // FIXME - verificar se são 6 digitos?
        _key = key;
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
    void addFriend(String id){
        // Can't add itself
    }
    boolean isFriend(Terminal terminal ) { return false; }

    void endInteractiveCommunication(int duration){}
    void performPayment(int communicationKey){}
    void removeFriend(String id){}
    void showOngoingCommunication(){}

    void silenceTerminal(){}

    void switchState(State state){

        _state = state;
    }

    int calculateBalance() {return 0;}
    void showTerminalBalance(){}

}
