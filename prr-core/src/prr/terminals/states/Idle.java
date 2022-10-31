package prr.terminals.states;

import prr.exceptions.AlreadyOnTerminalException;
import prr.exceptions.DestinationTerminalSilentException;
import prr.terminals.Terminal;

import java.io.Serializable;

public class Idle extends State implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    public Idle(Terminal terminal) {
        super(terminal);
        terminal.sendTextNotifications(this);
        terminal.sendInteractiveNotifications(this);
    }

    public boolean canReceiveTextCommunication(){
        return true;
    }

    public boolean canStartCommunication() {
        return true;
    }

    public boolean canReceiveInteractiveCommunication() {
        return true;
    }

    @Override
    public void turnOff() {
        Terminal terminal = getTerminal();
        terminal.setState(new Off(terminal));
    }

    @Override
    public void turnOn() throws AlreadyOnTerminalException {
        throw new AlreadyOnTerminalException();
    }


    public void toSilent() {
        Terminal terminal = getTerminal();
        terminal.setState(new Silent(terminal));
    }

    //FIXME
    public void accept(StateExceptionVisitor s) {}

    @Override
    public String toString(){
        return "IDLE";
    }

}
