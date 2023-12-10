package ir.bu.cloudlystorage.exception;

public class UnauthorizedError extends RuntimeException {
    public UnauthorizedError(String msg) {
        super(msg);
    }
}
