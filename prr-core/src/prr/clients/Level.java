package prr.clients;

import java.io.Serializable;

public abstract class Level implements Serializable {
    private static final long serialVersionUID = 202208091754L;
    //TODO
    private Client _client;
    public Level(Client client){
        _client=client;
    }

    protected Client getClient(){
        return _client;
    }
    public void balanceOver500(int balance){}

    public void negativeBalance(boolean balanceIsNegative){}

    public void fiveConsecutiveVideoAndNotNegative(boolean balanceIsNegative, boolean fiveConsecutiveVideo){} //na avaliação feita antes da sexta comunicação

    public void twoConsecutiveTextAndNotNegative(boolean balanceIsNegative, boolean twoConsecutiveText){} //na avaliação feita antes da terceira comunicação

}
