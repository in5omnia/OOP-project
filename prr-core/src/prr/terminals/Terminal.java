package prr.terminals;

import prr.Network;
import prr.clients.Client;
import prr.exceptions.*;

import prr.notifications.InteractiveNotification;
import prr.notifications.Notification;
import prr.notifications.TextNotification;
import prr.terminals.communication.Text;
import prr.terminals.communication.Video;
import prr.terminals.communication.Voice;
import prr.terminals.states.State;
import prr.terminals.states.Idle;
import prr.terminals.communication.Communication;
import prr.terminals.states.StateExceptionVisitor;

import java.io.Serializable;

import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Collection;


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

    private State _state = new Idle(this);

    private Map<String, Terminal> _friends = new TreeMap<>();

    private List<Communication> _pastCommunications = new LinkedList<>();

    private Communication _ongoingCommunication = null;

    // these store notifications to send when the terminal becomes available
    Collection<TextNotification> _textNotifications;

    Collection<InteractiveNotification> _interactiveNotifications;

    private int _payments = 0;

    private int _debts = 0;


    public Terminal(Client owner, String key) throws InvalidTerminalIdException {
        _owner = owner;
        if (!validTerminalKey(key)) {
            throw new InvalidTerminalIdException(key);
        }
        _key = key;
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

    public State getState() {
        return _state;
    }

    public abstract boolean canMessage();

    public abstract boolean canVoiceCommunicate();

    public abstract boolean canVideoCommunicate();

    private boolean canReceiveTextCommunication(){
        return _state.canReceiveTextCommunication();
    }

    public boolean canReceiveInteractiveCommunication() throws DestinationTerminalOffException {
        return _state.canReceiveInteractiveCommunication();
    }

    public void sendTextCommunication(Network network, String destinationTerminal, String message)
            throws DestinationTerminalOffException, CannotCommunicateException, UnknownTerminalException {

        if (!canStartCommunication())
            throw new CannotCommunicateException();

        Terminal destination = network.findTerminal(destinationTerminal);

        if (!(destination.canReceiveTextCommunication())){
            //method for this? FIXME
            if (_owner.notificationsEnabled())
                destination.registerTextNotificationToSend(this);

            throw new DestinationTerminalOffException();
        }

        Communication communication = new Text(this, destination, network.retrieveCommunicationId(), message);
        _pastCommunications.add(communication);
        _debts += communication.getCost();  //FIXME
    }



    public void startInteractiveCommunication(Network network, String destinationTerminalKey, String communicationType)
            throws CannotCommunicateException, UnknownTerminalException,
            UnsupportedAtOriginException, UnsupportedAtDestinationException, DestinationTerminalOffException,
            DestinationTerminalBusyException, DestinationTerminalSilentException {

        Terminal destination = network.findTerminal(destinationTerminalKey);
        Communication communication = null; //FIXME

        switch (communicationType) {
            case "VOICE" -> {
                destination = network.findTerminal(destinationTerminalKey);
                if (!canVoiceCommunicate())
                    throw new UnsupportedAtOriginException(_key, communicationType);
                if (!destination.canVoiceCommunicate())
                    throw new UnsupportedAtDestinationException(destinationTerminalKey, communicationType);
                communication = new Voice(this, destination, network.retrieveCommunicationId());
            }
            case "VIDEO" -> {
                destination = network.findTerminal(destinationTerminalKey);
                if (!canVideoCommunicate())
                    throw new UnsupportedAtOriginException(_key, communicationType);
                if (!destination.canVideoCommunicate())
                    throw new UnsupportedAtDestinationException(destinationTerminalKey, communicationType);
                communication = new Video(this, destination, network.retrieveCommunicationId());
            }
        }

        if (!canStartCommunication())
            throw new CannotCommunicateException();

        if (destinationTerminalKey.equals(_key)) {
            throw new DestinationTerminalBusyException();   //Presents the same message as a busy terminal
        }

        if (!(destination.canReceiveInteractiveCommunication())){
            //method for this? FIXME
            if (_owner.notificationsEnabled())
                destination.registerInteractiveNotificationToSend(this);
            //throws DestinationTerminalOffException, DestinationTerminalBusyException, DestinationTerminalSilentException
            //with Visitor
            destination.getStateException();
        }
        _state.startInteractiveCommunication();
        _ongoingCommunication = communication;
    }


    public long endInteractiveCommunication(int duration) {
        //FIXME do i have to check if its ongoing -> exceptions?
        _ongoingCommunication.setUnits(duration);
        long cost = _ongoingCommunication.getCost();
        _state.endInteractiveCommunication();
        _pastCommunications.add(_ongoingCommunication);
        _ongoingCommunication = null;
        return cost;
    }


    public void getStateException() throws DestinationTerminalSilentException, DestinationTerminalBusyException,
            DestinationTerminalOffException {
            _state.accept(new StateExceptionVisitor());
    }

    //provisorio
   /* public void handleNotifications(Terminal destination){
        //this & owner=origin
        if (_owner.notificationsEnabled())
            destination.registerTextNotificationToSend(_key);
    }*/


    public void registerTextNotificationToSend(Terminal origin){
        TextNotification notification = new TextNotification(origin,this);
        _textNotifications.add(notification);
    }

    public void registerInteractiveNotificationToSend(Terminal origin){
        InteractiveNotification notification = new InteractiveNotification(origin, this);
        _interactiveNotifications.add(notification);
    }


    public int calculatePayments() {
        return _payments;
    }

    public int calculateDebts() {
        return _debts;
    }

    public long calculateBalance() {
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


    public void addFriendToTerminal(Network network, String friendKey) throws UnknownTerminalException {
        Terminal friend = network.findTerminal(friendKey);
        try {
            addFriend(friendKey, friend);
        } catch (DuplicateFriendException | OwnFriendException e){
            //do nothing
        }
    }

    public void removeFriendFromTerminal(Network network, String friendKey) throws UnknownTerminalException {
        if (!network.terminalExists(friendKey))
            throw new UnknownTerminalException(friendKey);
        try {
            removeFriend(friendKey);
        } catch (NoSuchFriendException e){
            //do nothing
        }
    }

    public String showOngoingCommunication() throws NoOngoingCommunicationException {
        if (_ongoingCommunication == null)
            throw new NoOngoingCommunicationException();
        return _ongoingCommunication.toString();
    }

    public void setState(State state) {
        _state = state;
    }

    public void turnOn() throws AlreadyOnTerminalException {
        _state.turnOn();
    }

    public void turnOff() throws AlreadyOffTerminalException {
        _state.turnOff();
    }

    public void toSilent() throws AlreadySilentTerminalException {
        _state.toSilent();
    }

    public void sendTextNotifications(State currentState) {
        for (TextNotification notification : _textNotifications) {
            notification.chooseNotificationType(currentState);
        }
    }

    public void sendInteractiveNotifications(State currentState) {
        for (InteractiveNotification notification : _interactiveNotifications) {
            notification.chooseNotificationType(currentState);
        }
    }

}

