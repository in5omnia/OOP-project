package prr.exceptions;

public class UnknownClientException extends Exception {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202210171749L;

    private String _key;

    public UnknownClientException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
}