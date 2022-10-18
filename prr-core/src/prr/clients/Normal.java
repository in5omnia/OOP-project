package prr.clients;

import java.io.Serializable;

public class Normal extends Level {
    private static final long serialVersionUID = 202208091754L;

    public Normal(Client client){
        super(client);
    }

    public void balanceOver500(){
        if (_balance > 500)
           _client.updateLevel(new Gold(_client));
    }

}
