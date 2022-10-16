package prr.clients;

import java.io.Serializable;

public class Client implements Serializable {

    private static final long serialVersionUID = 202208091754L;

    private String _clientId;

    private String _name;

    private int _taxId;

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
}
