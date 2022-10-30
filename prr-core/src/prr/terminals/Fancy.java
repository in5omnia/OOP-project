package prr.terminals;

import prr.clients.Client;
import prr.terminals.states.State;
import prr.exceptions.InvalidTerminalIdException;

import java.io.Serializable;

public class Fancy extends Terminal implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;


    public Fancy(Client owner, String key) throws InvalidTerminalIdException {
        super(owner, key);
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
        return true;
    }

    @Override
    public String toString() {
        return "FANCY" + super.toString();
    }

}
