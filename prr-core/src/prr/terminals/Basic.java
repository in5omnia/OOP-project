package prr.terminals;

import prr.exceptions.InvalidTerminalIdException;
import prr.clients.Client;
import prr.terminals.states.State;

import java.io.Serializable;

public class Basic extends Terminal implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;


    public Basic(Client owner, String key) throws InvalidTerminalIdException {
        super(owner, key);
    }

    public Basic(Client owner, String key, State state) throws InvalidTerminalIdException {
        super(owner, key, state);
    }


    @Override
    public boolean canMessage() {
        return true;
    }

    @Override
    public boolean canVoiceCommunicate() {
        return true;
    }

    @Override
    public boolean canVideoCommunicate() {
        return false;
    }

    @Override
    public String toString() {
        return "BASIC" + super.toString();
    }

}
