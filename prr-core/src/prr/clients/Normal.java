package prr.clients;

import java.io.Serializable;

public class Normal extends Level {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    public Normal(Client client){
        super(client);
    }

    @Override
    public String toString(){
        return "NORMAL";
    }

}
