package prr.notifications;

import java.io.Serializable;
import prr.terminals.Terminal;
import prr.terminals.states.Busy;
import prr.terminals.states.Idle;
import prr.terminals.states.Off;
import prr.terminals.states.Silent;

public class Notification implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    //private String _originTerminalId;

    private Terminal _origin;

    //private String _originClientID;

    private Terminal _destination;

    private NotificationType _type;


    public Notification(Terminal origin, Terminal destination) {
        _origin = origin;
        _destination = destination;
    }


    public Terminal getOrigin() {
        return _origin;
    }

    public void setNotificationType(NotificationType type){
        _type = type;
    }

    //FIXME should busy have this?

    @Override
    public String toString(){
        return _type + "|" + /*_originTerminalId*/ _destination.getKey();
    }
}
