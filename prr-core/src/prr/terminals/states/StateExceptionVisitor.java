package prr.terminals.states;

import prr.exceptions.DestinationTerminalBusyException;
import prr.exceptions.DestinationTerminalOffException;
import prr.exceptions.DestinationTerminalSilentException;

public class StateExceptionVisitor {
    public void visit(Off state) throws DestinationTerminalOffException {
        throw new DestinationTerminalOffException();
    }
    public void visit(Busy state) throws DestinationTerminalBusyException {
        throw new DestinationTerminalBusyException();
    }
    public void visit(Silent state) throws DestinationTerminalSilentException {
        throw new DestinationTerminalSilentException();
    }
}
