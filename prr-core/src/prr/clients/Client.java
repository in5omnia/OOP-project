package prr.clients;

import prr.deliveryMethods.AppDelivery;
import prr.deliveryMethods.DeliveryMethod;
import prr.exceptions.AlreadyOffNotificationException;
import prr.exceptions.AlreadyOnNotificationException;
import prr.plans.Plan;
import prr.terminals.Terminal;
import prr.notifications.Notification;

import java.io.Serializable;

import java.util.*;

public class Client implements Serializable, Comparable<Client> {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    private String _clientId;

    private String _name;

    private int _taxId;

    private long _payments = 0;

    private long _debts = 0;

    private boolean _notificationsEnabled = true;

    private Level _level = new Normal(this);

    private Map<String, Terminal> _terminals = new TreeMap<>();

    private List<Notification> _notifications = new LinkedList<>();

    private DeliveryMethod _deliveryMethod = new AppDelivery();

    public Client(String clientId, String name, int taxId) {
        _clientId = clientId;
        _name = name;
        _taxId = taxId;
    }

    public String getId() {
        return _clientId;
    }

    public DeliveryMethod getDeliveryMethod() {
        return _deliveryMethod;
    }

    public Plan getPlan(){  //FIXME
        return _level.getPlan();
    }

    public Level getLevel(){  //FIXME
        return _level;
    }

    protected void setLevel(Level level) {
        _level = level;
    }

    public void addTerminal(String terminalKey, Terminal terminal) {
        _terminals.put(terminalKey, terminal);
    }

    public long calculateBalance() {
        return _payments - _debts;
    }

    public String showNotifications() {

        String string = "";
        for (Notification notification : _notifications) {
            string += "\n" + notification.toString();
        }
        _notifications.clear();
        return string;
    }

    public void enableNotifications()  throws  AlreadyOnNotificationException {
        if (_notificationsEnabled) {
            throw new AlreadyOnNotificationException();
        }
        _notificationsEnabled = true;
    }

    public void disableNotifications()  throws  AlreadyOffNotificationException {
        if (!_notificationsEnabled) {
            throw new AlreadyOffNotificationException();
        }
        _notificationsEnabled = false;
    }

    public boolean notificationsEnabled(){  //FIXME replace in disable/enable?
        return _notificationsEnabled;
    }

    public long getPayments() {
        return _payments;
    }

    public long getDebts() {
        return _debts;
    }

    @Override
    public String toString() {

        String notifications = (_notificationsEnabled) ? "YES" : "NO";
        return "CLIENT|" + _clientId + "|" + _name + "|" + _taxId + "|" + _level.toString() + "|" + notifications +
                "|" + _terminals.size() + "|" + _payments + "|" + _debts;

    }

    public void receiveNotification(Notification notification){
        _notifications.add(notification);
    }

    public void addClientPayment(long cost){
        _payments += cost;
        _debts -= cost;
    }

    public void addClientDebt(long cost){
        _payments -= cost;
        _debts += cost;
    }

    public Collection<String> showClientCommunications() {
        Collection<String> allCommunications = new LinkedList<>();
        for (Terminal terminal : _terminals.values())
            allCommunications.addAll(terminal.showTerminalCommunications());    //FIXME does this work?
        return allCommunications;
    }

    @Override
    public int compareTo(Client o) {
        Client c = (Client) o;

        return _clientId.compareTo(c._clientId);

    }
}