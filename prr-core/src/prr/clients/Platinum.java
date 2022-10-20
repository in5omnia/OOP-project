package prr.clients;

public class Platinum extends Level  {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    public Platinum(Client client){
        super(client);
    }

    @Override
    public String toString(){
        return "PLATINUM";
    }

}
