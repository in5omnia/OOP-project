package prr.terminals.states;

import java.io.Serializable;

public class Busy implements State, Serializable {
    private static final long serialVersionUID = 202208091754L;
    //FIXME
    public boolean canReceiveTextCommunication(){
        return true;
    }

    public boolean canStartCommunication() {
        return false;
    }

    public boolean canReceiveInteractiveCommunication(){
        return false;
    }

    @Override
    public String toString(){
        return "BUSY";
    }

}
