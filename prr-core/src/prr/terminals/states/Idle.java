package prr.terminals.states;

import java.io.Serializable;

public class Idle implements State, Serializable {
    private static final long serialVersionUID = 202208091754L;
    public boolean canReceiveTextCommunication(){
        return true;
    }

    public boolean canStartCommunication() {
        return true;
    }

    public boolean canReceiveInteractiveCommunication(){
        return true;
    }

    @Override
    public String toString(){
        return "IDLE";
    }

}
