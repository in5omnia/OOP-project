package prr.clients;

import java.io.Serializable;

public abstract class Level implements Serializable {
    private static final long serialVersionUID = 202208091754L;
    //protected int _balance; //FIXME PROTECTED
    protected Client _client;   //FIXME PROTECTED
    public Level(Client client){
        _client = client;
    }

    /*public void initializeBalance(){    //para o client usar
        _balance = 0;
    }*/

    public void balanceOver500(){}

    public void negativeBalance(){}

    public void fiveConsecutiveVideoAndNotNegative(){} //na avaliação feita antes da sexta comunicação

    public void twoConsecutiveTextAndNotNegative(){} //na avaliação feita antes da terceira comunicação

    //@Override   //FIXME ponho aqui ? leva override? Ou ponho só nas subclasses?
    public abstract String toString();
}
