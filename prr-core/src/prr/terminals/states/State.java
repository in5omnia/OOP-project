package prr.terminals.states;

import java.io.Serializable;

// should be abstract class instead?
public interface State extends Serializable {
    static final long serialVersionUID = 202208091754L; //copiei o num +1 ----- huuuuuh mas em interface n posso escrever private

    public boolean canReceiveTextCommunication();
    public boolean canStartCommunication();
    public boolean canReceiveInteractiveCommunication();

}
