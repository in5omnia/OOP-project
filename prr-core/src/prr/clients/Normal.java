package prr.clients;


import prr.plans.NormalPlan;

public class Normal extends Level {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    public Normal(Client client) {
        super(client, new NormalPlan());
    }

    @Override
    public void negativeBalance(){}

    @Override
    public String toString() {
        return "NORMAL";
    }

}
