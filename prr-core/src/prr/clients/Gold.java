package prr.clients;

public class Gold extends Level {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    public Gold(Client client) {
        super(client);
    }


    @Override
    public String toString() {
        return "GOLD";
    }
}
