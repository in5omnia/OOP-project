package prr.terminals.communication;

import java.io.Serializable;

public class Voice extends InteractiveCommunication implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    @Override
    public String toString() {
        return "VOICE" + super.toString();
    }
}
