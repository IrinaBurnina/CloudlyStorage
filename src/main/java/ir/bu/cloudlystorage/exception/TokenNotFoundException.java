package ir.bu.cloudlystorage.exception;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String msg) {
        super(msg);
    }
}
