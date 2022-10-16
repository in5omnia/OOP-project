package prr.terminals;

import java.io.Serializable;

public class Basic extends Terminal implements Serializable {
    private static final long serialVersionUID = 202208091754L;

    public Basic(String id) {
        super(id);
    }

    @Override
    public boolean canMessage() {
        return true;
    }

    @Override
    public boolean canVoiceCommunicate() {
        return true;
    }

    @Override
    public boolean canVideoCommunicate() {
        return false;
    }

}
