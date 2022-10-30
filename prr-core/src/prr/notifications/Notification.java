package prr.notifications;

import java.io.Serializable;
import prr.terminals.Terminal;

public class Notification implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    private String _originTerminalId;

    private Terminal _destination;

    private NotificationType _type;


    public Notification(String originTerminalId, Terminal destination) {
        _originTerminalId = originTerminalId;
        _destination = destination;
    }

    /*public Notification(String originTerminalId, Terminal destination, NotificationType type) {
        this(originTerminalId, destination);
        _type = type;
    }*/


    @Override
    public String toString(){
        return _type + "|" + _originTerminalId;
    }
}
