package prr.clients;

import java.io.Serializable;

public class Gold extends Level implements Serializable {
    private static final long serialVersionUID = 202208091754L;

    public Gold(Client client){
        super(client);
    }


    public void negativeBalance(){
        if (_balance < 0)
            _client.updateLevel(new Normal(_client));
    }

    public void fiveConsecutiveVideoAndNotNegative(){
        if (_balance >= 0 && _client.fiveConsecutiveVideo())
            _client.updateLevel(new Platinum(_client));
    }           //na avaliação feita antes da sexta comunicação
}
