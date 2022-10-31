package prr.terminals.communication;

import prr.terminals.Terminal;

import java.io.Serializable;

public abstract class InteractiveCommunication extends Communication implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    public InteractiveCommunication(Terminal source, Terminal destination, int communicationId) {
        super(source, destination, communicationId, true);

    }
}
