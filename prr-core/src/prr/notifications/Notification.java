package prr.notifications;

import java.io.Serializable;

import prr.clients.Client;
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
    private Client _caller;

    //private String _originClientID;

    private Terminal _destination;

    private NotificationType _type;


    public Notification(Terminal destination, Client caller) {
        _destination = destination;
        _caller = caller;
    }
    public Notification(Terminal destination, Client caller, NotificationType type) {
        _destination = destination;
        _caller = caller;
        _type = type;
    }


    public Client getCaller() {
        return _caller;
    }

    public void setNotificationType(NotificationType type){
        _type = type;
    }

    //FIXME should busy have this?

    @Override
    public String toString(){
        return _type + "|" +  _destination.getKey();
    }
}
