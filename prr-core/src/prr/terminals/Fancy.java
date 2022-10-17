package prr.terminals;

import prr.app.exceptions.InvalidTerminalKeyException;
import prr.clients.Client;
import prr.terminals.states.State;

import java.io.Serializable;
import java.util.Collection;

public class Fancy extends Terminal implements Serializable {
    private static final long serialVersionUID = 202208091754L;

    public Fancy(String key) {
        super(key);
    }

    public Fancy(Client owner, String key) throws InvalidTerminalKeyException {
        super(owner, key);
    }


    public Fancy(Client owner, String key, State state, Collection<Terminal> friends) throws InvalidTerminalKeyException {
        super(owner, key, state, friends);
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
