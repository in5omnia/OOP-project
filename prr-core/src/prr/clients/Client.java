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

    private int _consecutiveVideo = 0;
    private int _consecutiveText = 0;

    public Client(String clientId, String name, int taxId) {
        _clientId = clientId;
        _name = name;
        _taxId = taxId;
    }


    /**
     * Methods related to the Level
     */
    protected void updateLevel(Level level){
        _level = level;
    }

    public void balanceOver500(){
        _level.balanceOver500(calculateBalance());
    }

    public void negativeBalance(){
        _level.negativeBalance(balanceIsNegative());
    }

    public void fiveConsecutiveVideoAndNotNegative(){
        _level.fiveConsecutiveVideoAndNotNegative(balanceIsNegative(), _consecutiveVideo == 5);
    } //na avaliação feita antes da sexta comunicação

    public void twoConsecutiveTextAndNotNegative(){
        _level.twoConsecutiveTextAndNotNegative(balanceIsNegative(), _consecutiveText == 2);
    } //na avaliação feita antes da terceira comunicação

    public void checkForLevelUpdates_Text(){
        negativeBalance();
        twoConsecutiveTextAndNotNegative();
    }

    public void checkForLevelUpdates_Video(){
        negativeBalance();
        fiveConsecutiveVideoAndNotNegative();
    }



    public boolean balanceIsNegative(){
        return calculateBalance() < 0;
    }

    public String getId() {
        return _clientId;
    }

    public void videoCommunication(){
        _consecutiveVideo++;
        _consecutiveText = 0;
    }

    public void textCommunication(){
        _consecutiveText++;
        _consecutiveVideo = 0;
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
