package prr.clients;

import prr.terminals.Terminal;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Client implements Serializable {

    private static final long serialVersionUID = 202208091754L;

    private String _clientId;

    private String _name;

    private int _taxId;

    private Map<String, Terminal> _terminals = new TreeMap<>();

    public Client(String clientId, String name, int taxId) {
        _clientId = clientId;
        _name = name;
        _taxId = taxId;
    }

    private Level _level = new Normal(this);

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
}
