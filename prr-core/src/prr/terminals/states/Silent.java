package prr.terminals.states;

import java.io.Serializable;

public class Silent implements State, Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    public boolean canReceiveTextCommunication(){
        return true;
    }

    public boolean canStartCommunication() {
        return true;
    }

    public boolean canReceiveInteractiveCommunication(){
        return false;
    }

    @Override
    public String toString(){
        return "SILENCE";
    }

}
