package prr.clients;

import java.io.Serializable;

public class Normal extends Level {
    private static final long serialVersionUID = 202208091754L;

    //TODO
    public Normal(Client client){
        super(client);
    }

    public void balanceOver500(int balance){
        if (balance > 500)
            getClient().updateLevel(new Gold(getClient()));
    }
    @Override
    public String toString(){
        return "NORMAL";
    }

}
