package prr.terminals.states;

import prr.exceptions.AlreadyOffTerminalException;
import prr.exceptions.AlreadyOnTerminalException;
import prr.exceptions.AlreadySilentTerminalException;
import prr.exceptions.DestinationTerminalSilentException;
import prr.exceptions.DestinationTerminalBusyException;
import prr.exceptions.DestinationTerminalOffException;
import prr.terminals.Terminal;

import java.io.Serializable;

abstract public class State implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202210301733L;

    private Terminal _terminal;

    public State(Terminal terminal) {
        _terminal = terminal;
    }

    protected Terminal getTerminal() {
        return _terminal;
    }

    public abstract boolean canReceiveTextCommunication();

    public abstract boolean canStartCommunication();

    public abstract boolean canReceiveInteractiveCommunication();

    public void startInteractiveCommunication(){}

    public void endInteractiveCommunication(){}

    public void turnOff() throws AlreadyOffTerminalException {}

    public void turnOn() throws AlreadyOnTerminalException {}

    public void toSilent() throws AlreadySilentTerminalException {}

    public abstract void accept(StateExceptionVisitor s) throws DestinationTerminalSilentException,
            DestinationTerminalBusyException, DestinationTerminalOffException;

}
