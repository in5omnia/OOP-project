package prr.terminals.states;

import prr.clients.Client;
import prr.exceptions.AlreadyOnTerminalException;
import prr.exceptions.AlreadySilentTerminalException;
import prr.terminals.Terminal;

import java.io.Serializable;

public class Silent extends State implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    public Silent(Terminal Terminal) {
        super(Terminal);
    }

    public boolean canReceiveTextCommunication(){
        return true;
    }

    public boolean canStartCommunication() {
        return true;
    }

    public boolean canReceiveInteractiveCommunication(){
        return false;
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
    }

    public void toSilent() throws AlreadySilentTerminalException {
        throw new AlreadySilentTerminalException();
    }

    @Override
    public String toString(){
        return "SILENCE";
    }

}
