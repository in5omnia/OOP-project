package prr.terminals;

import prr.exceptions.OwnFriendException;
import prr.exceptions.NoSuchFriendException;
import prr.exceptions.DuplicateFriendException;
import prr.exceptions.UnknownTerminalException;
import prr.exceptions.AlreadyOnTerminalException;
import prr.exceptions.CannotCommunicateException;
import prr.exceptions.InvalidTerminalIdException;
import prr.exceptions.AlreadyOffTerminalException;
import prr.exceptions.UnsupportedAtOriginException;
import prr.exceptions.DestinationTerminalOffException;
import prr.exceptions.AlreadySilentTerminalException;
import prr.exceptions.NoOngoingCommunicationException;
import prr.exceptions.DestinationTerminalBusyException;
import prr.exceptions.UnsupportedAtDestinationException;
import prr.exceptions.DestinationTerminalSilentException;
import prr.exceptions.CommunicationNotFoundTerminalException;

import prr.Network;
import prr.clients.Client;
import prr.clients.Level;
import prr.notifications.Notification;
import prr.notifications.NotificationType;
import prr.terminals.communication.Text;
import prr.terminals.communication.Video;
import prr.terminals.communication.Voice;
import prr.terminals.communication.Communication;
import prr.terminals.communication.InteractiveCommunication;
import prr.terminals.states.StateExceptionVisitor;
import prr.terminals.states.State;
import prr.terminals.states.Idle;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.TreeSet;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;


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

    private Map<Integer, Communication> _pastCommunications = new TreeMap<>();

    private Communication _ongoingCommunication = null;

    private List<Communication> _communicationsReceived = new ArrayList<>();

    // these store notifications to send when the terminal becomes available
    private List<Client> _textNotificationsToSend = new ArrayList<>();

    private List<Client> _interactiveNotificationsToSend = new ArrayList<>();

    private long _payments = 0;

    private long _debts = 0;

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

    //FIXME
    public Client getOwner(){
        return _owner;
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

    //FIXME
    public boolean isFriend(String terminalKey) {
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
        return _state.canStartCommunication();
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

    public boolean canReceiveInteractiveCommunication() throws DestinationTerminalOffException {    //FIXME tf is this
        return _state.canReceiveInteractiveCommunication();
    }

    public void sendTextCommunication(Network network, String destinationTerminalKey, String message)
            throws DestinationTerminalOffException, CannotCommunicateException, UnknownTerminalException {

        if (!canStartCommunication())
            throw new CannotCommunicateException();

        Terminal destination = network.findTerminal(destinationTerminalKey);

        if (!(destination.canReceiveTextCommunication())){
            //method for this? FIXME
            if (_owner.notificationsEnabled())
                destination.registerTextNotificationToSend(_owner);

            throw new DestinationTerminalOffException();
        }

        int communicationId = network.retrieveCommunicationId();
        Text communication = new Text(this, destination, communicationId, message);
        _pastCommunications.put(communicationId, communication);
        destination.receiveCommunication(communication);    //so that we know if it's unused or not
        long cost = communication.getCost();
        _debts += cost;  //FIXME
        _owner.addClientDebt(cost);
        network.addCommunication(communication);
        Level level = _owner.getLevel();
        level.negativeBalance();
        level.detectCommunication(communication);
        _owner.getLevel().positiveBalanceAnd2Text();
    }



    public void startInteractiveCommunication(Network network, String destinationTerminalKey, String communicationType)
            throws CannotCommunicateException, UnknownTerminalException,
            UnsupportedAtOriginException, UnsupportedAtDestinationException, DestinationTerminalOffException,
            DestinationTerminalBusyException, DestinationTerminalSilentException {

        Terminal destination = network.findTerminal(destinationTerminalKey);
        //Communication communication = null; //FIXME

        switch (communicationType) {
            case "VOICE" -> {
                if (!canVoiceCommunicate())
                    throw new UnsupportedAtOriginException(_key, communicationType);
                if (!destination.canVoiceCommunicate())
                    throw new UnsupportedAtDestinationException(destinationTerminalKey, communicationType);

                validateStartInteractiveCommunication(destinationTerminalKey, destination);
                Voice communication = new Voice(this, destination, network.retrieveCommunicationId());

                _state.startInteractiveCommunication();
                destination.receiveCommunication(communication);
                _ongoingCommunication = communication;
                network.addCommunication(communication);
                ///
                _owner.getLevel().detectCommunication(communication);

            }
            case "VIDEO" -> {
                if (!canVideoCommunicate())
                    throw new UnsupportedAtOriginException(_key, communicationType);
                if (!destination.canVideoCommunicate())
                    throw new UnsupportedAtDestinationException(destinationTerminalKey, communicationType);

                validateStartInteractiveCommunication(destinationTerminalKey, destination);
                Video communication = new Video(this, destination, network.retrieveCommunicationId());

                _state.startInteractiveCommunication();
                destination.receiveCommunication(communication);
                _ongoingCommunication = communication;
                network.addCommunication(communication);
                ///
                _owner.getLevel().detectCommunication(communication);
            }
        }
    }

    public void receiveCommunication(Text communication){
        _communicationsReceived.add(communication);
    }

    public void receiveCommunication(InteractiveCommunication communication){
        _communicationsReceived.add(communication);
        getState().startInteractiveCommunication();
    }

    private void validateStartInteractiveCommunication(String destinationTerminalKey, Terminal destination)
            throws CannotCommunicateException, DestinationTerminalBusyException, DestinationTerminalOffException,
            DestinationTerminalSilentException {

        if (!canStartCommunication())
            throw new CannotCommunicateException();

        if (destinationTerminalKey.equals(_key)) {
            throw new DestinationTerminalBusyException();   //Presents the same message as a busy terminal
        }

        if (!(destination.canReceiveInteractiveCommunication())){
            //method for this? FIXME
            if (_owner.notificationsEnabled())
                destination.registerInteractiveNotificationToSend(_owner);
            //throws DestinationTerminalOffException, DestinationTerminalBusyException, DestinationTerminalSilentException
            //with Visitor
            destination.getStateException();
        }
    }



    public long endInteractiveCommunication(int duration) {
        //FIXME do i have to check if its ongoing -> exceptions?
        _ongoingCommunication.defineUnitsAndCost(duration);
        _ongoingCommunication.endCommunication();
        long cost = _ongoingCommunication.getCost();
        _state.endInteractiveCommunication();
        _ongoingCommunication.getDestination().getState().endInteractiveCommunication();    //FIXME this is horrendous
        _pastCommunications.put(_ongoingCommunication.getId(), _ongoingCommunication);
        _debts += cost;
        _owner.addClientDebt(cost);
        Level level = _owner.getLevel();
        level.negativeBalance();
        level.positiveBalanceAnd5Video();
        level.positiveBalanceAnd2Text();
        _ongoingCommunication = null;
        //what if it was voice? does this affect?

        return cost;
    }


    public void getStateException() throws DestinationTerminalSilentException, DestinationTerminalBusyException,
            DestinationTerminalOffException {
        _state.accept(new StateExceptionVisitor());
    }


    public void registerTextNotificationToSend(Client origin){
        _textNotificationsToSend.add(origin);
    }

    public void registerInteractiveNotificationToSend(Client origin){
        _interactiveNotificationsToSend.add(origin);
    }


    public long getPayments() {
        return _payments;
    }

    public long getDebts() {
        return _debts;
    }

    public long calculateBalance() {
        return _payments - _debts;
    }

    public boolean isUnused() {
        return _pastCommunications.isEmpty() && _ongoingCommunication == null && _communicationsReceived.isEmpty();
    }

    private String listFriends() {
        return String.join(",", _friends.keySet());
    }

    @Override
    public String toString() {
        String friends = _friends.isEmpty() ? "" : ("|" + listFriends());
        return "|" + _key + "|" + _owner.getId() + "|" + getState() + "|" + getPayments() + "|"
                + getDebts() + friends;
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

    public void sendTextNotifications(NotificationType type) {
        for (Client client : _textNotificationsToSend) {
            Notification notification = new Notification(this, client, type) ;
            client.getDeliveryMethod().deliver(notification);
        }
        _textNotificationsToSend.clear();
    }

    /*public void sendInteractiveNotifications(NotificationType type) {
        for (Client client : _interactiveNotificationsToSend) {
            Notification notification = new Notification(this, client, type) ;
            client.getDeliveryMethod().deliver(notification);
        }
        _interactiveNotificationsToSend.clear();
    }*/

    public void sendAllNotifications(NotificationType type) {
        Set<Client> toNotify = new TreeSet<>();
        toNotify.addAll(_textNotificationsToSend);
        toNotify.addAll(_interactiveNotificationsToSend);
        for (Client client : toNotify) {
            Notification notification = new Notification(this, client, type) ;
            client.getDeliveryMethod().deliver(notification);
        }
        _interactiveNotificationsToSend.clear();
        _textNotificationsToSend.clear();
    }

    public Communication findCommunication(int communicationKey) throws CommunicationNotFoundTerminalException {
        Communication communication = _pastCommunications.get(communicationKey);
        if (communication == null)
            throw new CommunicationNotFoundTerminalException();
        return communication;
    }

    public void performPayment(int communicationKey) throws CommunicationNotFoundTerminalException {
        Communication communication = findCommunication(communicationKey);
        if (communication.hasBeenPaid() || communication.ongoing())
            throw new CommunicationNotFoundTerminalException();
        long cost = communication.getCost();
        addTerminalPayment(cost);
        _owner.addClientPayment(cost);
        communication.pay();
        _owner.getLevel().clientBalanceOver500();
    }

    public Collection<String> showTerminalCommunications(){
        Collection<String> allCommunications = new LinkedList<>();
        if (_ongoingCommunication != null)
            allCommunications.add(_ongoingCommunication.toString());

        for (Communication communication : _pastCommunications.values())
            allCommunications.add(communication.toString());
        return allCommunications;
    }


    public Collection<String> showCommunicationsReceived(){
        Collection<String> received = new LinkedList<>();
        for (Communication communication : _communicationsReceived)
            received.add(communication.toString());
        return received;
    }


    public void addTerminalPayment(long cost) {
        _payments += cost;
        _debts -= cost;
    }

}