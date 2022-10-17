package prr.exceptions;
//FIXME not sure if we need or we just buse importfileexception
public class FileOpenFailException extends Exception {
    private static final long serialVersionUID = 202210171749L;
    private String _filename;

    public FileOpenFailException(String filename) {
        _filename = filename;
    }

    public String getFilename() {
        return _filename;
    }
}