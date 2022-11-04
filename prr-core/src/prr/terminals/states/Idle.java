package prr.terminals.states;

import prr.exceptions.AlreadyOnTerminalException;
import prr.terminals.Terminal;

import java.io.Serializable;

public class Idle extends State implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    public Idle(Terminal terminal) {
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
        return true;
    }

    @Override
    public void startInteractiveCommunication(){
        getTerminal().setState(new Busy(getTerminal(), this));
    }



    @Override
    public void turnOn() throws AlreadyOnTerminalException {
        throw new AlreadyOnTerminalException();
    }

    @Override
    public void turnOff() {
        Terminal terminal = getTerminal();
        terminal.setState(new Off(terminal));
    }

    @Override
    public void toSilent() {
        Terminal terminal = getTerminal();
        terminal.setState(new Silent(terminal));
    }


    @Override
    public void accept(StateExceptionVisitor s) {}

    @Override
    public String toString(){
        return "IDLE";
    }

}
