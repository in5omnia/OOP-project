package prr.clients;

import java.io.Serializable;

public abstract class Level implements Serializable {
    private static final long serialVersionUID = 202208091754L;
    protected Client _client;   //FIXME PROTECTED
    public Level(Client client){
        _client = client;
    }

    public void balanceOver500(){}

    public void negativeBalance(){}

    public void fiveConsecutiveVideoAndNotNegative(){} //na avaliação feita antes da sexta comunicação

    public void twoConsecutiveTextAndNotNegative(){} //na avaliação feita antes da terceira comunicação

}
