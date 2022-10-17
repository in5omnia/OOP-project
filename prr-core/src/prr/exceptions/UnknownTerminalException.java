package prr.exceptions;

public class UnknownTerminalException extends Exception{
    private static final long serialVersionUID = 202210171749L;

    private String _key;

    public UnknownTerminalException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
}
