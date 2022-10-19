package prr.terminals.states;

import java.io.Serializable;

// should be abstract class instead?
public interface State extends Serializable {

    public boolean canReceiveTextCommunication();
    public boolean canStartCommunication();
    public boolean canReceiveInteractiveCommunication();

}
