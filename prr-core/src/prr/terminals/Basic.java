package prr.terminals;

import prr.exceptions.InvalidTerminalIdException;
import prr.clients.Client;
import prr.terminals.states.State;

import java.io.Serializable;
import java.util.Collection;

public class Basic extends Terminal implements Serializable {
    private static final long serialVersionUID = 202208091754L;


    public Basic(Client owner, String key) throws InvalidTerminalIdException {
        super(owner, key);
    }

    // Does this need to throw exception? it calls super, isnt it enough that super throws? - same for fancy (im probably wrong)
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
