package prr.terminals.communication;

import prr.terminals.Terminal;

import java.io.Serializable;

public class Voice extends InteractiveCommunication implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;
    public Voice(Terminal source, Terminal destination, int communicationId) {
        super(source, destination, communicationId);
    }

    @Override
    public String toString() {
        return "VOICE" + super.toString();
    }
}
