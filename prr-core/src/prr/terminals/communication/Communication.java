package prr.terminals.communication;

import prr.terminals.Terminal;

import java.io.Serializable;

public abstract class Communication implements Serializable {
    private static final long serialVersionUID = 202208091754L;
    private int _key;
    private Terminal _origin;
    private boolean _ongoing;
    private boolean paid = false;
    private int cost;

}
