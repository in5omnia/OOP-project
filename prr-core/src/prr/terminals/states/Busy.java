package prr.terminals.states;

import prr.exceptions.DestinationTerminalBusyException;
import prr.notifications.B2I;
import prr.terminals.Terminal;

import java.io.Serializable;

public class Busy extends State implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    private boolean _previousStateSilenced;

    public Busy(Terminal terminal, Idle previousState) {
        super(terminal);
        _previousStateSilenced = false;
    }

    public Busy(Terminal terminal, Silent previousState) {
        super(terminal);
        _previousStateSilenced = true;
    }

    @Override
    public boolean canStartCommunication() {
        return false;
    }

    @Override
    public boolean canReceiveTextCommunication(){
        return true;
    }

    @Override
    public boolean canReceiveInteractiveCommunication() {
        return false;
    }

    @Override
    public void endInteractiveCommunication() {
        Terminal terminal = getTerminal();
        if (_previousStateSilenced)
            terminal.setState(new Silent(terminal));
        else {
            terminal.setState(new Idle(terminal));
            terminal.sendAllNotifications(new B2I());
        }
    }

    @Override
    public void accept(StateExceptionVisitor s) throws DestinationTerminalBusyException {
        s.visit(this);
    }

    @Override
    public String toString(){
        return "BUSY";
    }

}
