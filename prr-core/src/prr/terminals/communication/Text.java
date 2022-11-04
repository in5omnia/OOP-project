package prr.terminals.communication;

import prr.terminals.Terminal;

import java.io.Serializable;

public class Text extends Communication implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    private final String _message;

    public Text(Terminal source, Terminal destination, int communicationId, String message) {
        super(source, destination, communicationId, false, message.length());
        _message = message;
    }

    @Override
    protected double calculateCost(double units){
        return getSource().getOwner().getPlan().calculateMessageCost(units);
    }

    @Override
    public String toString() {
        return "TEXT" + super.toString();
    }
}
