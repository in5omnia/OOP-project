package prr.terminals;

import prr.exceptions.InvalidTerminalIdException;
import prr.clients.Client;
import prr.terminals.states.State;

import java.io.Serializable;
import java.util.Collection;

public class Fancy extends Terminal implements Serializable {
    private static final long serialVersionUID = 202208091754L;

    /*public Fancy(String key) {
        super(key);
    }*/ //why needed??

    public Fancy(Client owner, String key) throws InvalidTerminalIdException {
        super(owner, key);
    }


    public Fancy(Client owner, String key, State state) throws InvalidTerminalIdException {
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
        return true;
    }

}
