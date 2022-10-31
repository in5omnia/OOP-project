package prr.exceptions;

public class UnsupportedAtOriginException extends Exception{
    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202208091754L;

    private String _key;
    private String _type;

    public UnsupportedAtOriginException(String key, String type) {
        _key = key;
        _type = type;
    }

    public String getKey() {
        return _key;
    }

    public String getType() {
        return _type;
    }

}
