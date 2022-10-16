package prr.clients;

import java.io.Serializable;

public class Platinum extends Level implements Serializable {
    private static final long serialVersionUID = 202208091754L;

    public Platinum(Client client){
        super(client);
    }

    public void negativeBalance(){
        if (_balance < 0)
            _client.updateLevel(new Normal(_client));
    }

    public void twoConsecutiveTextAndNotNegative(){
        if (_balance >= 0 && _client.twoConsecutiveText())
            _client.updateLevel(new Platinum(_client));
    }                       //na avaliação feita antes da terceira comunicação
}
