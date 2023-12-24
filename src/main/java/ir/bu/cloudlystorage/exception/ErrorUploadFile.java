package ir.bu.cloudlystorage.exception;

public class ErrorUploadFile extends RuntimeException {
    public ErrorUploadFile(String message) {
        super(message);
    }
}
