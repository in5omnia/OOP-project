package prr.clients;

public class Platinum extends Level  {
    private static final long serialVersionUID = 202208091754L;

   //TODO
   public Platinum(Client client){
       super(client);
   }

    public void negativeBalance(boolean balanceIsNegative){
        if (balanceIsNegative)
            getClient().updateLevel(new Normal(getClient()));
    }

    public void twoConsecutiveTextAndNotNegative(boolean balanceIsNegative, boolean twoConsecutiveText){
        if (!balanceIsNegative && twoConsecutiveText)
            getClient().updateLevel(new Platinum(getClient()));
    }                       //na avaliação feita antes da terceira comunicação

    @Override
    public String toString(){
        return "PLATINUM";
    }

}
