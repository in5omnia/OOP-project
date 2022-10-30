package prr.clients;

import prr.exceptions.AlreadyOffNotificationException;
import prr.exceptions.AlreadyOnNotificationException;
import prr.terminals.Terminal;
import prr.notifications.Notification;

import java.io.Serializable;

import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.LinkedList;

public class Client implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    private String _clientId;

    private String _name;

    private int _taxId;

    private int _payments = 0;

    private int _debts = 0;

    private boolean _notificationsEnabled = true;

    private Level _level = new Normal(this);

    private Map<String, Terminal> _terminals = new TreeMap<>();

    private List<Notification> _notifications = new LinkedList<>();

    public Client(String clientId, String name, int taxId) {
        _clientId = clientId;
        _name = name;
        _taxId = taxId;
    }

    public String getId() {
        return _clientId;
    }

    protected void updateLevel(Level level) {
        _level = level;
    }

    public void addTerminal(String terminalKey, Terminal terminal) {
        _terminals.put(terminalKey, terminal);
    }

    public int calculateBalance() {
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
}
