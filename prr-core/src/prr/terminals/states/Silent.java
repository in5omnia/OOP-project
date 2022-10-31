package prr.terminals.states;

import prr.exceptions.*;
import prr.notifications.O2I;
import prr.notifications.S2I;
import prr.terminals.Terminal;

import java.io.Serializable;

public class Silent extends State implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    public Silent(Terminal terminal) {
        super(terminal);
    }

    public boolean canReceiveTextCommunication(){
        return true;
    }

    public boolean canStartCommunication() {
        return true;
    }

    public boolean canReceiveInteractiveCommunication() {
        return false;
    }

    @Override
    public void startInteractiveCommunication(){
        getTerminal().setState(new Busy(getTerminal(), this));
    }

    @Override
    public void turnOff() {
        Terminal terminal = getTerminal();
        terminal.setState(new Off(terminal));
    }

    @Override
    public void turnOn() throws AlreadyOnTerminalException {
        Terminal terminal = getTerminal();
        terminal.setState(new Idle(terminal));
        terminal.sendInteractiveNotifications(new S2I());
    }

    public void toSilent() throws AlreadySilentTerminalException {
        throw new AlreadySilentTerminalException();
    }

    //FIXME
    public void accept(StateExceptionVisitor s) throws DestinationTerminalSilentException {
        s.visit(this);
    }

    @Override
    public String toString(){
        return "SILENCE";
    }

}
