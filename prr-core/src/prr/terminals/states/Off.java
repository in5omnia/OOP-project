package prr.terminals.states;

import prr.exceptions.AlreadyOffTerminalException;
import prr.exceptions.DestinationTerminalOffException;
import prr.notifications.O2I;
import prr.notifications.O2S;
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

    @Override
    public boolean canReceiveTextCommunication(){
        return false;
    }

    @Override
    public boolean canStartCommunication() {
        return false;
    }

    @Override
    public boolean canReceiveInteractiveCommunication() {
        return false;
    }

    @Override
    public void turnOff() throws AlreadyOffTerminalException {
        throw new AlreadyOffTerminalException();
    }

    @Override
    public void turnOn() {  //changes a terminal to Idle
        Terminal terminal = getTerminal();
        terminal.setState(new Idle(terminal));
        terminal.sendAllNotifications(new O2I());
    }

    @Override
    public void toSilent() {
        Terminal terminal = getTerminal();
        terminal.setState(new Silent(terminal));
        terminal.sendTextNotifications(new O2S());
    }


    @Override
    public void accept(StateExceptionVisitor s) throws DestinationTerminalOffException {
        s.visit(this);
    }

    @Override
    public String toString(){
        return "OFF";
    }

}
