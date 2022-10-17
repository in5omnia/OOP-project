package prr.exceptions;

public class DuplicateFriendException extends Exception {
    private static final long serialVersionUID = 202210171749L;

    private String _key;

    public DuplicateFriendException(String key) {
        _key = key;
    }

    public String get_key() {
        return _key;
    }

}
