package prr.clients;

import java.io.Serializable;

public class Client implements Serializable {

    private static final long serialVersionUID = 202208091754L;
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
