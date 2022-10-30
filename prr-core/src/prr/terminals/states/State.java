package prr.terminals.states;

import prr.exceptions.AlreadyOffTerminalException;
import prr.exceptions.AlreadyOnTerminalException;
import prr.exceptions.AlreadySilentTerminalException;
import prr.terminals.Terminal;

import java.io.Serializable;

// should be abstract class instead?
abstract public class State implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202210301733L;

    protected Terminal getTerminal() {
        return _terminal;
    }

    private Terminal _terminal;

    public State(Terminal terminal) {
        _terminal = terminal;
    }

    public abstract boolean canReceiveTextCommunication();

    public abstract boolean canStartCommunication();

    public abstract boolean canReceiveInteractiveCommunication();

    public  void turnOff() throws AlreadyOffTerminalException {}

    public  void turnOn() throws AlreadyOnTerminalException {}

    public  void toSilent() throws AlreadySilentTerminalException {}


}
