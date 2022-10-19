package prr.notifications;

import prr.terminals.Terminal;
import java.io.Serializable;

public class Notification implements Serializable {
    private static final long serialVersionUID = 202208091754L;
    private String _originTerminalId;
    private Terminal _destination;
    private NotificationType _type;

    public Notification(String originTerminalId, Terminal destination, NotificationType type) {
        _originTerminalId = originTerminalId;
        _destination = destination;
        _type = type;
    }

    @Override
    public String toString(){
        return _type + "|" + _originTerminalId;
    }
}
