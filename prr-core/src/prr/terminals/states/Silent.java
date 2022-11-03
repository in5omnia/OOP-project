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

    @Override
    public boolean canReceiveTextCommunication(){
        return true;
    }

    @Override
    public boolean canStartCommunication() {
        return true;
    }

    @Override
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
    public void turnOn() {
        Terminal terminal = getTerminal();
        terminal.setState(new Idle(terminal));
        terminal.sendAllNotifications(new S2I());
    }

    @Override
    public void toSilent() throws AlreadySilentTerminalException {
        throw new AlreadySilentTerminalException();
    }

    //FIXME
    @Override
    public void accept(StateExceptionVisitor s) throws DestinationTerminalSilentException {
        s.visit(this);
    }

    @Override
    public String toString(){
        return "SILENCE";
    }

}
