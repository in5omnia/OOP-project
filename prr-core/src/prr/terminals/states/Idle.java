package prr.terminals.states;

import java.io.Serializable;

public class Idle implements State, Serializable {
    private static final long serialVersionUID = 202208091754L;
    // FIXME
    public boolean canReceiveTextCommunication(){
        return true;
    }

    public boolean canStartCommunication() {
        return true;
    }

    public boolean canReceiveInteractiveCommunication(){
        return true;
    }
}
