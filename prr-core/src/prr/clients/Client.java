package prr.clients;

import prr.notifications.Notification;
import prr.terminals.Terminal;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Client implements Serializable {

    private static final long serialVersionUID = 202208091754L;

    private String _clientId;

    private String _name;

    private int _taxId;

    private int _payments = 0;

    private int _debts = 0;

    private boolean notificationsEnabled = true;

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

    protected void updateLevel(Level level){        //FIXME PROTECTED
        _level = level;
    }

    public boolean fiveConsecutiveVideo(){/*FIXME*/
        return true;
    }

    public boolean twoConsecutiveText(){/*FIXME*/
        return true;
    }

    public void addTerminal(String terminalKey, Terminal terminal) {
        _terminals.put(terminalKey, terminal);
    }

    public int calculateBalance(){
        return _payments - _debts;
    }

    public String showNotifications() {

        String string = "";
        for (Notification notification : _notifications){
            string += "\n" + notification.toString();
        }
        _notifications.clear();
        return string;

    }

    @Override
    public String toString(){

        String notifications = (notificationsEnabled) ? "YES" : "NO" ;
        return "CLIENT|" + _clientId + "|" + _name + "|" + _taxId + "|" + _level.toString() + "|" + notifications +
                "|" + _terminals.size() + "|" + _payments + "|" + _debts;

    }
}
