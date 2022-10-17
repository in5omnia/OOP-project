package prr.exceptions;

public abstract class NetworkException extends Exception {
    private static final long serialVersionUID = 202210171749L;
    public NetworkException() {
        super("");//FIXME - NOT MESSAGE
    }

}
