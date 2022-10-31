package prr.terminals.states;

import prr.exceptions.AlreadyOffTerminalException;
import prr.exceptions.DestinationTerminalOffException;
import prr.terminals.Terminal;

import java.io.Serializable;

public class Off extends State implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    public Off(Terminal terminal) {
        super(terminal);
    }

    public boolean canReceiveTextCommunication(){
        return false;
    }

    public boolean canStartCommunication() {
        return false;
    }

    public boolean canReceiveInteractiveCommunication() {
        return false;
    }

    public void turnOff() throws AlreadyOffTerminalException {
        throw new AlreadyOffTerminalException();
    }

    public void turnOn() {
        Terminal terminal = getTerminal();
        terminal.setState(new Idle(terminal));
    }

    public void toSilent() {
        Terminal terminal = getTerminal();
        terminal.setState(new Silent(terminal));
    }

    //FIXME
    public void accept(StateExceptionVisitor s) throws DestinationTerminalOffException {
        s.visit(this);
    }

    @Override
    public String toString(){
        return "OFF";
    }

}
