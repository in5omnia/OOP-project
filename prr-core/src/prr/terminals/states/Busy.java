package prr.terminals.states;

import prr.exceptions.DestinationTerminalBusyException;
import prr.terminals.Terminal;

import java.io.Serializable;

public class Busy extends State implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    private State _previousState;

    /*public Busy(Terminal terminal) {
        super(terminal);
    }*/

    public Busy(Terminal terminal, State previousState) {
        super(terminal);
        _previousState = previousState;
        //terminal.sendTextNotifications(this);
    }

    public boolean canReceiveTextCommunication(){
        return true;
    }

    public boolean canStartCommunication() {
        return false;
    }

    public boolean canReceiveInteractiveCommunication() {
        return false;
    }

    @Override
    public void endInteractiveCommunication() {
        getTerminal().setState(_previousState);
    }

    //FIXME
    public void accept(StateExceptionVisitor s) throws DestinationTerminalBusyException {
        s.visit(this);
    }


    @Override
    public String toString(){
        return "BUSY";
    }

}
