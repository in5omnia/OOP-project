package prr.clients;

import java.io.Serializable;

public class Gold extends Level {
    private static final long serialVersionUID = 202208091754L;
    //TODO
    public Gold(Client client){
        super(client);
    }


    public void negativeBalance(boolean balanceIsNegative){
        if (balanceIsNegative)
            getClient().updateLevel(new Normal(getClient()));
    }

    public void fiveConsecutiveVideoAndNotNegative(boolean balanceIsNegative, boolean fiveConsecutiveVideo){
        if (!balanceIsNegative && fiveConsecutiveVideo)
            getClient().updateLevel(new Platinum(getClient()));
    }           //na avaliação feita antes da sexta comunicação

    @Override
    public String toString(){
        return "GOLD";
    }
}
